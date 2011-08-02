package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.HistoricoColaboradorUtil;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajusteInterface;

public class TabelaReajusteColaboradorManagerImpl extends GenericManagerImpl<TabelaReajusteColaborador, TabelaReajusteColaboradorDao> implements TabelaReajusteColaboradorManager
{
	private ReajusteColaboradorManager reajusteColaboradorManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private AcPessoalClientTabelaReajusteInterface acPessoalClientTabelaReajuste;
	private ColaboradorManager colaboradorManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;

	//private final Boolean APROVADA = true;  - Descomentar se for necessário criar o método para encontrar tabelas aprovadas.
	private final Boolean NAO_APROVADA = false;
	private final Boolean TODAS = null;
	
	@Override
	public void update(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		getDao().update(tabelaReajusteColaborador);
	}
	
	public void remove(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		reajusteColaboradorManager.deleteByColaboradoresTabelaReajuste(null, tabelaReajusteColaborador.getId());
		getDao().remove(tabelaReajusteColaborador.getId());
	}

	public Collection<TabelaReajusteColaborador> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId, TODAS);
	}

	public Collection<TabelaReajusteColaborador> findAllSelectByNaoAprovada(Long empresaId)
	{
		return getDao().findAllSelect(empresaId, NAO_APROVADA);
	}

	public void marcaUltima(Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors)
	{
		CollectionUtil<TabelaReajusteColaborador> cu = new CollectionUtil<TabelaReajusteColaborador>();
		tabelaReajusteColaboradors = cu.sortCollectionDate(tabelaReajusteColaboradors, "data");

		for(TabelaReajusteColaborador tabelaReajusteColaborador :tabelaReajusteColaboradors)
		{
			if(tabelaReajusteColaborador.isAprovada())
			{
				tabelaReajusteColaborador.setEhUltimo(true);
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void aplicar(TabelaReajusteColaborador tabelaReajusteColaborador, Empresa empresa, Collection<ReajusteColaborador> reajustes) throws IntegraACException, Exception, ColecaoVaziaException
	{
		if(tabelaReajusteColaborador != null && (reajustes == null || reajustes.size() == 0))
			throw new ColecaoVaziaException("Nenhum Colaborador no Reajuste");
		
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

			if(reajuste.getAmbienteProposto() != null && reajuste.getAmbienteProposto().getId() != null)
				historicoColaborador.setAmbiente(reajuste.getAmbienteProposto());

			if(reajuste.getFuncaoProposta() != null && reajuste.getFuncaoProposta().getId() != null)
				historicoColaborador.setFuncao(reajuste.getFuncaoProposta());

			historicoColaborador.setHistoricoAnterior(historicoAtual);
			if (tabelaReajusteColaborador.isDissidio())
				historicoColaborador.setMotivo(MotivoHistoricoColaborador.DISSIDIO);
			else
				historicoColaborador.setMotivo(HistoricoColaboradorUtil.getMotivoReajuste(reajuste, historicoColaborador));

			if(empresa.isAcIntegra() && !reajuste.getColaborador().isNaoIntegraAc())
				historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);

			if(historicoColaboradorManager.verifyExists(new String[]{"data", "colaborador.id"}, new Object[]{historicoColaborador.getData(), historicoColaborador.getColaborador().getId()}))
				throw new Exception("Colaborador já possui um histórico na data do Planejamento de Realinhamento.");

			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), empresa.getId(), reajuste.getColaborador().getId());
			
			historicoColaborador = historicoColaboradorManager.save(historicoColaborador);

			if(!reajuste.getColaborador().isNaoIntegraAc())
				historicosAc.add(historicoColaborador);
		}

		getDao().updateSetAprovada(tabelaReajusteColaborador.getId(), true);
		
		// garante que um erro no banco do RH levantará uma Exception antes de alterar o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush();

		if(empresa.isAcIntegra())
			acPessoalClientTabelaReajuste.aplicaReajuste(historicosAc, empresa);
	}

	public void cancelar(Long tabelaReajusteColaboradorId, Empresa empresa) throws Exception
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
				colaboradoresComHistoricoNaData.append(historicoColaborador.getColaborador().getNome() + "<br>");
			}
			
			throw new Exception("Já existe(m) Histórico(s) na data " + DateUtil.formataDiaMesAno(data) + " para esse(s) colaborador(es): <br>" + colaboradoresComHistoricoNaData.toString());
		}
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}
	public void setReajusteColaboradorManager(
			ReajusteColaboradorManager reajusteColaboradorManager) {
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

	public void setQuantidadeLimiteColaboradoresPorCargoManager(QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager) {
		this.quantidadeLimiteColaboradoresPorCargoManager = quantidadeLimiteColaboradoresPorCargoManager;
	}

}