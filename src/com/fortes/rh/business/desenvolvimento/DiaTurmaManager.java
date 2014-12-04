package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;

public interface DiaTurmaManager extends GenericManager<DiaTurma>
{
	Collection<DiaTurma> montaListaDias(Date dataPrevIni, Date dataPrevFim, Boolean aplicarPorTurno);
	void saveDiasTurma(Turma turma, String[] diasCheck, String[] horasIni, String[] horasFim) throws Exception;
	void deleteDiasTurma(Long turmaId);
	Collection<DiaTurma> findByTurma(Long turmaId);
	Integer qtdDiasDasTurmas(Long turmaId);
	void clonarDiaTurmasDeTurma(Turma turmaOrigem, Turma turmaDestino);
	Collection<DiaTurma> findByTurmaAndPeriodo(Long turmaId, Date dataIni, Date dataFim);
}