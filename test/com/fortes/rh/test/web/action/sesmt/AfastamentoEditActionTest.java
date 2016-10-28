package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.test.factory.sesmt.AfastamentoFactory;
import com.fortes.rh.web.action.sesmt.AfastamentoEditAction;

public class AfastamentoEditActionTest extends MockObjectTestCase
{
	private AfastamentoEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AfastamentoManager.class);
		action = new AfastamentoEditAction();
		action.setAfastamentoManager((AfastamentoManager) manager.proxy());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		Afastamento afastamento = AfastamentoFactory.getEntity(1L);
		action.setAfastamento(afastamento);
		manager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Afastamento>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getAfastamentos());
	}

	public void testDelete() throws Exception
	{
		Afastamento afastamento = AfastamentoFactory.getEntity(1L);
		action.setAfastamento(afastamento);

		manager.expects(once()).method("remove").with(eq(afastamento.getId())).isVoid();
		manager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Afastamento>()));
		assertEquals(action.delete(), "success");
		assertEquals("Afastamento excluído com sucesso.", action.getActionMessages().toArray()[0]);
	}

	public void testDeleteException() throws Exception
	{
		Afastamento afastamento = AfastamentoFactory.getEntity(1L);
		action.setAfastamento(afastamento);

		manager.expects(once()).method("remove").with(eq(afastamento.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(afastamento.getId(), ""))));
		manager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Afastamento>()));
		assertEquals(action.delete(), "success");
		assertEquals("Não foi possível excluir este afastamento.", action.getActionErrors().toArray()[0]);
	}

	public void testPrepare() throws Exception
	{
		Afastamento afastamento = AfastamentoFactory.getEntity(1L);
		action.setAfastamento(afastamento);
		manager.expects(once()).method("findById").with(eq(afastamento.getId())).will(returnValue(afastamento));
		
		assertEquals("success", action.prepare());
	}

	public void testInsert() throws Exception
	{
		Afastamento afastamento = AfastamentoFactory.getEntity(1L);
		action.setAfastamento(afastamento);
		manager.expects(once()).method("save").with(eq(afastamento)).will(returnValue(afastamento));

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Afastamento afastamento = AfastamentoFactory.getEntity(1L);
		action.setAfastamento(afastamento);

		manager.expects(once()).method("update").with(eq(afastamento)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		assertNotNull(action.getAfastamento());

		Afastamento afastamento = new Afastamento();
		action.setAfastamento(afastamento);
		assertTrue(action.getAfastamento() instanceof Afastamento);

	}
}