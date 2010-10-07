package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.web.tags.CheckBox;

public interface EpcManager extends GenericManager<Epc>
{
	Epc findByIdProjection(Long epcId);
	Collection<Epc> findAllSelect(Long empresaId);
	Collection<CheckBox> populaCheckBox(Long empresaId);
	String[] findByAmbiente(Long id);
	Collection<Epc> findEpcsDoAmbiente(Long ambienteId, Date data);
}