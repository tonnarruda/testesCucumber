package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.TurmaTipoDespesa;

public interface TurmaTipoDespesaDao extends GenericDao<TurmaTipoDespesa> 
{
	Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId);
	void removeByTurma(Long turmaId);
}
