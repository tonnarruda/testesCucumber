package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EventoManager;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.factory.sesmt.EventoFactory;
import com.fortes.rh.web.action.sesmt.EventoEditAction;

public class EventoEditActionTest extends MockObjectTestCase
{
	private EventoEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(EventoManager.class);
		action = new EventoEditAction();
		action.setEventoManager((EventoManager) manager.proxy());

		action.setEvento(new Evento());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Evento>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getEventos());
	}

	public void testDelete() throws Exception
	{
		Evento evento = EventoFactory.getEntity(1L);
		action.setEvento(evento);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Evento>()));
		assertEquals(action.delete(), "success");
	}

	public void testInsert() throws Exception
	{
		Evento evento = EventoFactory.getEntity(1L);
		action.setEvento(evento);

		manager.expects(once()).method("save").with(eq(evento)).will(returnValue(evento));

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Evento evento = EventoFactory.getEntity(1L);
		action.setEvento(evento);

		manager.expects(once()).method("update").with(eq(evento)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setEvento(null);

		assertNotNull(action.getEvento());
		assertTrue(action.getEvento() instanceof Evento);
	}
}
