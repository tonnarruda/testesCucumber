package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.AreaVivenciaPcmatManager;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.test.factory.sesmt.AreaVivenciaPcmatFactory;
import com.fortes.rh.web.action.sesmt.AreaVivenciaPcmatEditAction;

public class AreaVivenciaPcmatEditActionTest extends MockObjectTestCase
{
	private AreaVivenciaPcmatEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AreaVivenciaPcmatManager.class);
		action = new AreaVivenciaPcmatEditAction();
		action.setAreaVivenciaPcmatManager((AreaVivenciaPcmatManager) manager.proxy());

		action.setAreaVivenciaPcmat(new AreaVivenciaPcmat());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<AreaVivenciaPcmat>()));
		assertEquals("success", action.list());
		assertNotNull(action.getAreasVivenciaPcmat());
	}

	public void testDelete() throws Exception
	{
		AreaVivenciaPcmat areaVivenciaPcmat = AreaVivenciaPcmatFactory.getEntity(1L);
		action.setAreaVivenciaPcmat(areaVivenciaPcmat);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<AreaVivenciaPcmat>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		AreaVivenciaPcmat areaVivenciaPcmat = AreaVivenciaPcmatFactory.getEntity(1L);
		action.setAreaVivenciaPcmat(areaVivenciaPcmat);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		AreaVivenciaPcmat areaVivenciaPcmat = AreaVivenciaPcmatFactory.getEntity(1L);
		action.setAreaVivenciaPcmat(areaVivenciaPcmat);

		manager.expects(once()).method("save").with(eq(areaVivenciaPcmat)).will(returnValue(areaVivenciaPcmat));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		AreaVivenciaPcmat areaVivenciaPcmat = AreaVivenciaPcmatFactory.getEntity(1L);
		action.setAreaVivenciaPcmat(areaVivenciaPcmat);

		manager.expects(once()).method("update").with(eq(areaVivenciaPcmat)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		AreaVivenciaPcmat areaVivenciaPcmat = AreaVivenciaPcmatFactory.getEntity(1L);
		action.setAreaVivenciaPcmat(areaVivenciaPcmat);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(areaVivenciaPcmat.getId())).will(returnValue(areaVivenciaPcmat));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setAreaVivenciaPcmat(null);

		assertNotNull(action.getAreaVivenciaPcmat());
		assertTrue(action.getAreaVivenciaPcmat() instanceof AreaVivenciaPcmat);
	}
}
