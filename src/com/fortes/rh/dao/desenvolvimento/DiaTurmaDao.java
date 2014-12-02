package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.DiaTurma;

public interface DiaTurmaDao extends GenericDao<DiaTurma>
{
	void deleteDiasTurma(Long turmaId);
	Collection<DiaTurma> findByTurma(Long turmaId);
	Integer qtdDiasDasTurmas(Long turmaId);
	Collection<DiaTurma> findByTurmaAndPeriodo(Long turmaId, Date dataIni, Date dataFim);
}