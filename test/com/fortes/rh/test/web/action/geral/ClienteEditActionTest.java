package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.ClienteManager;
import com.fortes.rh.model.geral.Cliente;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.ClienteFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.ClienteEditAction;
import com.opensymphony.xwork.ActionContext;

public class ClienteEditActionTest extends MockObjectTestCase
{
	private ClienteEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ClienteManager.class);
		action = new ClienteEditAction();
		action.setClienteManager((ClienteManager) manager.proxy());

		action.setCliente(new Cliente());
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
        MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Cliente>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getClientes());
	}

	public void testDelete() throws Exception
	{
		Cliente cliente = ClienteFactory.getEntity(1L);
		action.setCliente(cliente);

		manager.expects(once()).method("remove");
		assertEquals(action.delete(), "success");
	}
	
	public void testDeleteException() throws Exception
	{
		Cliente cliente = ClienteFactory.getEntity(1L);
		action.setCliente(cliente);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));;
		assertEquals(action.delete(), "success");
	}

	public void testInsert() throws Exception
	{
		Cliente cliente = ClienteFactory.getEntity(1L);
		action.setCliente(cliente);

		manager.expects(once()).method("save").with(eq(cliente)).will(returnValue(cliente));

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Cliente cliente = ClienteFactory.getEntity(1L);
		action.setCliente(cliente);

		manager.expects(once()).method("update").with(eq(cliente)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setCliente(null);

		assertNotNull(action.getCliente());
		assertTrue(action.getCliente() instanceof Cliente);
	}
}
