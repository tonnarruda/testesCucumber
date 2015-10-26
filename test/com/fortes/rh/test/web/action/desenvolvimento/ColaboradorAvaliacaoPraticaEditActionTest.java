package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorAvaliacaoPraticaEditAction;

public class ColaboradorAvaliacaoPraticaEditActionTest extends MockObjectTestCase
{
	private ColaboradorAvaliacaoPraticaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ColaboradorAvaliacaoPraticaManager.class);
		action = new ColaboradorAvaliacaoPraticaEditAction();
		action.setColaboradorAvaliacaoPraticaManager((ColaboradorAvaliacaoPraticaManager) manager.proxy());

		action.setColaboradorAvaliacaoPratica(new ColaboradorAvaliacaoPratica());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<ColaboradorAvaliacaoPratica>()));
		assertEquals("success", action.list());
		assertNotNull(action.getColaboradorAvaliacaoPraticas());
	}

	public void testDelete() throws Exception
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		action.setColaboradorAvaliacaoPratica(colaboradorAvaliacaoPratica);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<ColaboradorAvaliacaoPratica>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		action.setColaboradorAvaliacaoPratica(colaboradorAvaliacaoPratica);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		action.setColaboradorAvaliacaoPratica(colaboradorAvaliacaoPratica);

		manager.expects(once()).method("save").with(eq(colaboradorAvaliacaoPratica)).will(returnValue(colaboradorAvaliacaoPratica));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		action.setColaboradorAvaliacaoPratica(colaboradorAvaliacaoPratica);

		manager.expects(once()).method("update").with(eq(colaboradorAvaliacaoPratica)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		action.setColaboradorAvaliacaoPratica(colaboradorAvaliacaoPratica);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(colaboradorAvaliacaoPratica.getId())).will(returnValue(colaboradorAvaliacaoPratica));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setColaboradorAvaliacaoPratica(null);

		assertNotNull(action.getColaboradorAvaliacaoPratica());
		assertTrue(action.getColaboradorAvaliacaoPratica() instanceof ColaboradorAvaliacaoPratica);
	}
}
