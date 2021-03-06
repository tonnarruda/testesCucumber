package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;

/**
 * @author Tiago Lopes
 */
public interface ExameSolicitacaoExameDao extends GenericDao<ExameSolicitacaoExame>
{

	void removeAllBySolicitacaoExame(Long solicitacaoExameId);

	Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long[] solicitacaoExameIds);

	Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long solicitacaoExameId, Boolean asoPadrao);
	
	public ExameSolicitacaoExame findDataSolicitacaoExame(Long colaboradorId, Long candidatoId, Long exameId);
	
	public ExameSolicitacaoExame findIdColaboradorOUCandidato(Long solicitacaoExameId, Long exameId);

}