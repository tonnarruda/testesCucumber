package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;

public interface ExameSolicitacaoExameManager extends GenericManager<ExameSolicitacaoExame>
{
	public void removeAllBySolicitacaoExame(Long solicitacaoExameId);

	public void save(SolicitacaoExame solicitacaoExame, String[] exameIds, String[] selectClinicas, Integer[] periodicidades);

//	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long solicitacaoExameId);

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long[] solicitacaoExameIds);

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long solicitacaoExameId, Boolean asoPadrao);

	public boolean verificaExisteResultado(Collection<ExameSolicitacaoExame> exameSolicitacaoExames);
	
	public ExameSolicitacaoExame findDataSolicitacaoExame(Long colaboradorId, Long candidatoId, Long exameId);
	
	public ExameSolicitacaoExame findIdColaboradorOUCandidato(Long solicitacaoExameId, Long exameId);
}