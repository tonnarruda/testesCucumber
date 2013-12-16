package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.EpcPcmat;

public class EpcPcmatFactory
{
	public static EpcPcmat getEntity()
	{
		EpcPcmat epcPcmat = new EpcPcmat();
		epcPcmat.setId(null);
		return epcPcmat;
	}

	public static EpcPcmat getEntity(Long id)
	{
		EpcPcmat epcPcmat = getEntity();
		epcPcmat.setId(id);

		return epcPcmat;
	}

	public static Collection<EpcPcmat> getCollection()
	{
		Collection<EpcPcmat> epcPcmats = new ArrayList<EpcPcmat>();
		epcPcmats.add(getEntity());

		return epcPcmats;
	}
	
	public static Collection<EpcPcmat> getCollection(Long id)
	{
		Collection<EpcPcmat> epcPcmats = new ArrayList<EpcPcmat>();
		epcPcmats.add(getEntity(id));
		
		return epcPcmats;
	}
}
