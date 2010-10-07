package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.EventoDao;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.EventoFactory;

public class EventoDaoHibernateTest extends GenericDaoHibernateTest<Evento>
{
	private EventoDao eventoDao;

	@Override
	public Evento getEntity()
	{
		return EventoFactory.getEntity();
	}

	@Override
	public GenericDao<Evento> getGenericDao()
	{
		return eventoDao;
	}

	public void setEventoDao(EventoDao eventoDao)
	{
		this.eventoDao = eventoDao;
	}
}
