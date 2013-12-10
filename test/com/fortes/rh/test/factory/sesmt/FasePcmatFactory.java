package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.FasePcmat;

public class FasePcmatFactory
{
	public static FasePcmat getEntity()
	{
		FasePcmat fasePcmat = new FasePcmat();
		fasePcmat.setId(null);
		return fasePcmat;
	}

	public static FasePcmat getEntity(Long id)
	{
		FasePcmat fasePcmat = getEntity();
		fasePcmat.setId(id);

		return fasePcmat;
	}

	public static Collection<FasePcmat> getCollection()
	{
		Collection<FasePcmat> fasePcmats = new ArrayList<FasePcmat>();
		fasePcmats.add(getEntity());

		return fasePcmats;
	}
	
	public static Collection<FasePcmat> getCollection(Long id)
	{
		Collection<FasePcmat> fasePcmats = new ArrayList<FasePcmat>();
		fasePcmats.add(getEntity(id));
		
		return fasePcmats;
	}
}
