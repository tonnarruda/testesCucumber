package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.cargosalario.ReajusteColaboradorDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.MathUtil;
import com.fortes.rh.util.RelatorioUtil;

@Component
@SuppressWarnings("unchecked")
public class ReajusteColaboradorManagerImpl extends GenericManagerImpl<ReajusteColaborador, ReajusteColaboradorDao> implements ReajusteColaboradorManager
{
	@Autowired private IndiceManager indiceManager;
	@Autowired private FaixaSalarialManager faixaSalarialManager;
	@Autowired private PlatformTransactionManager transactionManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private HistoricoColaboradorManager historicoColaboradorManager;
	@Autowired private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	@Autowired private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	
	@Autowired
	ReajusteColaboradorManagerImpl(ReajusteColaboradorDao dao) {
		setDao(dao);
	}
	
	public Collection<ReajusteColaborador> findByGruposAreas(HashMap<Object, Object> parametros)
	{
		int filtrarPor = Integer.parseInt(parametros.get("filtrarPor").toString());
		Collection<ReajusteColaborador> reajustes = getDao().findByIdEstabelecimentoAreaGrupo((Long) parametros.get("tabela"),(Collection<Long>) parametros.get("estabelecimentos"),  (Collection<Long>)parametros.get("areas"), (Collection<Long>)parametros.get("grupos"), filtrarPor);

		return reajustes;
	}

	public Collection<ReajusteColaborador> findByIdEstabelecimentoAreaGrupo(Long tabelaReajusteColaboradorId, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, int filtraPor)
	{
		return getDao().findByIdEstabelecimentoAreaGrupo(tabelaReajusteColaboradorId, estabelecimentoIds, areaIds, grupoIds, filtraPor);
	}
	
	public void validaSolicitacaoReajuste(ReajusteColaborador reajusteColaborador) throws Exception 
	{
		if(areaOrganizacionalManager.verificaMaternidade(reajusteColaborador.getAreaOrganizacionalProposta().getId(), null))
			throw new FortesException("Não é possível fazer solicitações para áreas que possuem sub-áreas.");
		
		TabelaReajusteColaborador tabelaReajusteColaboradorTemp = tabelaReajusteColaboradorManager.findByIdProjection(reajusteColaborador.getTabelaReajusteColaborador().getId());
		if(historicoColaboradorManager.verifyExists(new String[]{"data", "colaborador.id"}, new Object[]{tabelaReajusteColaboradorTemp.getData(), reajusteColaborador.getColaborador().getId()}))
			throw new FortesException(reajusteColaborador.getColaborador().getNome()+" já possui um histórico na mesma data do planejamento de realinhamento.");
		
		boolean existeColaboradorAndTabela = getDao().verifyExists(new String[]{"colaborador.id", "tabelaReajusteColaborador.id"}, new Object[]{reajusteColaborador.getColaborador().getId(), reajusteColaborador.getTabelaReajusteColaborador().getId()});
		if(existeColaboradorAndTabela)
			throw new FortesException("Já existe uma solicitação de reajuste para " + reajusteColaborador.getColaborador().getNome() + " nesta tabela.");
		
	}
	
	public void insertSolicitacaoReajuste(ReajusteColaborador reajusteColaborador, Long empresaId, Colaborador colaborador) throws Exception
	{
		try
		{
			ajustaTipoSalario(reajusteColaborador);
			save(reajusteColaborador);
			
			gerenciadorComunicacaoManager.enviaAvisoAoCadastrarSolicitacaoRealinhamentoColaborador(empresaId, colaborador, reajusteColaborador.getTabelaReajusteColaborador().getId());
		}
		catch(Exception e)
		{
			throw new Exception("Erro ao inserir solicitação de ajuste.");
		}
	}

	private void ajustaTipoSalario(ReajusteColaborador reajusteColaborador) throws Exception
	{
		int tipoSalario = reajusteColaborador.getTipoSalarioProposto();
		
		switch (tipoSalario)
		{
			case TipoAplicacaoIndice.CARGO:
				reajusteColaborador.setIndiceProposto(null);
				reajusteColaborador.setQuantidadeIndiceProposto(0.0);
				reajusteColaborador.setSalarioProposto(0d);
			break;
			case TipoAplicacaoIndice.VALOR:
				reajusteColaborador.setIndiceProposto(null);
				reajusteColaborador.setQuantidadeIndiceProposto(0.0);
				if(reajusteColaborador.getSalarioProposto() == null || reajusteColaborador.getSalarioProposto() == 0)
					throw new Exception("Valor do salário não informado.");
			break;
			case TipoAplicacaoIndice.INDICE:
				reajusteColaborador.setSalarioProposto(0d);
				if(reajusteColaborador.getIndiceProposto() == null || reajusteColaborador.getIndiceProposto().getId() == null)
					throw new Exception("Índice não informado.");
			break;
			default:
				throw new Exception("Tipo de salário não identificado.");
		}
	}

