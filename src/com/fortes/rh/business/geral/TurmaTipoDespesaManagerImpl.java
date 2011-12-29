package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.geral.TurmaTipoDespesa;

public class TurmaTipoDespesaManagerImpl extends GenericManagerImpl<TurmaTipoDespesa, TurmaTipoDespesaDao> implements TurmaTipoDespesaManager
{
	public void save(Collection<TurmaTipoDespesa> turmaTipoDespesas, Long turmaId) 
	{
//		for (TurmaTipoDespesa turmaTipoDespesa : turmaTipoDespesas) {
//			if(turmaTipoDespesa != null)
//			{
//				turmaTipoDespesa.setProjectionTurmaId(turmaId);
//				save(turmaTipoDespesa);
//			}
//		}
	}

	public void removeByTurma(Long turmaId) 
	{
		getDao().removeByTurma(turmaId);
	}
	
	public Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId) {
		return getDao().findTipoDespesaTurma(turmaId);
	}
}
