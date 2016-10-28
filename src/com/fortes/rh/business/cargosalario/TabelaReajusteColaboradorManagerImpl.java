package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.HistoricoColaboradorUtil;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajusteInterface;

@Component
public class TabelaReajusteColaboradorManagerImpl extends GenericManagerImpl<TabelaReajusteColaborador, TabelaReajusteColaboradorDao> implements TabelaReajusteColaboradorManager
{
	private ReajusteColaboradorManager reajusteColaboradorManager;
	private ReajusteFaixaSalarialManager reajusteFaixaSalarialManager;
	private ReajusteIndiceManager reajusteIndiceManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private IndiceHistoricoManager indiceHistoricoManager;
	private AcPessoalClientTabelaReajusteInterface acPessoalClientTabelaReajuste;
	private ColaboradorManager colaboradorManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;

	//private final Boolean APROVADA = true;  - Descomentar se for necessário criar o método para encontrar tabelas aprovadas.
	private final Boolean NAO_APROVADA = false;
	private final Boolean TODAS = null;
	
	@Autowired
	TabelaReajusteColaboradorManagerImpl(TabelaReajusteColaboradorDao dao) {
		setDao(dao);
	}
	
	@Override
	public void update(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		getDao().update(tabelaReajusteColaborador);
	}
	
	public void remove(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		reajusteFaixaSalarialManager.removeByTabelaReajusteColaborador(tabelaReajusteColaborador.getId());
		reajusteIndiceManager.removeByTabelaReajusteColaborador(tabelaReajusteColaborador.getId());
		reajusteColaboradorManager.deleteByColaboradoresTabelaReajuste(null, tabelaReajusteColaborador.getId());
		getDao().remove(tabelaReajusteColaborador.getId());
	}

	public Collection<TabelaReajusteColaborador> findAllSelect(Long empresaId, Date dataIni, Date dataFim)
	{
		return getDao().findAllSelect(empresaId, null, TODAS, dataIni, dataFim);
	}

	public Collection<TabelaReajusteColaborador> findAllSelectByNaoAprovada(Long empresaId, Character tipoReajuste)
	{
		return getDao().findAllSelect(empresaId, tipoReajuste, NAO_APROVADA, null, null);
	}

	public void marcaUltima(Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors)
	{
		CollectionUtil<TabelaReajusteColaborador> cu = new CollectionUtil<TabelaReajusteColaborador>();
		tabelaReajusteColaboradors = cu.sortCollectionDate(tabelaReajusteColaboradors, "data");

		boolean ultimoColaborador = true;
		boolean ultimaFaixaSalarial = true;
		boolean ultimoIndice = true;
		
		for(TabelaReajusteColaborador tabelaReajusteColaborador :tabelaReajusteColaboradors)
		{
			if(tabelaReajusteColaborador.isAprovada())
			{
				if(ultimoColaborador && tabelaReajusteColaborador.getTipoReajuste().equals(TipoReajuste.COLABORADOR))
				{
					tabelaReajusteColaborador.setEhUltimo(true);
					ultimoColaborador = false;
				}
				if(ultimaFaixaSalarial && tabelaReajusteColaborador.getTipoReajuste().equals(TipoReajuste.FAIXA_SALARIAL))
				{
					tabelaReajusteColaborador.setEhUltimo(true);
					ultimaFaixaSalarial = false;
				}
				if(ultimoIndice && tabelaReajusteColaborador.getTipoReajuste().equals(TipoReajuste.INDICE))
				{
					tabelaReajusteColaborador.setEhUltimo(true);
					ultimoIndice = false;
				}
			}
		}
	}

