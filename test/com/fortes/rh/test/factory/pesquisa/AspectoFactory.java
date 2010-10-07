package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.Aspecto;

public class AspectoFactory
{
	public static Aspecto getEntity()
	{
		Aspecto aspecto = new Aspecto();

		aspecto.setId(null);
		aspecto.setNome("aspecto");

		return aspecto;
	}

	public static Aspecto getEntity(Long id)
	{
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto.setId(id);

		return aspecto;
	}

	public static Collection<Aspecto> getCollection(Long id)
	{
		Collection<Aspecto> collection = new ArrayList<Aspecto>();
		collection.add(AspectoFactory.getEntity(id));
		return collection;
	}
}
