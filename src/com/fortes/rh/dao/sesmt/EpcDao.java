package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Epc;

public interface EpcDao extends GenericDao<Epc>
{
	Epc findByIdProjection(Long epcId);
	Collection<Epc> findAllSelect(Long empresaId);
	Collection<Epc> findByAmbiente(Long id);
	Collection<Epc> findEpcsDoAmbiente(Long ambienteId, Date data);
}