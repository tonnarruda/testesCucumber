package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;

public interface ColaboradorPresencaDao extends GenericDao<ColaboradorPresenca>
{
	Collection<ColaboradorPresenca> findPresencaByTurma(Long id);
	Collection<ColaboradorPresenca> existPresencaByTurma(Long turmaId);
	void remove(Long diaTurmaId, Long colaboradorTurmaId);
	Collection<ColaboradorPresenca> findByDiaTurma(Long diaTurmaId);
	void savePresencaDia(Long diaTurmaId, Long[] colaboradorTurmaIds);
	void removeByColaboradorTurma(Long[] colaboradorTurmaIds);
	Collection<ColaboradorPresenca> findColaboradorPresencaAprovadoOuReprovadoAvaliacao(Collection<Long> turmaIds, boolean aprovado);
	public Integer qtdDiaPresentesTurma(Long turmaId);
}