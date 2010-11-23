package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;

public interface DiaTurmaManager extends GenericManager<DiaTurma>
{
	Collection<DiaTurma> montaListaDias(Date dataPrevIni, Date dataPrevFim);
	void saveDiasTurma(Turma turma, String[] diasCheck) throws Exception;
	void deleteDiasTurma(Long turmaId);
	Collection<DiaTurma> findByTurma(Long turmaId);
	public Integer qtdDiasDasTurmas(Long turmaId);
	public void clonarDiaTurmasDeTurma(Turma turmaOrigem, Turma turmaDestino);
}