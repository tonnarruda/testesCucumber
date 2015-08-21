package com.fortes.rh.business.desenvolvimento;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;

public interface TurmaAvaliacaoTurmaManager extends GenericManager<TurmaAvaliacaoTurma>
{
	void salvarAvaliacaoTurmas(Long turmaId, Long[] avaliacaoTurmaIds);
	boolean verificaAvaliacaoliberada(Long turmaId);
	void updateLiberada(Long turmaId, Long avaliacaoTurmaId, Boolean liberada, Long empresaId);
	void removeByTurma(Long turmaId, Long[] avaliacaoTurmaIdsQueNaoDevemSerRemovidas);
}