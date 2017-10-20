package com.fortes.rh.test.factory.sesmt.eSocialTabelas;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;

public class ParteCorpoAtingidaFactory
{
	public static ParteCorpoAtingida getEntity()
	{
		ParteCorpoAtingida parteCorpoAtingida = new ParteCorpoAtingida();
		parteCorpoAtingida.setId(null);
		return parteCorpoAtingida;
	}

	public static ParteCorpoAtingida getEntity(Long id)
	{
		ParteCorpoAtingida parteCorpoAtingida = getEntity();
		parteCorpoAtingida.setId(id);

		return parteCorpoAtingida;
	}

	public static Collection<ParteCorpoAtingida> getCollection()
	{
		Collection<ParteCorpoAtingida> parteCorpoAtingidas = new ArrayList<ParteCorpoAtingida>();
		parteCorpoAtingidas.add(getEntity());

		return parteCorpoAtingidas;
	}
	
	public static Collection<ParteCorpoAtingida> getCollection(Long id)
	{
		Collection<ParteCorpoAtingida> parteCorpoAtingidas = new ArrayList<ParteCorpoAtingida>();
		parteCorpoAtingidas.add(getEntity(id));
		
		return parteCorpoAtingidas;
	}
}
