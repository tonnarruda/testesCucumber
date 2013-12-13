package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.EpiPcmat;

public class EpiPcmatFactory
{
	public static EpiPcmat getEntity()
	{
		EpiPcmat epiPcmat = new EpiPcmat();
		epiPcmat.setId(null);
		return epiPcmat;
	}

	public static EpiPcmat getEntity(Long id)
	{
		EpiPcmat epiPcmat = getEntity();
		epiPcmat.setId(id);

		return epiPcmat;
	}

	public static Collection<EpiPcmat> getCollection()
	{
		Collection<EpiPcmat> epiPcmats = new ArrayList<EpiPcmat>();
		epiPcmats.add(getEntity());

		return epiPcmats;
	}
	
	public static Collection<EpiPcmat> getCollection(Long id)
	{
		Collection<EpiPcmat> epiPcmats = new ArrayList<EpiPcmat>();
		epiPcmats.add(getEntity(id));
		
		return epiPcmats;
	}
}
