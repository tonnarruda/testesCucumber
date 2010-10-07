package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.Agenda;

public class AgendaFactory
{
	public static Agenda getEntity()
	{
		Agenda agenda = new Agenda();
		agenda.setId(null);
		return agenda;
	}

	public static Agenda getEntity(Long id)
	{
		Agenda agenda = getEntity();
		agenda.setId(id);

		return agenda;
	}

	public static Collection<Agenda> getCollection()
	{
		Collection<Agenda> agendas = new ArrayList<Agenda>();
		agendas.add(getEntity());

		return agendas;
	}
	
	public static Collection<Agenda> getCollection(Long id)
	{
		Collection<Agenda> agendas = new ArrayList<Agenda>();
		agendas.add(getEntity(id));
		
		return agendas;
	}
}
