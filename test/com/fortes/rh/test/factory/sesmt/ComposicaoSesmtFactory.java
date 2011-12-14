package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.ComposicaoSesmt;

public class ComposicaoSesmtFactory
{
	public static ComposicaoSesmt getEntity()
	{
		ComposicaoSesmt composicaoSesmt = new ComposicaoSesmt();
		composicaoSesmt.setId(null);
		return composicaoSesmt;
	}

	public static ComposicaoSesmt getEntity(Long id)
	{
		ComposicaoSesmt composicaoSesmt = getEntity();
		composicaoSesmt.setId(id);

		return composicaoSesmt;
	}

	public static Collection<ComposicaoSesmt> getCollection()
	{
		Collection<ComposicaoSesmt> composicaoSesmts = new ArrayList<ComposicaoSesmt>();
		composicaoSesmts.add(getEntity());

		return composicaoSesmts;
	}
	
	public static Collection<ComposicaoSesmt> getCollection(Long id)
	{
		Collection<ComposicaoSesmt> composicaoSesmts = new ArrayList<ComposicaoSesmt>();
		composicaoSesmts.add(getEntity(id));
		
		return composicaoSesmts;
	}
}
