package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.OrdemDeServicoManager;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.test.factory.sesmt.OrdemDeServicoFactory;
import com.fortes.rh.web.action.sesmt.OrdemDeServicoEditAction;

public class OrdemDeServicoEditActionTest extends MockObjectTestCase
{
	private OrdemDeServicoEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(OrdemDeServicoManager.class);
		action = new OrdemDeServicoEditAction();
		action.setOrdemDeServicoManager((OrdemDeServicoManager) manager.proxy());

		action.setOrdemDeServico(new OrdemDeServico());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<OrdemDeServico>()));
		assertEquals("success", action.list());
		assertNotNull(action.getOrdemDeServicos());
	}

	public void testDelete() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<OrdemDeServico>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);

		manager.expects(once()).method("save").with(eq(ordemDeServico)).will(returnValue(ordemDeServico));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);

		manager.expects(once()).method("update").with(eq(ordemDeServico)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity(1L);
		action.setOrdemDeServico(ordemDeServico);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(ordemDeServico.getId())).will(returnValue(ordemDeServico));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setOrdemDeServico(null);

		assertNotNull(action.getOrdemDeServico());
		assertTrue(action.getOrdemDeServico() instanceof OrdemDeServico);
	}
}
