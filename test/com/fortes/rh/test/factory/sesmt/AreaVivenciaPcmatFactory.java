package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;

public class AreaVivenciaPcmatFactory
{
	public static AreaVivenciaPcmat getEntity()
	{
		AreaVivenciaPcmat areaVivenciaPcmat = new AreaVivenciaPcmat();
		areaVivenciaPcmat.setId(null);
		return areaVivenciaPcmat;
	}

	public static AreaVivenciaPcmat getEntity(Long id)
	{
		AreaVivenciaPcmat areaVivenciaPcmat = getEntity();
		areaVivenciaPcmat.setId(id);

		return areaVivenciaPcmat;
	}

	public static Collection<AreaVivenciaPcmat> getCollection()
	{
		Collection<AreaVivenciaPcmat> areaVivenciaPcmats = new ArrayList<AreaVivenciaPcmat>();
		areaVivenciaPcmats.add(getEntity());

		return areaVivenciaPcmats;
	}
	
	public static Collection<AreaVivenciaPcmat> getCollection(Long id)
	{
		Collection<AreaVivenciaPcmat> areaVivenciaPcmats = new ArrayList<AreaVivenciaPcmat>();
		areaVivenciaPcmats.add(getEntity(id));
		
		return areaVivenciaPcmats;
	}
}
