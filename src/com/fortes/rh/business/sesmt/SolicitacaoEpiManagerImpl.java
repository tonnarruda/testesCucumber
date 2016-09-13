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
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiVO;
import com.fortes.rh.util.LongUtil;

public class SolicitacaoEpiManagerImpl extends GenericManagerImpl<SolicitacaoEpi, SolicitacaoEpiDao> implements SolicitacaoEpiManager
{
	private PlatformTransactionManager transactionManager;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;

	public SolicitacaoEpiVO findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, String situacao, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck, char ordem)
	{
		SolicitacaoEpiVO solicitacaoEpiVO = getDao().findAllSelect(page, pagingSize, empresaId, dataIni, dataFim, colaborador, situacao, tipoEpi, situacaoColaborador, LongUtil.arrayStringToArrayLong(estabelecimentoCheck), ordem); 
		
		for (SolicitacaoEpi solicitacaoEpi : solicitacaoEpiVO.getSolicitacaoEpis()){ 
			solicitacaoEpi.setInformativoEntrega(montaInformacaoDeEntregas(solicitacaoEpi));
			solicitacaoEpi.setInformativoDevolucao(montaInformacaoDeDevolucoes(solicitacaoEpi));
		}
		
		return solicitacaoEpiVO;
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
	
	private String montaInformacaoDeDevolucoes(SolicitacaoEpi solicitacaoEpi) 
	{
		Collection<SolicitacaoEpiItem> solicitacaoEpiItems = solicitacaoEpiItemManager.findAllDevolucoesBySolicitacaoEpi(solicitacaoEpi.getId());
		
		StringBuffer epiDevolvido = new StringBuffer();
		StringBuffer epiNaoDevolvido = new StringBuffer();

		for (SolicitacaoEpiItem solicitacaoEpiItem : solicitacaoEpiItems) 
		{
			if(solicitacaoEpiItem.getTotalDevolvido() > 0){
				
				if(epiDevolvido.length()  == 0)
					epiDevolvido.append("Devolvidos:<br>");

				epiDevolvido.append("- " + solicitacaoEpiItem.getEpi().getNome() + "<br>");
				if(solicitacaoEpiItem.getTotalDevolvido() > solicitacaoEpiItem.getQtdSolicitado())
				{
					if(epiNaoDevolvido.length()  == 0 && solicitacaoEpiItem.getTotalEntregue() < 0 )
						epiNaoDevolvido.append("A Devolver:<br>");
				
					epiNaoDevolvido.append("- " + solicitacaoEpiItem.getEpi().getNome() + "<br>");
				}
			}else
			{
				if(epiNaoDevolvido.length()  == 0  && solicitacaoEpiItem.getTotalEntregue() > 0)
					epiNaoDevolvido.append("A Devolver:<br>");
				
				if(solicitacaoEpiItem.getTotalEntregue() > 0)
					epiNaoDevolvido.append("- " + solicitacaoEpiItem.getEpi().getNome() + "<br>");
			}
		}
		
		if(epiDevolvido.length() == 0 && epiNaoDevolvido.length() == 0){
			epiDevolvido.append("Sem EPI a ");
			epiNaoDevolvido.append("devolver");
		}
		return epiDevolvido.toString() + epiNaoDevolvido.toString();
	}

	public SolicitacaoEpi findByIdProjection(Long solicitacaoEpiId)
	{
		return getDao().findByIdProjection(solicitacaoEpiId);
	}

	public void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi, Date dataEntrega, boolean entregue, String[] selectTamanhoEpi) throws Exception
	{
		try
		{
			getDao().save(solicitacaoEpi);
			solicitacaoEpiItemManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado, selectMotivoSolicitacaoEpi, dataEntrega, entregue, selectTamanhoEpi);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public void update(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi, String[] selectTamanhoEpi)
	{
		update(solicitacaoEpi);
		solicitacaoEpiItemManager.removeAllBySolicitacaoEpi(solicitacaoEpi.getId());
		solicitacaoEpiItemManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado, selectMotivoSolicitacaoEpi, null, false, selectTamanhoEpi);
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

	public Collection<SolicitacaoEpiItemVO> findEpisWithItens(Long empresaId, Date dataIni, Date dataFim, String situacao, Colaborador colaborador, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck, char ordem) {
		return getDao().findEpisWithItens(empresaId, dataIni, dataFim, situacao, colaborador, tipoEpi, situacaoColaborador, LongUtil.arrayStringToArrayLong(estabelecimentoCheck), ordem);
	}
	
	public Collection<SolicitacaoEpi> findByColaboradorId(Long colaboradorId) {
		return getDao().findByColaboradorId(colaboradorId);
	}

	public Collection<SolicitacaoEpiItemDevolucao> findRelatorioDevolucaoEpi(Long empresaId, Date dataIni, Date dataFim, String[] epiCheck, String[] areasCheck, String[] colaboradorCheck, char agruparPor,boolean exibirDesligados) throws ColecaoVaziaException {
		Collection<SolicitacaoEpiItemDevolucao> devolucoes = getDao().findDevolucaoEpi(empresaId, dataIni, dataFim, LongUtil.arrayStringToArrayLong(epiCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(colaboradorCheck), agruparPor, exibirDesligados);
		
		if (devolucoes == null || devolucoes.isEmpty())
			throw new ColecaoVaziaException("Não existem EPIs a serem listados para os filtros informados.");
		
		return devolucoes;
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