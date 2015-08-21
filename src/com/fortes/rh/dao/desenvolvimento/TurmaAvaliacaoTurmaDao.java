package com.fortes.rh.dao.desenvolvimento;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;

public interface TurmaAvaliacaoTurmaDao extends GenericDao<TurmaAvaliacaoTurma>
{
	boolean verificaAvaliacaoliberada(Long turmaId);
	void updateLiberada(Long turmaId, Long avaliacaoTurmaId, Boolean liberada);
	void removeByTurma(Long turmaId, Long[] avaliacaoTurmaIds);
}