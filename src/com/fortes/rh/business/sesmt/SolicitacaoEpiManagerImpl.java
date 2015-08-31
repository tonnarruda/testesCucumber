package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;
import com.fortes.rh.util.LongUtil;

public class SolicitacaoEpiManagerImpl extends GenericManagerImpl<SolicitacaoEpi, SolicitacaoEpiDao> implements SolicitacaoEpiManager
{
	private PlatformTransactionManager transactionManager;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;

	public Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacao, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck, char ordem)
	{
		Collection<SolicitacaoEpi> solicitacaoEpis = getDao().findAllSelect(page, pagingSize, empresaId, dataIni, dataFim, colaborador, situacao, tipoEpi, situacaoColaborador, LongUtil.arrayStringToArrayLong(estabelecimentoCheck), ordem); 

		for (SolicitacaoEpi solicitacaoEpi : solicitacaoEpis) 
			solicitacaoEpi.setInformativo(montaInformacaoDeEntregas(solicitacaoEpi));
		
		return solicitacaoEpis;
	}

	private String montaInformacaoDeEntregas(SolicitacaoEpi solicitacaoEpi) 
	{
		Collection<SolicitacaoEpiItem> solicitacaoEpiItems = solicitacaoEpiItemManager.findAllEntregasBySolicitacaoEpi(solicitacaoEpi.getId());
		StringBuffer epiEntregues = new StringBuffer();
		StringBuffer epiNaoEntregues = new StringBuffer();

		for (SolicitacaoEpiItem solicitacaoEpiItem : solicitacaoEpiItems) 
		{
			if(solicitacaoEpiItem.getTotalEntregue() > 0){
				
				if(epiEntregues.length()  == 0)
					epiEntregues.append("Entregues:<br>");

				epiEntregues.append("- " + solicitacaoEpiItem.getEpi().getNome() + "<br>");
				if(solicitacaoEpiItem.getTotalEntregue() > solicitacaoEpiItem.getQtdSolicitado())
				{
					if(epiNaoEntregues.length()  == 0)
						epiNaoEntregues.append("A Entregar:<br>");
				
					epiNaoEntregues.append("- " + solicitacaoEpiItem.getEpi().getNome() + "<br>");
				}
			}else
			{
				if(epiNaoEntregues.length()  == 0)
					epiNaoEntregues.append("A Entregar:<br>");
				
				epiNaoEntregues.append("- " + solicitacaoEpiItem.getEpi().getNome() + "<br>");
			}
		}

		return epiEntregues.toString() + epiNaoEntregues.toString();
	}

	public Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacao, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck, char ordem)
	{
		return getDao().getCount(empresaId, dataIni, dataFim, colaborador, situacao, tipoEpi, situacaoColaborador, LongUtil.arrayStringToArrayLong(estabelecimentoCheck), ordem);
	}

	public SolicitacaoEpi findByIdProjection(Long solicitacaoEpiId)
	{
		return getDao().findByIdProjection(solicitacaoEpiId);
	}

	public void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi, Date dataEntrega, boolean entregue) throws Exception
	{
		try
		{
			getDao().save(solicitacaoEpi);
			solicitacaoEpiItemManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado, selectMotivoSolicitacaoEpi, dataEntrega, entregue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public void update(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi)
	{
		update(solicitacaoEpi);
		solicitacaoEpiItemManager.removeAllBySolicitacaoEpi(solicitacaoEpi.getId());
		solicitacaoEpiItemManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado, selectMotivoSolicitacaoEpi, null, false);
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

	public Collection<SolicitacaoEpi> findRelatorioVencimentoEpi(Long empresaId, Date vencimento, char agruparPor, boolean exibirVencimentoCA, String[] tipoEPICheck, String[] areasCheck, String[] estabelecimentoCheck) throws ColecaoVaziaException
	{
		Collection<SolicitacaoEpi> solicitacaoEpis = getDao().findVencimentoEpi(empresaId, vencimento, exibirVencimentoCA, LongUtil.arrayStringToArrayLong(tipoEPICheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentoCheck), agruparPor);

		if (solicitacaoEpis == null || solicitacaoEpis.isEmpty())
			throw new ColecaoVaziaException("Não existem EPIs com prazo a vencer para os filtros informados.");

		return solicitacaoEpis;
	}

	public Collection<SolicitacaoEpiItemEntrega> findRelatorioEntregaEpi(Long empresaId, Date dataIni, Date dataFim, String[] epiCheck, String[] areaIds, String[] colaboradorCheck, char agruparPor, boolean exibirDesligados) throws ColecaoVaziaException
	{
		Collection<SolicitacaoEpiItemEntrega> solicitacaoEpis = getDao().findEntregaEpi(empresaId, dataIni, dataFim, LongUtil.arrayStringToArrayLong(epiCheck), LongUtil.arrayStringToArrayLong(areaIds), LongUtil.arrayStringToArrayLong(colaboradorCheck), agruparPor, exibirDesligados);
		
		if (solicitacaoEpis == null || solicitacaoEpis.isEmpty())
			throw new ColecaoVaziaException("Não existem EPIs a serem listados para os filtros informados.");
		
		return solicitacaoEpis;
	}

	public Collection<SolicitacaoEpiItemVO> findEpisWithItens(Long empresaId, Date dataIni, Date dataFim, char situacao, Colaborador colaborador, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck, char ordem) {
		return getDao().findEpisWithItens(empresaId, dataIni, dataFim, situacao, colaborador, tipoEpi, situacaoColaborador, LongUtil.arrayStringToArrayLong(estabelecimentoCheck), ordem);
	}
	
	public Collection<SolicitacaoEpi> findByColaboradorId(Long colaboradorId) {
		return getDao().findByColaboradorId(colaboradorId);
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