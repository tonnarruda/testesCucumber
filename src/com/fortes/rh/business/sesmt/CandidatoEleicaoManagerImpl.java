package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.sesmt.CandidatoEleicaoDao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.relatorio.LinhaCedulaEleitoralRelatorio;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

public class CandidatoEleicaoManagerImpl extends GenericManagerImpl<CandidatoEleicao, CandidatoEleicaoDao> implements CandidatoEleicaoManager
{
	private PlatformTransactionManager transactionManager;
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EleicaoManager eleicaoManager;
		
	public Collection<CandidatoEleicao> findByEleicao(Long eleicaoId)
	{
		return getDao().findByEleicao(eleicaoId, false, false);
	}
	
	/**
	 * Participações de um colaborador como candidato, em todas as eleições. */
	public Collection<CandidatoEleicao> findByColaborador(Long colaboradorId)
	{
		return getDao().findByColaborador(colaboradorId);
	}

	public void save(String[] candidatosCheck, Eleicao eleicao)throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			CollectionUtil<Colaborador> util = new CollectionUtil<Colaborador>();
			Collection<Colaborador> colaboradores = util.convertArrayStringToCollection(Colaborador.class, candidatosCheck);
			Collection<CandidatoEleicao> candidatoEleicaos = findByEleicao(eleicao.getId());

			for (Colaborador colaborador : colaboradores)
			{
				CandidatoEleicao candidatoEleicao = new CandidatoEleicao(eleicao, colaborador);
				
				if(!candidatoEleicaos.contains(candidatoEleicao))
					save(candidatoEleicao);
			}

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw e;
		}
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Collection<CandidatoEleicao> getColaboradoresByEleicao(Long eleicaoId, Long empresaId) throws Exception
	{
		Collection<CandidatoEleicao> candidatoEleicaos = findByEleicao(eleicaoId);
		
		if(!candidatoEleicaos.isEmpty())
		{
			Collection<Long> colaboradorIds = new ArrayList<Long>();			
			
			for (CandidatoEleicao candidatoEleicao : candidatoEleicaos)
			{
				colaboradorIds.add(candidatoEleicao.getCandidato().getId());
			}

			Collection<Colaborador> colaboradors = colaboradorManager.findByIdHistoricoAtual(colaboradorIds);
			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativa(empresaId, AreaOrganizacional.TODAS, null);
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

			for (CandidatoEleicao candidatoEleicao : candidatoEleicaos)
			{
				for (Colaborador colaborador : colaboradors)
				{
					if(candidatoEleicao.getCandidato().equals(colaborador))
					{
						candidatoEleicao.setCandidato(colaborador);
						colaborador.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, colaborador.getAreaOrganizacional().getId()));
						break;
					}					
				}
			}
		}
		
		return candidatoEleicaos;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	/**
	 * TODO mover esse código para EleicaoManager
	 * 
	 * EleicaoManager deveria conter CandidatoEleicaoManager, não o contrário. 
	 * Movendo este código vamos tirar os SpringUtil.getBean()... de EleicaoManager.
	 * 
	 * Caso semelhante ao de ColaboradorRespostaManager
	 * @see ColaboradorRespostaManager.savePerformanceDaAvaliacao()
	 */
	public void saveVotosEleicao(String[] eleitosIds, String[] qtdVotos, String[] idCandidatoEleicaos, Eleicao eleicao) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			saveEleitos(eleitosIds, idCandidatoEleicaos);
			saveVotos(qtdVotos, idCandidatoEleicaos);
			eleicaoManager.updateVotos(eleicao);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw e;
		}
	}

	private void saveVotos(String[] qtdVotos, String[] idCandidatoEleicaos)
	{
		if(idCandidatoEleicaos != null)
		{
			Long[] ids = LongUtil.arrayStringToArrayLong(idCandidatoEleicaos);
			for (int i = 0; i < ids.length; i++)
			{
				int qtd = 0;
				if(StringUtils.isNotBlank(qtdVotos[i]))
					qtd = Integer.parseInt(qtdVotos[i]);
				
				getDao().setQtdVotos(qtd, ids[i]);
			}
		}
	}

	private void saveEleitos(String[] eleitosIds, String[] idCandidatoEleicaos)
	{
		//Seta todos como false primeiro
		if(idCandidatoEleicaos != null)
		{
			getDao().setEleito(false, LongUtil.arrayStringToArrayLong(idCandidatoEleicaos));
			
			//seta apenas os selecionados
			if(eleitosIds != null)
				getDao().setEleito(true, LongUtil.arrayStringToArrayLong(eleitosIds));
		}
	}

	public void setEleicaoManager(EleicaoManager eleicaoManager)
	{
		this.eleicaoManager = eleicaoManager;
	}

	public Collection<LinhaCedulaEleitoralRelatorio> montaCedulas(Collection<CandidatoEleicao> candidatoEleicaos) throws Exception
	{
		if(candidatoEleicaos.isEmpty())
			throw new Exception("Não existe candidatos.");

		int cont = 1;
		StringBuilder linhasDaCedula = new StringBuilder();
		for (CandidatoEleicao candidatoEleicao: candidatoEleicaos)
		{
			if (candidatoEleicao.getCandidato().getNome().equals(candidatoEleicao.getCandidato().getNomeComercial()))
				linhasDaCedula.append("(  )  " + cont++ + " - " + candidatoEleicao.getCandidato().getNome() + " - " + getNomeCargo(candidatoEleicao) + "\n");
			else
				linhasDaCedula.append("(  )  " + cont++ + " - " + candidatoEleicao.getCandidato().getNome() + " (" + candidatoEleicao.getCandidato().getNomeComercial() + ")" + " - " + getNomeCargo(candidatoEleicao) + "\n");
		}
		
		Collection<LinhaCedulaEleitoralRelatorio> cedulas = new ArrayList<LinhaCedulaEleitoralRelatorio>(10);
		for (int i = 0; i < 10; i++)
		{
			LinhaCedulaEleitoralRelatorio cedulaCandidatos = new LinhaCedulaEleitoralRelatorio(linhasDaCedula.toString());
			cedulas.add(cedulaCandidatos);
		}
		
		return cedulas;
	}

	private String getNomeCargo(CandidatoEleicao candidatoEleicao) 
	{
		try {
			return candidatoEleicao.getCandidato().getFaixaSalarial().getCargo().getNome();
			} catch (Exception e) {
			return "";			
		}
	}
	
	
	public void removeByEleicao(Long eleicaoId)
	{
		getDao().removeByEleicao(eleicaoId);
	}
}