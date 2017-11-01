package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.ParteAtingida;

public class ParteAtingidaFactory
{
	public static ParteAtingida getEntity()
	{
		ParteAtingida parteAtingida = new ParteAtingida();
		parteAtingida.setId(null);
		return parteAtingida;
	}

	public static ParteAtingida getEntity(Long id)
	{
		ParteAtingida parteAtingida = getEntity();
		parteAtingida.setId(id);

		return parteAtingida;
	}

	public static Collection<ParteAtingida> getCollection()
	{
		Collection<ParteAtingida> parteAtingidas = new ArrayList<ParteAtingida>();
		parteAtingidas.add(getEntity());

		return parteAtingidas;
	}
	
	public static Collection<ParteAtingida> getCollection(Long id)
	{
		Collection<ParteAtingida> parteAtingidas = new ArrayList<ParteAtingida>();
		parteAtingidas.add(getEntity(id));
		
		return parteAtingidas;
	}
}
