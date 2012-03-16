package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.util.ComparatorString;
import com.fortes.rh.util.LongUtil;

public class SolicitacaoEpiManagerImpl extends GenericManagerImpl<SolicitacaoEpi, SolicitacaoEpiDao> implements SolicitacaoEpiManager
{
	private PlatformTransactionManager transactionManager;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;

	public Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacao)
	{
		return getDao().findAllSelect(page, pagingSize, empresaId, dataIni, dataFim, colaborador, situacao);
	}

	public Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacao)
	{
		return getDao().getCount(empresaId, dataIni, dataFim, colaborador, situacao);
	}

	public SolicitacaoEpi findByIdProjection(Long solicitacaoEpiId)
	{
		return getDao().findByIdProjection(solicitacaoEpiId);
	}

	public void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, Date dataEntrega) throws Exception
	{
		try
		{
			getDao().save(solicitacaoEpi);
			solicitacaoEpiItemManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado, dataEntrega);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public void update(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado)
	{
		update(solicitacaoEpi);
		solicitacaoEpiItemManager.removeAllBySolicitacaoEpi(solicitacaoEpi.getId());
		solicitacaoEpiItemManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado, null);
	}

	@Override
	public void remove(Long solicitacaoEpiId)
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			solicitacaoEpiItemManager.removeAllBySolicitacaoEpi(solicitacaoEpiId);
			getDao().remove(solicitacaoEpiId);
			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw new PersistenceException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<SolicitacaoEpi> findRelatorioVencimentoEpi(Long empresaId, Date vencimento, char agruparPor, boolean exibirVencimentoCA, String[] tipoEPICheck, String[] areasCheck, String[] estabelecimentoCheck) throws ColecaoVaziaException
	{
		Collection<SolicitacaoEpi> solicitacoesEpiAVencer = null;
		Collection<SolicitacaoEpi> solicitacaoEpis = getDao().findVencimentoEpi(empresaId, vencimento, exibirVencimentoCA, LongUtil.arrayStringToArrayLong(tipoEPICheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentoCheck));

		if (solicitacaoEpis == null || solicitacaoEpis.isEmpty())
			throw new ColecaoVaziaException("Não existem EPIs com Prazo a Vencer para os filtros informados.");

		solicitacoesEpiAVencer = prepareUltimosEpisVencidos(solicitacaoEpis);

		switch (agruparPor)
		{
			case 'E':
		        Collections.sort((List) solicitacoesEpiAVencer, new BeanComparator("epi.nome", new ComparatorString()));
				break;

			case 'C':
		        Collections.sort((List) solicitacoesEpiAVencer, new BeanComparator("colaborador.nome", new ComparatorString()));
				break;
		}

		return solicitacoesEpiAVencer;
	}

	public Collection<SolicitacaoEpiItemEntrega> findRelatorioEntregaEpi(Long empresaId, Date dataIni, Date dataFim, String[] epiCheck, String[] colaboradorCheck, char agruparPor) throws ColecaoVaziaException
	{
		Collection<SolicitacaoEpiItemEntrega> solicitacaoEpis = getDao().findEntregaEpi(empresaId, dataIni, dataFim, LongUtil.arrayStringToArrayLong(epiCheck), LongUtil.arrayStringToArrayLong(colaboradorCheck), agruparPor);
		
		if (solicitacaoEpis == null || solicitacaoEpis.isEmpty())
			throw new ColecaoVaziaException("Não existem EPIs a serem listados para os filtros informados.");
		
		return solicitacaoEpis;
	}

	/*
	 * Trata a coleção de solicitações, mantendo apenas o último Epi vencido de cada tipo de Epi para cada colaborador
	 * Ex., Para o Epi "Luva" solicitado para o colaborador Antonio, vencido em 09/09, 01/10 e 02/10, só deve retornar o de 02/10.
	 *
	 * Obs: É esperado que a coleção venha ordenada por Colaborador, Epi e Data
	 */
	private Collection<SolicitacaoEpi> prepareUltimosEpisVencidos(Collection<SolicitacaoEpi> solicitacaoEpis)
	{
		Collection<SolicitacaoEpi> solicitacoesEpiAVencer = new ArrayList<SolicitacaoEpi>();
		SolicitacaoEpi solicitacaoEpiAnterior = null;

		for (SolicitacaoEpi solicitacaoEpi : solicitacaoEpis)
		{
			if ((solicitacaoEpiAnterior != null)
					&& solicitacaoEpi.getColaborador().getId().equals(solicitacaoEpiAnterior.getColaborador().getId())
					&& solicitacaoEpi.getEpi().getId().equals(solicitacaoEpiAnterior.getEpi().getId()))
			{
				if (solicitacaoEpiAnterior.getData().compareTo(solicitacaoEpi.getData()) == -1)
				{
					solicitacoesEpiAVencer.remove(solicitacaoEpiAnterior);
				}
			}

			solicitacoesEpiAVencer.add(solicitacaoEpi);
			solicitacaoEpiAnterior = solicitacaoEpi;
		}

		return solicitacoesEpiAVencer;
	}

	public void setSolicitacaoEpiItemManager(SolicitacaoEpiItemManager solicitacaoEpiItemManager)
	{
		this.solicitacaoEpiItemManager = solicitacaoEpiItemManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}
}