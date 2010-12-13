package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.web.action.captacao.AtitudeEditAction;

public class AtitudeEditActionTest extends MockObjectTestCase
{
	private AtitudeEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AtitudeManager.class);
		action = new AtitudeEditAction();
		action.setAtitudeManager((AtitudeManager) manager.proxy());

		action.setAtitude(new Atitude());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Atitude>()));
		assertEquals("success", action.list());
		assertNotNull(action.getAtitudes());
	}

	public void testDelete() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Atitude>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		manager.expects(once()).method("save").with(eq(atitude)).will(returnValue(atitude));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		manager.expects(once()).method("update").with(eq(atitude)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(atitude.getId())).will(returnValue(atitude));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setAtitude(null);

		assertNotNull(action.getAtitude());
		assertTrue(action.getAtitude() instanceof Atitude);
	}
}
