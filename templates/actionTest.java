package com.fortes.rh.test.web.action.#NOME_PACOTE#;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.hibernate.ObjectNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.#NOME_PACOTE#.#NOME_CLASSE#Manager;
import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;
import com.fortes.rh.test.factory.#NOME_PACOTE#.#NOME_CLASSE#Factory;
import com.fortes.rh.web.action.#NOME_PACOTE#.#NOME_CLASSE#EditAction;

public class #NOME_CLASSE#EditActionTest
{
	private #NOME_CLASSE#EditAction action;
	private #NOME_CLASSE#Manager manager;

	@Before
	public void setUp() throws Exception
	{
		action = new #NOME_CLASSE#EditAction();

		manager = mock(#NOME_CLASSE#Manager.class);
		action.set#NOME_CLASSE#Manager(manager);

		action.set#NOME_CLASSE#(new #NOME_CLASSE#());
	}

	@After
	public void tearDown() throws Exception
	{
		manager = null;
		action = null;
	}

	@Test
	public void testList() throws Exception
	{
		when(manager.findAll()).thenReturn(new ArrayList<#NOME_CLASSE#>());
		
		assertEquals("success", action.list());
		assertNotNull(action.get#NOME_CLASSE#s());
	}
	
	@Test
	public void testDelete() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		when(manager.findAll()).thenReturn(new ArrayList<#NOME_CLASSE#>());
		
		assertEquals("success", action.delete());
	}
	
	@Test
	public void testDeleteException() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);
		
		doThrow(Exception.class).when(manager).remove(#NOME_CLASSE_MINUSCULO#.getId());
				
		assertEquals("success", action.delete());
	}
	
	@Test
	public void testInsert() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		when(manager.save(#NOME_CLASSE_MINUSCULO#)).thenReturn(#NOME_CLASSE_MINUSCULO#);

		assertEquals("success", action.insert());
	}

	@Test(expected=Exception.class)
	public void testInsertException() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		doThrow(Exception.class).when(manager).save(#NOME_CLASSE_MINUSCULO#);
		
		assertEquals("input", action.insert());
	}

	@Test
	public void testUpdate() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		assertEquals("success", action.update());
		
		verify(manager).update(#NOME_CLASSE_MINUSCULO#);
	}

	@Test(expected=Exception.class)
	public void testUpdateException() throws Exception
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = #NOME_CLASSE#Factory.getEntity(1L);
		action.set#NOME_CLASSE#(#NOME_CLASSE_MINUSCULO#);

		doThrow(Exception.class).when(manager).update(#NOME_CLASSE_MINUSCULO#);
		when(manager.findById(#NOME_CLASSE_MINUSCULO#.getId())).thenReturn(#NOME_CLASSE_MINUSCULO#);

		assertEquals("input", action.update());
	}

	@Test
	public void testGetSet() throws Exception
	{
		action.set#NOME_CLASSE#(null);

		assertNotNull(action.get#NOME_CLASSE#());
		assertTrue(action.get#NOME_CLASSE#() instanceof #NOME_CLASSE#);
	}
}