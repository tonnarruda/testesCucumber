package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.TurmaTipoDespesa;

public interface TurmaTipoDespesaManager extends GenericManager<TurmaTipoDespesa>
{
	public void save(Map<Long, String> despesas, Long turmaId);
	public Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId);
	public void removeByTurma(Long turmaId);
	public void save(String turmaTipoDespesasJSON, Long turmaId);
}
