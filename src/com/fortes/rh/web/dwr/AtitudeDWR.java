package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

public class AtitudeDWR
{
	private AtitudeManager atitudeManager;

	public Map getAtitudes(String[] areaOrganizacionalIds, Long empresaId)
	{
		Collection<Atitude> atitudes;

		if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
			atitudes =  atitudeManager.findByAreasOrganizacionalIds(LongUtil.arrayStringToArrayLong(areaOrganizacionalIds),empresaId);
		else
			atitudes =  atitudeManager.find(new String[]{"empresa.id"},new Object[]{empresaId},new String[]{"nome"});

		return new CollectionUtil<Atitude>().convertCollectionToMap(atitudes,"getId","getNome");
	}

	public AtitudeManager getAtitudeManager() {
		return atitudeManager;
	}

	public void setAtitudeManager(AtitudeManager atitudeManager) {
		this.atitudeManager = atitudeManager;
	}


}