	public Collection<ReajusteColaborador> aplicarDissidio(Collection<HistoricoColaborador> historicoColaboradores, TabelaReajusteColaborador tabelaReajusteColaborador, char dissidioPor, Double valorDissidio) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			Collection<ReajusteColaborador> reajusteColaboradors = new ArrayList<ReajusteColaborador>();

			Long[] colaboradorIds = getColaboradoresIdsByReajuste(historicoColaboradores);

			if(colaboradorIds.length != 0)
				getDao().deleteByColaboradoresTabelaReajuste(colaboradorIds, tabelaReajusteColaborador.getId());

			for (HistoricoColaborador historicoColaborador : historicoColaboradores)
			{
				ReajusteColaborador reajusteColaborador = new ReajusteColaborador();

				reajusteColaborador.setTipoSalarioAtual(historicoColaborador.getTipoSalario());
				reajusteColaborador.setTipoSalarioProposto(historicoColaborador.getTipoSalario());

				if(historicoColaborador.getAmbiente() != null && historicoColaborador.getAmbiente().getId() != null)
				{
					reajusteColaborador.setAmbienteAtual(historicoColaborador.getAmbiente());
					reajusteColaborador.setAmbienteProposto(historicoColaborador.getAmbiente());
				}
				if(historicoColaborador.getAreaOrganizacional() != null && historicoColaborador.getAreaOrganizacional().getId() != null)
				{
					reajusteColaborador.setAreaOrganizacionalAtual(historicoColaborador.getAreaOrganizacional());
					reajusteColaborador.setAreaOrganizacionalProposta(historicoColaborador.getAreaOrganizacional());
				}
				if(historicoColaborador.getEstabelecimento() != null && historicoColaborador.getEstabelecimento().getId() != null)
				{
					reajusteColaborador.setEstabelecimentoAtual(historicoColaborador.getEstabelecimento());
					reajusteColaborador.setEstabelecimentoProposto(historicoColaborador.getEstabelecimento());
				}

				if(historicoColaborador.getFuncao() != null && historicoColaborador.getFuncao().getId() != null)
				{
					reajusteColaborador.setFuncaoAtual(historicoColaborador.getFuncao());
					reajusteColaborador.setFuncaoProposta(historicoColaborador.getFuncao());
				}

				if(historicoColaborador.getFaixaSalarial() != null)
				{
					reajusteColaborador.setFaixaSalarialAtual(historicoColaborador.getFaixaSalarial());
					reajusteColaborador.setFaixaSalarialProposta(historicoColaborador.getFaixaSalarial());
				}

				if(historicoColaborador.getColaborador() != null && historicoColaborador.getColaborador().getId() != null)
					reajusteColaborador.setColaborador(historicoColaborador.getColaborador());

				if(historicoColaborador.getSalario() != null)
					reajusteColaborador.setSalarioAtual(historicoColaborador.getSalario());

				reajusteColaborador.setSalarioProposto(MathUtil.calculaDissidio(dissidioPor, valorDissidio, historicoColaborador.getSalario()));

				if(tabelaReajusteColaborador != null && tabelaReajusteColaborador.getId() != null)
					reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);

