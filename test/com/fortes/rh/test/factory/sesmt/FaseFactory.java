package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.Fase;

public class FaseFactory
{
	public static Fase getEntity()
	{
		Fase fase = new Fase();
		fase.setId(null);
		fase.setDescricao("Fase");
		return fase;
	}

	public static Fase getEntity(Long id)
	{
		Fase fase = getEntity();
		fase.setId(id);

		return fase;
	}

	public static Collection<Fase> getCollection()
	{
		Collection<Fase> fases = new ArrayList<Fase>();
		fases.add(getEntity());

		return fases;
	}
	
	public static Collection<Fase> getCollection(Long id)
	{
		Collection<Fase> fases = new ArrayList<Fase>();
		fases.add(getEntity(id));
		
		return fases;
	}
}
