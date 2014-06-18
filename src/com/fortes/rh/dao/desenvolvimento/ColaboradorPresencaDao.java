package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;
import java.util.Date;

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
	public Integer qtdDiaPresentesTurma(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds);
}