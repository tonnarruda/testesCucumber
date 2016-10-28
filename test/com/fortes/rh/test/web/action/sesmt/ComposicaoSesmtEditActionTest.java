package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.ComposicaoSesmtManager;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;
import com.fortes.rh.test.factory.sesmt.ComposicaoSesmtFactory;
import com.fortes.rh.web.action.sesmt.ComposicaoSesmtEditAction;

public class ComposicaoSesmtEditActionTest extends MockObjectTestCase
{
	private ComposicaoSesmtEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ComposicaoSesmtManager.class);
		action = new ComposicaoSesmtEditAction();
		action.setComposicaoSesmtManager((ComposicaoSesmtManager) manager.proxy());

		action.setComposicaoSesmt(new ComposicaoSesmt());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<ComposicaoSesmt>()));
		assertEquals("success", action.list());
		assertNotNull(action.getComposicaoSesmts());
	}

	public void testDelete() throws Exception
	{
		ComposicaoSesmt composicaoSesmt = ComposicaoSesmtFactory.getEntity(1L);
		action.setComposicaoSesmt(composicaoSesmt);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<ComposicaoSesmt>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		ComposicaoSesmt composicaoSesmt = ComposicaoSesmtFactory.getEntity(1L);
		action.setComposicaoSesmt(composicaoSesmt);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		ComposicaoSesmt composicaoSesmt = ComposicaoSesmtFactory.getEntity(1L);
		action.setComposicaoSesmt(composicaoSesmt);

		manager.expects(once()).method("save").with(eq(composicaoSesmt)).will(returnValue(composicaoSesmt));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ComposicaoSesmt composicaoSesmt = ComposicaoSesmtFactory.getEntity(1L);
		action.setComposicaoSesmt(composicaoSesmt);

		manager.expects(once()).method("update").with(eq(composicaoSesmt)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		ComposicaoSesmt composicaoSesmt = ComposicaoSesmtFactory.getEntity(1L);
		action.setComposicaoSesmt(composicaoSesmt);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(composicaoSesmt.getId())).will(returnValue(composicaoSesmt));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setComposicaoSesmt(null);

		assertNotNull(action.getComposicaoSesmt());
		assertTrue(action.getComposicaoSesmt() instanceof ComposicaoSesmt);
	}
}
