package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.FasePcmat;

public class FasePcmatFactory
{
	public static FasePcmat getEntity()
	{
		FasePcmat fasepcmat = new FasePcmat();
		fasepcmat.setId(null);
		fasepcmat.setDescricao("Fase");
		return fasepcmat;
	}

	public static FasePcmat getEntity(Long id)
	{
		FasePcmat fasepcmat = getEntity();
		fasepcmat.setId(id);

		return fasepcmat;
	}

	public static Collection<FasePcmat> getCollection()
	{
		Collection<FasePcmat> fasepcmats = new ArrayList<FasePcmat>();
		fasepcmats.add(getEntity());

		return fasepcmats;
	}
	
	public static Collection<FasePcmat> getCollection(Long id)
	{
		Collection<FasePcmat> fasepcmats = new ArrayList<FasePcmat>();
		fasepcmats.add(getEntity(id));
		
		return fasepcmats;
	}
}
