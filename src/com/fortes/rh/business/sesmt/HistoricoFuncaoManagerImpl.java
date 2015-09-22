package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class HistoricoFuncaoManagerImpl extends GenericManagerImpl<HistoricoFuncao, HistoricoFuncaoDao> implements HistoricoFuncaoManager
{
	private FuncaoManager funcaoManager;
	private AbstractPlatformTransactionManager transactionManager;
	private ExameManager exameManager;
	private EpiManager epiManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	
	public void setTransactionManager(AbstractPlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public void saveFuncaoHistorico(Funcao funcao, HistoricoFuncao historicoFuncao, Long[] examesChecked, Long[] episChecked, String[] riscoChecks, Collection<RiscoFuncao> riscoFuncoes) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			funcaoManager.save(funcao);

			historicoFuncao.setExames(addExames(examesChecked));
			historicoFuncao.setFuncao(funcao);
			
			CollectionUtil<Epi> collectionUtil = new CollectionUtil<Epi>(); 
			historicoFuncao.setEpis(collectionUtil.convertArrayLongToCollection(Epi.class, episChecked));
			
			Long[] riscosMarcados = LongUtil.arrayStringToArrayLong(riscoChecks);
			
			if (historicoFuncao.getId() != null)
				riscoFuncaoManager.removeByHistoricoFuncao(historicoFuncao.getId());
			
			Collection<RiscoFuncao> riscoFuncoesSelecionados = new ArrayList<RiscoFuncao>();
			
			for (Long riscoId : riscosMarcados)
			{
				for (RiscoFuncao riscoFuncao : riscoFuncoes)
				{
					if (riscoFuncao != null && riscoFuncao.getRisco() != null && riscoId.equals(riscoFuncao.getRisco().getId()))
					{
						riscoFuncao.setHistoricoFuncao(historicoFuncao);
						riscoFuncoesSelecionados.add(riscoFuncao);
					}
				}
			}
			
			historicoFuncao.setRiscoFuncaos(riscoFuncoesSelecionados);
			
			getDao().save(historicoFuncao);

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);

			funcao.setId(null);
			historicoFuncao = null;
			throw e;
		}
	}

	private Collection<Exame> addExames(Long[] examesChecked)
	{
		Collection<Exame> exames = new ArrayList<Exame>();
		if(examesChecked != null && examesChecked.length > 0)
		{
			Exame exameTemp;

			for (Long exameId : examesChecked)
			{
				exameTemp = new Exame();
				exameTemp.setId(exameId);

				exames.add(exameTemp);
			}
		}

		return exames;
	}

	public Collection<HistoricoFuncao> findHistoricoFuncaoColaborador(Collection<HistoricoColaborador> historicosColaborador, Date dataLimite, Date dataDesligamento)
	{
		Collection<HistoricoFuncao> historicosFuncaoRetorno = new LinkedList<HistoricoFuncao>();
		Date dataInicio;
		Date dataFim;
		HistoricoFuncao historicoFuncaoAnterior = null;
		for (HistoricoColaborador historicoColaborador : historicosColaborador)
		{
			Collection<Long> idsFuncoes = new ArrayList<Long>();
			idsFuncoes.add(historicoColaborador.getFuncao().getId());

			dataInicio = historicoColaborador.getData();
			if (historicoColaborador.getDataProximoHistorico() != null)
				dataFim = DateUtil.retornaDataDiaAnterior(historicoColaborador.getDataProximoHistorico());
			else
				dataFim = dataLimite;

			Collection<HistoricoFuncao> historicoFuncaosTemp = getDao().findHistoricoByFuncoesId(idsFuncoes, dataInicio, dataFim);

			for (HistoricoFuncao historicoFuncao : historicoFuncaosTemp)
			{
				if (dataInicio.compareTo(historicoFuncao.getData()) >= 0)
					historicoFuncao.setData(dataInicio);

				if (historicoFuncaoAnterior!=null)
					historicoFuncaoAnterior.setDataProximoHistorico(DateUtil.retornaDataDiaAnterior(historicoFuncao.getData()));

				historicoFuncao.setDataDesligamento(dataDesligamento);
				historicoFuncaoAnterior = historicoFuncao;
			}

			historicosFuncaoRetorno.addAll(historicoFuncaosTemp);
		}
		return historicosFuncaoRetorno;
	}

	@SuppressWarnings("unchecked")
	public Collection<HistoricoFuncao> inserirPeriodos(Collection<HistoricoFuncao> historicos)
	{
        HistoricoFuncao historico;
        HistoricoFuncao proxHistorico = new HistoricoFuncao();
        List<HistoricoFuncao> listHistoricos = new ArrayList<HistoricoFuncao>();
        listHistoricos = new CollectionUtil<HistoricoFuncao>().convertCollectionToList(historicos);
        for (int i = 0; i < listHistoricos.size(); i++)
		{
        	historico = (HistoricoFuncao) listHistoricos.get(i);

        	if(i+1<listHistoricos.size())
	        	if(listHistoricos.get(i+1)!=null)
					proxHistorico = (HistoricoFuncao) listHistoricos.get(i+1);

        	if (proxHistorico!=null && proxHistorico.getData()!=null)
        		historico.setDataProximoHistorico(DateUtil.retornaDataDiaAnterior(proxHistorico.getData()));

        	proxHistorico = null;
		}
		return historicos;

	}


	public Collection<HistoricoFuncao> getUltimoHistoricosByDateFuncaos(Collection<Long> funcaoIds, Date data)
	{
		Collection<HistoricoFuncao> historicoFuncaos = new ArrayList<HistoricoFuncao>();

		if (funcaoIds.size() > 0)
			historicoFuncaos = getDao().getHistoricosByDateFuncaos(funcaoIds, data);

		Collection<HistoricoFuncao> historicoFuncaosRetorno = new ArrayList<HistoricoFuncao>();

		Long historicoFuncaoId = 0L;
		for (HistoricoFuncao historicoFuncao : historicoFuncaos)
		{
			if(historicoFuncao.getFuncao() != null && !historicoFuncaoId.equals(historicoFuncao.getFuncao().getId()))
			{
				historicoFuncaosRetorno.add(historicoFuncao);
				historicoFuncaoId = historicoFuncao.getFuncao().getId();
			}
		}

		return historicoFuncaosRetorno;
	}

	public void saveHistorico(HistoricoFuncao historicoFuncao, Long[] examesChecked, Long[] episChecked, Long[] riscoChecks, Collection<RiscoFuncao> riscoFuncoes) throws FortesException, Exception 
	{
		if (this.findByData(historicoFuncao.getData(), historicoFuncao.getId(), historicoFuncao.getFuncao().getId()) != null)
			throw new FortesException("Já existe um histórico para a data informada");			
		
		historicoFuncao.setExames(addExames(examesChecked));
		CollectionUtil<Epi> collectionUtil = new CollectionUtil<Epi>(); 
		historicoFuncao.setEpis(collectionUtil.convertArrayLongToCollection(Epi.class, episChecked));
		
		if (historicoFuncao.getId() != null)
			riscoFuncaoManager.removeByHistoricoFuncao(historicoFuncao.getId());
		
		Collection<RiscoFuncao> riscoFuncoesSelecionados = new ArrayList<RiscoFuncao>();
		
		if (riscoChecks != null)
		{
			for (Long riscoId : riscoChecks)
			{
				for (RiscoFuncao riscoFuncao : riscoFuncoes)
				{
					if (riscoFuncao != null && riscoFuncao.getRisco() != null && riscoId.equals(riscoFuncao.getRisco().getId()))
					{
						riscoFuncao.setHistoricoFuncao(historicoFuncao);
						riscoFuncoesSelecionados.add(riscoFuncao);
					}
				}
			}
		}
		
		historicoFuncao.setRiscoFuncaos(riscoFuncoesSelecionados);
		
		if (historicoFuncao.getId() == null)
			save(historicoFuncao);
		else
			update(historicoFuncao);
	}

	public HistoricoFuncao findByData(Date data, Long historicoFuncaoId, Long funcaoId) 
	{
		return getDao().findByData(data, historicoFuncaoId, funcaoId);
	}

	public void removeByFuncoes(Long[] funcaoIds)
	{
		removeEpisDaFuncao(funcaoIds);
		getDao().removeByFuncoes(funcaoIds);
	}

	private void removeEpisDaFuncao(Long[] funcaoIds) 
	{
		for (Long funcaoId : funcaoIds) 
		{
			Collection<HistoricoFuncao> histFuncaos = getDao().findByFuncao(funcaoId);
			for (HistoricoFuncao historicoFuncao : histFuncaos) 
			{
				historicoFuncao.setEpis(null);
				update(historicoFuncao);
			}
		}
	}

	public HistoricoFuncao findByIdProjection(Long historicoFuncaoId)
	{
		HistoricoFuncao historicoFuncao = getDao().findByIdProjection(historicoFuncaoId);

		historicoFuncao.setExames(exameManager.findByHistoricoFuncao(historicoFuncao.getId()));
		historicoFuncao.setEpis(epiManager.findByHistoricoFuncao(historicoFuncao.getId()));

		return historicoFuncao;
	}

	public Collection<Funcao> findByFuncoes(Date data, Long[] funcoesCheck) 
	{
		return getDao().findByFuncoes(data, funcoesCheck);
	}
	
	public HistoricoFuncao findUltimoHistoricoAteData(Long id, Date data)
	{
		return getDao().findUltimoHistoricoAteData(id, data);
	}
	
	public Collection<HistoricoFuncao> findEpis(Collection<Long> funcaoIds, Date data)
	{
		return getDao().findEpis(funcaoIds, data);
	}
	
	public Collection<HistoricoFuncao> findByFuncao(Long funcaoId) 
	{
		return getDao().findByFuncao(funcaoId);
	}
	
	public void setExameManager(ExameManager exameManager)
	{
		this.exameManager = exameManager;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public void setRiscoFuncaoManager(RiscoFuncaoManager riscoFuncaoManager) 
	{
		this.riscoFuncaoManager = riscoFuncaoManager;
	}
}