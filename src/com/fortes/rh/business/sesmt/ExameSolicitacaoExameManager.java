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

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long solicitacaoExameId);

	public boolean verificaExisteResultado(Collection<ExameSolicitacaoExame> exameSolicitacaoExames);

}