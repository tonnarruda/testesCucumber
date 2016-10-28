package com.fortes.rh.test.web.action.#NOME_PACOTE#;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.#NOME_PACOTE#.#NOME_CLASSE#Manager;
import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;
import com.fortes.rh.test.factory.#NOME_PACOTE#.#NOME_CLASSE#Factory;
import com.fortes.rh.web.action.#NOME_PACOTE#.#NOME_CLASSE#EditAction;

public class #NOME_CLASSE#EditActionTest extends MockObjectTestCase
{
	private #NOME_CLASSE#EditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(#NOME_CLASSE#Manager.class);
		action = new #NOME_CLASSE#EditAction();
		action.set#NOME_CLASSE#Manager((#NOME_CLASSE#Manager) manager.proxy());

		action.set#NOME_CLASSE#(new #NOME_CLASSE#());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<#NOME_CLASSE#>()));
		assertEquals("success", action.list());
		assertNotNull(action.get#NOME_CLASSE#s());
	}

	public void testDelete() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<#NOME_CLASSE#>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		manager.expects(once()).method("save").with(eq(#NOME_CLASSE_MINUSCULO#)).will(returnValue(#NOME_CLASSE_MINUSCULO#));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		manager.expects(once()).method("update").with(eq(#NOME_CLASSE_MINUSCULO#)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(#NOME_CLASSE_MINUSCULO#.getId())).will(returnValue(#NOME_CLASSE_MINUSCULO#));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.set#NOME_CLASSE#(null);

		assertNotNull(action.get#NOME_CLASSE#());
		assertTrue(action.get#NOME_CLASSE#() instanceof #NOME_CLASSE#);
	}
}