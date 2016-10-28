package com.fortes.rh.business.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.HistoricoBeneficioDao;
import com.fortes.rh.model.geral.HistoricoBeneficio;

@Component
public class HistoricoBeneficioManagerImpl extends GenericManagerImpl<HistoricoBeneficio, HistoricoBeneficioDao> implements HistoricoBeneficioManager
{
	@Autowired
	HistoricoBeneficioManagerImpl(HistoricoBeneficioDao dao) {
		setDao(dao);
	}

	public HistoricoBeneficio findByHistoricoId(Long id)
	{
		return getDao().findByHistoricoId(id);
	}

	public Collection<HistoricoBeneficio> getHistoricos()
	{
		return getDao().getHistoricos();
	}

}