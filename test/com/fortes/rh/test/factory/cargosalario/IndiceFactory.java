package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.Indice;

public class IndiceFactory
{
	public static Indice getEntity()
	{
		Indice indice = new Indice();

		indice.setId(null);
		indice.setCodigoAC("0");
		indice.setNome("nome indice");

		return indice;
	}


	public static Indice getEntity(Long id)
	{
		Indice indice = getEntity();
		indice.setId(id);

		return indice;
	}

	public static Collection<Indice> getCollection()
	{
		Collection<Indice> indices = new ArrayList<Indice>();
		indices.add(getEntity());

		return indices;
	}
	
	public static Collection<Indice> getCollection(Long id)
	{
		Collection<Indice> indices = new ArrayList<Indice>();
		indices.add(getEntity(id));
		
		return indices;
	}
}
