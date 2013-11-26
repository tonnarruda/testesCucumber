package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.RiscoFasePcmat;

public class RiscoFasePcmatFactory
{
	public static RiscoFasePcmat getEntity()
	{
		RiscoFasePcmat riscoFasePcmat = new RiscoFasePcmat();
		riscoFasePcmat.setId(null);
		return riscoFasePcmat;
	}

	public static RiscoFasePcmat getEntity(Long id)
	{
		RiscoFasePcmat riscoFasePcmat = getEntity();
		riscoFasePcmat.setId(id);

		return riscoFasePcmat;
	}

	public static Collection<RiscoFasePcmat> getCollection()
	{
		Collection<RiscoFasePcmat> riscoFasePcmats = new ArrayList<RiscoFasePcmat>();
		riscoFasePcmats.add(getEntity());

		return riscoFasePcmats;
	}
	
	public static Collection<RiscoFasePcmat> getCollection(Long id)
	{
		Collection<RiscoFasePcmat> riscoFasePcmats = new ArrayList<RiscoFasePcmat>();
		riscoFasePcmats.add(getEntity(id));
		
		return riscoFasePcmats;
	}
}
