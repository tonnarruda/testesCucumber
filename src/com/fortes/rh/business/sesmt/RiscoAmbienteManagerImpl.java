package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;

public class RiscoAmbienteManagerImpl extends GenericManagerImpl<RiscoAmbiente, RiscoAmbienteDao> implements RiscoAmbienteManager
{
	public boolean removeByHistoricoAmbiente(Long historicoAmbienteId) 
	{
		if (historicoAmbienteId != null)
		{
			return getDao().removeByHistoricoAmbiente(historicoAmbienteId);
		}
		return true;
	}

	public Collection<Risco> findRiscosByAmbienteData(Long ambienteId, Date data) 
	{
		return getDao().findRiscosByAmbienteData(ambienteId, data);
	}
}