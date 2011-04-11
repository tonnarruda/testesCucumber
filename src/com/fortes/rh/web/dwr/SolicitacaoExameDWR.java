package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.util.DateUtil;
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

	public String verificaColaboradorExameDentroDoPrazo(Long colaboradorId,Long candidatoId,Long solicitacaoExameId,Long exameId) 
	{
		String textoResult = "";
		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		ExameSolicitacaoExame exameSolicitacaoExameTemp = new ExameSolicitacaoExame();
		Date dataDeVencimentoExame = DateUtil.criarAnoMesDia(1900,01,01);
		
		if(colaboradorId==null && candidatoId==null)
		{
			exameSolicitacaoExameTemp = exameSolicitacaoExameManager.findIdColaboradorOUCandidato(solicitacaoExameId,exameId);
			
			if (exameSolicitacaoExameTemp != null && exameSolicitacaoExameTemp.getColaboradorId() != null)
				colaboradorId = exameSolicitacaoExameTemp.getColaboradorId();

			if (exameSolicitacaoExameTemp != null && exameSolicitacaoExameTemp.getCandidatoId() != null)
				candidatoId = exameSolicitacaoExameTemp.getCandidatoId();
		}
		
		if(colaboradorId!=null || candidatoId!=null)
			exameSolicitacaoExame = exameSolicitacaoExameManager.findDataSolicitacaoExame(colaboradorId, candidatoId, exameId);
		
		if (exameSolicitacaoExame!=null && exameSolicitacaoExame.getExame()!=null)
		{
			if (exameSolicitacaoExame.getRealizacaoExame() != null && exameSolicitacaoExame.getRealizacaoExame().getData() != null)
				dataDeVencimentoExame = DateUtil.incrementaMes(exameSolicitacaoExame.getRealizacaoExame().getData(), exameSolicitacaoExame.getPeriodicidade());

			if(dataDeVencimentoExame.after(new Date()))
			{	
				if (exameSolicitacaoExame.getRealizacaoExame() != null && exameSolicitacaoExame.getRealizacaoExame().getData() != null)
				{
					textoResult += "O prazo de validade do ";
					
					if (exameSolicitacaoExame.getCandidatoNome() != null)
						textoResult +=	"candidato " +  exameSolicitacaoExame.getCandidatoNome();
					if (exameSolicitacaoExame.getColaboradorNome() != null)
						textoResult +=	"colaborador " +  exameSolicitacaoExame.getColaboradorNome();
						
					textoResult += " para \no exame " + exameSolicitacaoExame.getExame().getNome() + " é até "
						+ DateUtil.formataDiaMesAno(dataDeVencimentoExame);
				}
			}
			
			else
			{
				textoResult += "O exame "
					+ exameSolicitacaoExame.getExame().getNome() + " para o ";
				
				if (exameSolicitacaoExame.getCandidatoNome() != null)
					textoResult +=	"candidato " +  exameSolicitacaoExame.getCandidatoNome();
				if (exameSolicitacaoExame.getColaboradorNome() != null)
					textoResult +=	"colaborador " +  exameSolicitacaoExame.getColaboradorNome();
				
				textoResult += " se encontra pendente. ";
			}
		}
		
		return textoResult;
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
 