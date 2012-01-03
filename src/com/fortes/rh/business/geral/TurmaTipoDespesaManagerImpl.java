package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.geral.TurmaTipoDespesa;

public class TurmaTipoDespesaManagerImpl extends GenericManagerImpl<TurmaTipoDespesa, TurmaTipoDespesaDao> implements TurmaTipoDespesaManager
{
	public void save(Map<Long, String> despesas, Long turmaId) 
	{
		TurmaTipoDespesa turmaTipoDespesa;
		Double valor;
		
		for (Map.Entry<Long, String> despesa : despesas.entrySet())
		{
			valor = Double.parseDouble(despesa.getValue());
			
			if (valor > 0)
			{
				turmaTipoDespesa = new TurmaTipoDespesa();
				turmaTipoDespesa.setProjectionTipoDespesaId(despesa.getKey());
				turmaTipoDespesa.setDespesa(valor);
				turmaTipoDespesa.setProjectionTurmaId(turmaId);
				save(turmaTipoDespesa);
			}
		}
	}

	public void removeByTurma(Long turmaId) 
	{
		getDao().removeByTurma(turmaId);
	}
	
	public Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId) {
		return getDao().findTipoDespesaTurma(turmaId);
	}
}
