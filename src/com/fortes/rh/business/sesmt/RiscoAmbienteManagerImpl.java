package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;

@Component
public class RiscoAmbienteManagerImpl extends GenericManagerImpl<RiscoAmbiente, RiscoAmbienteDao> implements RiscoAmbienteManager
{
	@Autowired
	RiscoAmbienteManagerImpl(RiscoAmbienteDao riscoAmbienteDao) {
		setDao(riscoAmbienteDao);
	}
	
	public boolean removeByHistoricoAmbiente(Long historicoAmbienteId) 
	{
		return getDao().removeByHistoricoAmbiente(historicoAmbienteId);
	}

	public Collection<Risco> findRiscosByAmbienteData(Long ambienteId, Date data) 
	{
		return getDao().findRiscosByAmbienteData(ambienteId, data);
	}

	public Collection<String> findColaboradoresSemAmbiente(Date data, Long estabelecimentoId) 
	{
		return getDao().findColaboradoresSemAmbiente(data, estabelecimentoId);
	}

	public Collection<Long> findAmbienteAtualDosColaboradores(Date data, Long estabelecimentoId) 
	{
		return getDao().findAmbienteAtualDosColaboradores(data, estabelecimentoId);
	}

	public Collection<RiscoAmbiente> findByAmbiente(Long ambienteId) 
	{
		return getDao().findByAmbiente(ambienteId);
	}
}