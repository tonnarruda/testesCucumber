package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.SinalizacaoPcmat;

public class SinalizacaoPcmatFactory
{
	public static SinalizacaoPcmat getEntity()
	{
		SinalizacaoPcmat sinalizacaoPcmat = new SinalizacaoPcmat();
		sinalizacaoPcmat.setId(null);
		return sinalizacaoPcmat;
	}

	public static SinalizacaoPcmat getEntity(Long id)
	{
		SinalizacaoPcmat sinalizacaoPcmat = getEntity();
		sinalizacaoPcmat.setId(id);

		return sinalizacaoPcmat;
	}

	public static Collection<SinalizacaoPcmat> getCollection()
	{
		Collection<SinalizacaoPcmat> sinalizacaoPcmats = new ArrayList<SinalizacaoPcmat>();
		sinalizacaoPcmats.add(getEntity());

		return sinalizacaoPcmats;
	}
	
	public static Collection<SinalizacaoPcmat> getCollection(Long id)
	{
		Collection<SinalizacaoPcmat> sinalizacaoPcmats = new ArrayList<SinalizacaoPcmat>();
		sinalizacaoPcmats.add(getEntity(id));
		
		return sinalizacaoPcmats;
	}
}
