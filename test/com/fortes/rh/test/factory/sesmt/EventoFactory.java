package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.Evento;

public class EventoFactory
{
	public static Evento getEntity()
	{
		Evento evento = new Evento();
		evento.setId(null);
		evento.setNome("teste");
		return evento;
	}

	public static Evento getEntity(Long id)
	{
		Evento evento = getEntity();
		evento.setId(id);

		return evento;
	}

	public static Collection<Evento> getCollection()
	{
		Collection<Evento> eventos = new ArrayList<Evento>();
		eventos.add(getEntity());

		return eventos;
	}
	
	public static Collection<Evento> getCollection(Long id)
	{
		Collection<Evento> eventos = new ArrayList<Evento>();
		eventos.add(getEntity(id));
		
		return eventos;
	}
}
