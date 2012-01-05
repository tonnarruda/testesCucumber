package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;

public interface TurmaTipoDespesaDao extends GenericDao<TurmaTipoDespesa> 
{
	Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId);
	void removeByTurma(Long turmaId);
	Collection<TipoDespesa> somaDespesasPorTipo(Date dataIni, Date dataFim, Long empresaId);
}
