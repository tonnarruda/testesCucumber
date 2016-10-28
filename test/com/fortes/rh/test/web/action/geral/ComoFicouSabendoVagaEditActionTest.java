package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.ComoFicouSabendoVagaManager;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.test.factory.geral.ComoFicouSabendoVagaFactory;
import com.fortes.rh.web.action.geral.ComoFicouSabendoVagaEditAction;

public class ComoFicouSabendoVagaEditActionTest extends MockObjectTestCase
{
	private ComoFicouSabendoVagaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ComoFicouSabendoVagaManager.class);
		action = new ComoFicouSabendoVagaEditAction();
		action.setComoFicouSabendoVagaManager((ComoFicouSabendoVagaManager) manager.proxy());

		action.setComoFicouSabendoVaga(new ComoFicouSabendoVaga());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAllSemOutros").will(returnValue(new ArrayList<ComoFicouSabendoVaga>()));
		assertEquals("success", action.list());
		assertNotNull(action.getComoFicouSabendoVagas());
	}

	public void testDelete() throws Exception
	{
		ComoFicouSabendoVaga comoFicouSabendoVaga = ComoFicouSabendoVagaFactory.getEntity(1L);
		action.setComoFicouSabendoVaga(comoFicouSabendoVaga);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSemOutros").will(returnValue(new ArrayList<ComoFicouSabendoVaga>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		ComoFicouSabendoVaga comoFicouSabendoVaga = ComoFicouSabendoVagaFactory.getEntity(1L);
		action.setComoFicouSabendoVaga(comoFicouSabendoVaga);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findAllSemOutros").will(returnValue(new ArrayList<ComoFicouSabendoVaga>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		ComoFicouSabendoVaga comoFicouSabendoVaga = ComoFicouSabendoVagaFactory.getEntity(1L);
		action.setComoFicouSabendoVaga(comoFicouSabendoVaga);

		manager.expects(once()).method("save").with(eq(comoFicouSabendoVaga)).will(returnValue(comoFicouSabendoVaga));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ComoFicouSabendoVaga comoFicouSabendoVaga = ComoFicouSabendoVagaFactory.getEntity(1L);
		action.setComoFicouSabendoVaga(comoFicouSabendoVaga);

		manager.expects(once()).method("update").with(eq(comoFicouSabendoVaga)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		ComoFicouSabendoVaga comoFicouSabendoVaga = ComoFicouSabendoVagaFactory.getEntity(1L);
		action.setComoFicouSabendoVaga(comoFicouSabendoVaga);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(comoFicouSabendoVaga.getId())).will(returnValue(comoFicouSabendoVaga));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setComoFicouSabendoVaga(null);

		assertNotNull(action.getComoFicouSabendoVaga());
		assertTrue(action.getComoFicouSabendoVaga() instanceof ComoFicouSabendoVaga);
	}
}
