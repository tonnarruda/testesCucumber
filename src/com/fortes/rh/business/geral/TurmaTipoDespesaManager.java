package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.TurmaTipoDespesa;

public interface TurmaTipoDespesaManager extends GenericManager<TurmaTipoDespesa>
{
	public void save(Collection<TurmaTipoDespesa> turmaTipoDespesas, Long turmaId);
	public Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId);
	public void removeByTurma(Long turmaId);
}
