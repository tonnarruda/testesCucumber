package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.util.StringUtil;

public class SolicitacaoExameDWR 
{
	private ExameSolicitacaoExameManager exameSolicitacaoExameManager;
	private RealizacaoExameManager realizacaoExameManager;
	private PlatformTransactionManager transactionManager;
	
	public String[] marcarNaoInformadosComoNormal(Long solicitacaoExameId)
	{
		Collection<String> ids = new ArrayList<String>();
		Collection<ExameSolicitacaoExame> exameSolicitacaoExames = exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExameId);
		Collection<Long> realizacaoExameIds = new ArrayList<Long>();
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try
		{
		
			for (ExameSolicitacaoExame exameSolicitacaoExame : exameSolicitacaoExames) 
			{
				RealizacaoExame realizacao = exameSolicitacaoExame.getRealizacaoExame();
				
				if (realizacao != null && 
						(realizacao.getResultado() == null || realizacao.getResultado().equals(ResultadoExame.NAO_REALIZADO.toString())))
				{
					// salvar previamente a realização de exame se ainda não existe 
					if (realizacao.getId() == null)
					{
						realizacaoExameManager.save(exameSolicitacaoExame, exameSolicitacaoExame.getSolicitacaoExame().getData(), ResultadoExame.NAO_REALIZADO.toString(), "");
					}
					
					realizacaoExameIds.add(exameSolicitacaoExame.getRealizacaoExame().getId());
					ids.add(solicitacaoExameId + "_" + exameSolicitacaoExame.getExame().getId());
				}
			}
			
			realizacaoExameManager.marcarResultadoComoNormal(realizacaoExameIds);
			
			transactionManager.commit(status);
			return StringUtil.converteCollectionToArrayString(ids);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			return null;
		}
	}

	public void setExameSolicitacaoExameManager(ExameSolicitacaoExameManager exameSolicitacaoExameManager) {
		this.exameSolicitacaoExameManager = exameSolicitacaoExameManager;
	}

	public void setRealizacaoExameManager(RealizacaoExameManager realizacaoExameManager) {
		this.realizacaoExameManager = realizacaoExameManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
 