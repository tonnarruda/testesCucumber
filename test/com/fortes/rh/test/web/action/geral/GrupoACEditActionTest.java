package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.factory.geral.GrupoACFactory;
import com.fortes.rh.web.action.geral.GrupoACEditAction;

public class GrupoACEditActionTest extends MockObjectTestCase
{
	private GrupoACEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(GrupoACManager.class);
		action = new GrupoACEditAction();
		action.setGrupoACManager((GrupoACManager) manager.proxy());

		action.setGrupoAC(new GrupoAC());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<GrupoAC>()));
		assertEquals("success", action.list());
		assertNotNull(action.getGrupoACs());
	}

	public void testDelete() throws Exception
	{
		GrupoAC grupoAC = GrupoACFactory.getEntity(1L);
		action.setGrupoAC(grupoAC);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<GrupoAC>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		GrupoAC grupoAC = GrupoACFactory.getEntity(1L);
		action.setGrupoAC(grupoAC);

		manager.expects(once()).method("save").with(eq(grupoAC)).will(returnValue(grupoAC));

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		GrupoAC grupoAC = GrupoACFactory.getEntity(1L);
		action.setGrupoAC(grupoAC);

		manager.expects(once()).method("update").with(eq(grupoAC)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setGrupoAC(null);

		assertNotNull(action.getGrupoAC());
		assertTrue(action.getGrupoAC() instanceof GrupoAC);
	}
}
