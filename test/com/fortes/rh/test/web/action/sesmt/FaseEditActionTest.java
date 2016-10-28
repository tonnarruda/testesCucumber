package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.FaseManager;
import com.fortes.rh.model.sesmt.Fase;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.FaseFactory;
import com.fortes.rh.web.action.sesmt.FaseEditAction;

public class FaseEditActionTest extends MockObjectTestCase
{
	private FaseEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(FaseManager.class);
		action = new FaseEditAction();
		action.setFaseManager((FaseManager) manager.proxy());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

		action.setFase(new Fase());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Fase>()));
		assertEquals("success", action.list());
		assertNotNull(action.getFases());
	}

	public void testDelete() throws Exception
	{
		Fase fase = FaseFactory.getEntity(1L);
		action.setFase(fase);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Fase>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Fase fase = FaseFactory.getEntity(1L);
		action.setFase(fase);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Fase>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Fase fase = FaseFactory.getEntity(1L);
		action.setFase(fase);

		manager.expects(once()).method("save").with(eq(fase)).will(returnValue(fase));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Fase fase = FaseFactory.getEntity(1L);
		action.setFase(fase);

		manager.expects(once()).method("update").with(eq(fase)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Fase fase = FaseFactory.getEntity(1L);
		action.setFase(fase);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(fase.getId())).will(returnValue(fase));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setFase(null);

		assertNotNull(action.getFase());
		assertTrue(action.getFase() instanceof Fase);
	}
}