				reajusteColaborador = getDao().save(reajusteColaborador);
				reajusteColaboradors.add(reajusteColaborador);
			}

			transactionManager.commit(status);
			return reajusteColaboradors;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			transactionManager.rollback(status);
			throw new Exception();
		}

	}

	private Long[] getColaboradoresIdsByReajuste(Collection<HistoricoColaborador> historicoColaboradores)
	{
		Long[] colaboradorIds = new Long[historicoColaboradores.size()];
		int cont = 0;

		for (HistoricoColaborador historicoColaborador : historicoColaboradores)
		{
			if(historicoColaborador.getColaborador() != null && historicoColaborador.getColaborador().getId() != null)
				colaboradorIds[cont++] = historicoColaborador.getColaborador().getId();
		}

		return colaboradorIds;
	}

	public void deleteByColaboradoresTabelaReajuste(Long[] colaboradorIds, Long tabelaReajusteColaboradorId)
	{
		getDao().deleteByColaboradoresTabelaReajuste(colaboradorIds, tabelaReajusteColaboradorId);
	}

	public ReajusteColaborador getSituacaoReajusteColaborador(Long reajusteColaboradorId)
	{
		return getDao().getSituacaoReajusteColaborador(reajusteColaboradorId);
	}
	
	public ReajusteColaborador findByIdProjection(Long reajusteColaboradorId)
	{
		return getDao().findByIdProjection(reajusteColaboradorId);
	}

	@Override
	@Deprecated
	public void update(ReajusteColaborador reajusteColaborador)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: updateReajusteColaborador(reajusteColaborador).");
	}

	public void updateReajusteColaborador(ReajusteColaborador reajusteColaborador) throws Exception
	{
		if(reajusteColaborador.getFuncaoAtual() != null && reajusteColaborador.getFuncaoAtual().getId() == null)
			reajusteColaborador.setFuncaoAtual(null);

		if(reajusteColaborador.getAmbienteAtual() != null && reajusteColaborador.getAmbienteAtual().getId() == null)
			reajusteColaborador.setAmbienteAtual(null);

		if(reajusteColaborador.getFuncaoProposta() != null && reajusteColaborador.getFuncaoProposta().getId() != null && reajusteColaborador.getFuncaoProposta().getId() == -1L)
			reajusteColaborador.setFuncaoProposta(null);

		if(reajusteColaborador.getAmbienteProposto() != null && reajusteColaborador.getAmbienteProposto().getId() != null && reajusteColaborador.getAmbienteProposto().getId() == -1L)
			reajusteColaborador.setAmbienteProposto(null);

		if(reajusteColaborador.getIndiceAtual() != null && reajusteColaborador.getIndiceAtual().getId() == null)
			reajusteColaborador.setIndiceAtual(null);

		ajustaTipoSalario(reajusteColaborador);

		super.update(reajusteColaborador);
	}

	public Collection<ReajusteColaborador> ordenaPorEstabelecimentoAreaOrGrupoOcupacional(Long empresaId, Collection<ReajusteColaborador> reajusteColaboradors, String filtro) throws Exception
	{
		//monta familia das areas
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);
		areas = areaOrganizacionalManager.montaFamilia(areas);
		
		for (ReajusteColaborador reajusteTmp : reajusteColaboradors)
		{
			reajusteTmp.setAreaOrganizacionalProposta(areaOrganizacionalManager.getAreaOrganizacional(areas, reajusteTmp.getAreaOrganizacionalProposta().getId()));
			reajusteTmp.setAreaOrganizacionalAtual(areaOrganizacionalManager.getAreaOrganizacional(areas, reajusteTmp.getAreaOrganizacionalAtual().getId()));
		}
		
		//Ordena por Area e Estabelecimento
		if(filtro.equals("1"))
		{
			CollectionUtil<ReajusteColaborador> cu1 = new CollectionUtil<ReajusteColaborador>();
			reajusteColaboradors = cu1.sortCollectionStringIgnoreCase(reajusteColaboradors, "descricaoEstabelecimentoAreaOrganizacionalPropostos");
		}
		else if(filtro.equals("2"))
		{
			CollectionUtil<ReajusteColaborador> cu1 = new CollectionUtil<ReajusteColaborador>();
			reajusteColaboradors = cu1.sortCollectionStringIgnoreCase(reajusteColaboradors, "faixaSalarialProposta.cargo.grupoOcupacional.nome");
		}

		return reajusteColaboradors;
	}

	public Map<String, Object> getParametrosRelatorio(String nomeRelatorio, Empresa empresa, String nomeFiltro)
	{
		Map<String, Object> parametros = RelatorioUtil.getParametrosRelatorio(nomeRelatorio, empresa, nomeFiltro);

		return parametros;
	}

	public void updateFromHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		getDao().updateFromHistoricoColaborador(historicoColaborador);
	}

	public Double calculaSalarioProposto(ReajusteColaborador reajusteColaborador)
	{
		switch (reajusteColaborador.getTipoSalarioProposto())
		{
			case TipoAplicacaoIndice.CARGO:
				reajusteColaborador.setFaixaSalarialProposta(faixaSalarialManager.findHistorico(reajusteColaborador.getFaixaSalarialProposta().getId(), new Date()));
				break;
			case TipoAplicacaoIndice.INDICE:
				reajusteColaborador.setIndiceProposto(indiceManager.findHistorico(reajusteColaborador.getIndiceProposto().getId(), new Date()));
				break;
		}

		return reajusteColaborador.getSalarioProposto();
	}
}