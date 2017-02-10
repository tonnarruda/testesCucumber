package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

@Component
@RemoteProxy(name="AtitudeDWR")
@SuppressWarnings("rawtypes")
public class AtitudeDWR
{
	@Autowired private AtitudeManager atitudeManager;

	@RemoteMethod
	public Map getAtitudes(String[] areaOrganizacionalIds, Long empresaId)
	{
		Collection<Atitude> atitudes;

		if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
			atitudes =  atitudeManager.findByAreasOrganizacionalIds(LongUtil.arrayStringToArrayLong(areaOrganizacionalIds),empresaId);
		else
			atitudes =  atitudeManager.findAllSelect(empresaId);

		return new CollectionUtil<Atitude>().convertCollectionToMap(atitudes,"getId","getNome");
	}
}