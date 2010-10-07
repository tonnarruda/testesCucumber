package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.HistoricoBeneficioDao;
import com.fortes.rh.model.geral.HistoricoBeneficio;

public class HistoricoBeneficioManagerImpl extends GenericManagerImpl<HistoricoBeneficio, HistoricoBeneficioDao> implements HistoricoBeneficioManager
{

	public HistoricoBeneficio findByHistoricoId(Long id)
	{
		return getDao().findByHistoricoId(id);
	}

	public Collection<HistoricoBeneficio> getHistoricos()
	{
		return getDao().getHistoricos();
	}

}