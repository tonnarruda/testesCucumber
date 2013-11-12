package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.PcmatManager;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.web.action.sesmt.PcmatEditAction;

public class PcmatEditActionTest extends MockObjectTestCase
{
	private PcmatEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(PcmatManager.class);
		action = new PcmatEditAction();
		action.setPcmatManager((PcmatManager) manager.proxy());

		action.setPcmat(new Pcmat());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Pcmat>()));
		assertEquals("success", action.list());
		assertNotNull(action.getPcmats());
	}

	public void testDelete() throws Exception
	{
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Pcmat>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);

		manager.expects(once()).method("save").with(eq(pcmat)).will(returnValue(pcmat));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);

		manager.expects(once()).method("update").with(eq(pcmat)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(pcmat.getId())).will(returnValue(pcmat));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setPcmat(null);

		assertNotNull(action.getPcmat());
		assertTrue(action.getPcmat() instanceof Pcmat);
	}
}
