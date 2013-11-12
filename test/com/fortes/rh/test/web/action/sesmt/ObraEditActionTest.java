package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.ObraManager;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.test.factory.sesmt.ObraFactory;
import com.fortes.rh.web.action.sesmt.ObraEditAction;

public class ObraEditActionTest extends MockObjectTestCase
{
	private ObraEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ObraManager.class);
		action = new ObraEditAction();
		action.setObraManager((ObraManager) manager.proxy());

		action.setObra(new Obra());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Obra>()));
		assertEquals("success", action.list());
		assertNotNull(action.getObras());
	}

	public void testDelete() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Obra>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);

		manager.expects(once()).method("save").with(eq(obra)).will(returnValue(obra));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);

		manager.expects(once()).method("update").with(eq(obra)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(obra.getId())).will(returnValue(obra));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setObra(null);

		assertNotNull(action.getObra());
		assertTrue(action.getObra() instanceof Obra);
	}
}