	public void aplicarPorColaborador(TabelaReajusteColaborador tabelaReajusteColaborador, Empresa empresa, Collection<ReajusteColaborador> reajustes) throws Exception
	{
		if(tabelaReajusteColaborador != null && (reajustes == null || reajustes.size() == 0))
			throw new ColecaoVaziaException("Nenhum colaborador na tabela de realinhamento.");
		
		//Verifica se existem colaboradores desligados antes da data de aplicação do reajuste
		colaboradorManager.verificaColaboradoresDesligados(reajustes);

		//Se a empresa estiver integrada com ac todos os colaboradores precisam do código ac, caso tenha colaborador sem codigoAC throws
		if(empresa.isAcIntegra())
			colaboradorManager.verificaColaboradoresSemCodigoAC(reajustes);

		HistoricoColaborador historicoColaborador;
		Collection<HistoricoColaborador> historicosAc = new ArrayList<HistoricoColaborador>();

		for (ReajusteColaborador reajuste : reajustes)
		{
			HistoricoColaborador historicoAtual = historicoColaboradorManager.getHistoricoAtual(reajuste.getColaborador().getId());
			
			historicoColaborador = new HistoricoColaborador();
			historicoColaborador.setId(null);
			historicoColaborador.setColaborador(reajuste.getColaborador());
			historicoColaborador.setData(reajuste.getTabelaReajusteColaborador().getData());
			historicoColaborador.setFaixaSalarial(reajuste.getFaixaSalarialProposta());
			historicoColaborador.setReajusteColaborador(reajuste);
			historicoColaborador.setAreaOrganizacional(reajuste.getAreaOrganizacionalProposta());
			historicoColaborador.setEstabelecimento(reajuste.getEstabelecimentoProposto());
			historicoColaborador.setTipoSalario(reajuste.getTipoSalarioProposto());
			historicoColaborador.setGfip(historicoAtual.getGfip());

			historicoColaborador = historicoColaboradorManager.ajustaTipoSalario(historicoColaborador, reajuste.getTipoSalarioProposto(), reajuste.getIndiceProposto(), reajuste.getQuantidadeIndiceProposto(), reajuste.getSalarioProposto());

			if(reajuste.getObservacao() !=null)
				historicoColaborador.setObsACPessoal(reajuste.getObservacao());
			
			if(reajuste.getAmbienteProposto() != null && reajuste.getAmbienteProposto().getId() != null)
				historicoColaborador.setAmbiente(reajuste.getAmbienteProposto());

			if(reajuste.getFuncaoProposta() != null && reajuste.getFuncaoProposta().getId() != null)
				historicoColaborador.setFuncao(reajuste.getFuncaoProposta());

			if (tabelaReajusteColaborador.isDissidio())
				historicoColaborador.setMotivo(MotivoHistoricoColaborador.DISSIDIO);
			else
				historicoColaborador.setMotivo(HistoricoColaboradorUtil.getMotivoReajuste(reajuste, historicoColaborador));

			if(empresa.isAcIntegra() && !reajuste.getColaborador().isNaoIntegraAc())
				historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
			else
				historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);

			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), empresa.getId(), reajuste.getColaborador().getId());
			
			historicoColaborador = historicoColaboradorManager.save(historicoColaborador);

			if(!reajuste.getColaborador().isNaoIntegraAc())
				historicosAc.add(historicoColaborador);
		}

		getDao().updateSetAprovada(tabelaReajusteColaborador.getId(), true);
		
		// garante que um erro no banco do RH levantará uma Exception antes de alterar o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush();

		if(empresa.isAcIntegra() && historicosAc.size() > 0)
			acPessoalClientTabelaReajuste.aplicaReajuste(historicosAc, empresa);
	}

	public void aplicarPorFaixaSalarial(Long tabelaReajusteColaboradorId, Empresa empresa) throws ColecaoVaziaException, Exception
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = findByIdProjection(tabelaReajusteColaboradorId);
		Collection<ReajusteFaixaSalarial> reajustes = reajusteFaixaSalarialManager.findByTabelaReajusteColaboradorId(tabelaReajusteColaboradorId);
		
		if (reajustes == null || reajustes.size() == 0)
			throw new ColecaoVaziaException("Nenhuma faixa salarial para o reajuste");
		
		FaixaSalarialHistorico faixaSalarialHistorico;
		
		for (ReajusteFaixaSalarial reajuste : reajustes)
		{
			faixaSalarialHistorico = new FaixaSalarialHistorico();
			faixaSalarialHistorico.setReajusteFaixaSalarial(reajuste);
			faixaSalarialHistorico.setData(tabelaReajusteColaborador.getData());
			faixaSalarialHistorico.setFaixaSalarial(reajuste.getFaixaSalarial());
			faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
			faixaSalarialHistorico.setValor(reajuste.getValorProposto());
			
			faixaSalarialHistoricoManager.save(faixaSalarialHistorico, reajuste.getFaixaSalarial(), empresa, true);
		}
		
		getDao().updateSetAprovada(tabelaReajusteColaborador.getId(), true);
	}
	
	public void aplicarPorIndice(Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception, FortesException 
	{
		if(empresa.isAcIntegra())
			throw new FortesException("A manutenção no cadastro de índice deve ser realizada no Fortes Pessoal.");
		
		TabelaReajusteColaborador tabelaReajusteColaborador = findByIdProjection(tabelaReajusteColaboradorId);
		Collection<ReajusteIndice> reajustes = reajusteIndiceManager.findByTabelaReajusteColaboradorId(tabelaReajusteColaboradorId);
		
		if (reajustes == null || reajustes.size() == 0)
			throw new FortesException("Nenhum índice para o reajuste");
		
		IndiceHistorico indiceHistorico;
		
		for (ReajusteIndice reajuste : reajustes)
		{
			if(indiceHistoricoManager.verifyData(null, tabelaReajusteColaborador.getData(), reajuste.getIndice().getId()))
				throw new FortesException("Já existe um histórico com essa data.");

			indiceHistorico = new IndiceHistorico();
			indiceHistorico.setIndice(reajuste.getIndice());
			indiceHistorico.setData(tabelaReajusteColaborador.getData());
			indiceHistorico.setValor(reajuste.getValorProposto());
			indiceHistorico.setReajusteIndice(reajuste);

			indiceHistoricoManager.save(indiceHistorico);
		}
		
		getDao().updateSetAprovada(tabelaReajusteColaborador.getId(), true);
	}
	
	public void cancelar(Character tipoReajuste, Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception
	{
		if (tipoReajuste.equals(TipoReajuste.COLABORADOR))
			cancelarColaborador(tabelaReajusteColaboradorId, empresa);
		
		else if (tipoReajuste.equals(TipoReajuste.FAIXA_SALARIAL))
			cancelarPorFaixaSalarial(tabelaReajusteColaboradorId, empresa);

		else if (tipoReajuste.equals(TipoReajuste.INDICE))
			cancelarPorIndice(tabelaReajusteColaboradorId, empresa);
	}
	
	private void cancelarColaborador(Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception
	{
		getDao().updateSetAprovada(tabelaReajusteColaboradorId, false);

		Collection<TSituacao> situacaoIntegrados = historicoColaboradorManager.findHistoricosByTabelaReajuste(tabelaReajusteColaboradorId, empresa);
		Collection<TSituacao> situacaosTmp = new ArrayList<TSituacao>(); 
		
		if(!situacaoIntegrados.isEmpty())
		{
			Long[] historicoIds = new Long[situacaoIntegrados.size()];

			int cont = 0;
			for (TSituacao situacao : situacaoIntegrados)
			{
				historicoIds[cont++] = situacao.getId().longValue();
				
				if(situacao.getEmpregadoCodigoAC() != null)
					situacaosTmp.add(situacao);
			}
			
			historicoColaboradorManager.remove(historicoIds);
			
			// garante que um erro no banco do RH levantará uma Exception antes de alterar o outro banco.
			getDao().getHibernateTemplateByGenericDao().flush();

			if(empresa.isAcIntegra())
			{
				TSituacao[] situacaos = prepareDeleteSituacao(situacaosTmp);
				acPessoalClientTabelaReajuste.deleteHistoricoColaboradorAC(empresa, situacaos);
			}
		}
	}
	
	private void cancelarPorFaixaSalarial(Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception
	{
		Collection<FaixaSalarialHistorico> historicos = faixaSalarialHistoricoManager.findByTabelaReajusteId(tabelaReajusteColaboradorId);
		
		for (FaixaSalarialHistorico faixaSalarialHistorico : historicos) 
			faixaSalarialHistoricoManager.remove(faixaSalarialHistorico.getId(), empresa, true);
		
		getDao().updateSetAprovada(tabelaReajusteColaboradorId, false);
	}

	private void cancelarPorIndice(Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception
	{
		Collection<IndiceHistorico> historicos = indiceHistoricoManager.findByTabelaReajusteId(tabelaReajusteColaboradorId);
		
		for (IndiceHistorico indiceHistorico : historicos) 
			indiceHistoricoManager.remove(indiceHistorico.getId());
		
		getDao().updateSetAprovada(tabelaReajusteColaboradorId, false);
	}

	public TSituacao[] prepareDeleteSituacao(Collection<TSituacao> situacaosTmp) throws Exception
	{
		TSituacao[] situacaos = new TSituacao[situacaosTmp.size()];
		situacaosTmp.toArray(situacaos);
		return situacaos;
	}

	public void verificaDataHistoricoColaborador(Long tabelaReajusteColaboradorId, Date data) throws Exception
	{
		Collection<HistoricoColaborador> historicoColaboradors = historicoColaboradorManager.findColaboradoresByTabelaReajusteData(tabelaReajusteColaboradorId, data);
		
		if(historicoColaboradors != null && !historicoColaboradors.isEmpty())
		{
			StringBuilder colaboradoresComHistoricoNaData = new StringBuilder();
			for (HistoricoColaborador historicoColaborador : historicoColaboradors)
			{
				colaboradoresComHistoricoNaData.append("- "+historicoColaborador.getColaborador().getNome() + "<br>");
			}
			
			throw new FortesException("Já existe(m) histórico(s) na data " + DateUtil.formataDiaMesAno(data) + " para o(s) colaborador(es) abaixo: <br>" + colaboradoresComHistoricoNaData.toString());
		}
	}

	public void verificaDataHistoricoFaixaSalarial(Long tabelaReajusteColaboradorId, Date data) throws Exception
	{
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = faixaSalarialHistoricoManager.findByTabelaReajusteIdData(tabelaReajusteColaboradorId, data);
		
		if(faixaSalarialHistoricos != null && !faixaSalarialHistoricos.isEmpty())
		{
			StringBuilder faixasSalariaisComHistoricoNaData = new StringBuilder();
			for (FaixaSalarialHistorico faixaSalarialHistorico : faixaSalarialHistoricos)
			{
				faixasSalariaisComHistoricoNaData.append("- "+faixaSalarialHistorico.getDescricao() + "<br>");
			}
			
			throw new FortesException("Já existe(m) histórico(s) na data " + DateUtil.formataDiaMesAno(data) + " para a(s) faixa(s) salarial(is) abaixo: <br>" + faixasSalariaisComHistoricoNaData.toString());
		}
	}
	
	public void verificaDataHistoricoIndice(Long tabelaReajusteColaboradorId, Date data) throws Exception
	{
		Collection<IndiceHistorico> indiceHistoricos = indiceHistoricoManager.findByTabelaReajusteIdData(tabelaReajusteColaboradorId, data);
		
		if(indiceHistoricos != null && !indiceHistoricos.isEmpty())
		{
			StringBuilder indicesComHistoricoNaData = new StringBuilder();
			for (IndiceHistorico indiceHistorico : indiceHistoricos)
			{
				indicesComHistoricoNaData.append("- "+indiceHistorico.getIndice().getNome() + "<br>");
			}
			
			throw new FortesException("Já existe(m) histórico(s) na data " + DateUtil.formataDiaMesAno(data) + " para o(s) índice(s) abaixo: <br>" + indicesComHistoricoNaData.toString());
		}
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}
	
	public void setReajusteColaboradorManager(	ReajusteColaboradorManager reajusteColaboradorManager) 
	{
		this.reajusteColaboradorManager = reajusteColaboradorManager;
	}

	public Integer getCount(Long empresaId)
	{
		return getDao().getCount(empresaId);
	}

	public Collection<TabelaReajusteColaborador> findAllList(int page, int PagingSize, Long empresaId)
	{
		return getDao().findAllList(page, PagingSize, empresaId);
	}

	public TabelaReajusteColaborador findByIdProjection(Long tabelaReajusteColaboradorId)
	{
		return getDao().findByIdProjection(tabelaReajusteColaboradorId);
	}

	public void setAcPessoalClientTabelaReajuste(AcPessoalClientTabelaReajusteInterface acPessoalClientTabelaReajuste)
	{
		this.acPessoalClientTabelaReajuste = acPessoalClientTabelaReajuste;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setQuantidadeLimiteColaboradoresPorCargoManager(QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager) 
	{
		this.quantidadeLimiteColaboradoresPorCargoManager = quantidadeLimiteColaboradoresPorCargoManager;
	}

	public void setReajusteFaixaSalarialManager(ReajusteFaixaSalarialManager reajusteFaixaSalarialManager) 
	{
		this.reajusteFaixaSalarialManager = reajusteFaixaSalarialManager;
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager) 
	{
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}

	public void setReajusteIndiceManager(ReajusteIndiceManager reajusteIndiceManager)
	{
		this.reajusteIndiceManager = reajusteIndiceManager;
	}

	public void setIndiceHistoricoManager(IndiceHistoricoManager indiceHistoricoManager)
	{
		this.indiceHistoricoManager = indiceHistoricoManager;
	}
}