package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.Gasto;

public class GastoFactory
{
	public static Gasto getEntity()
	{
		Gasto gasto = new Gasto();
		gasto.setId(null);

		return gasto;
	}

	public static Gasto getEntity(Long id)
	{
		Gasto gasto = getEntity();
		gasto.setId(id);

		return gasto;
	}

	public static Collection<Gasto> getCollection()
	{
		Collection<Gasto> collection = new ArrayList<Gasto>();
		collection.add(getEntity());
		return collection;
	}
}
