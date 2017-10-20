package com.fortes.rh.test.business.#NOME_PACOTE#;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.junit.*;

import com.fortes.rh.business.#NOME_PACOTE#.#NOME_CLASSE#ManagerImpl;
import com.fortes.rh.dao.#NOME_PACOTE#.#NOME_CLASSE#Dao;
import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;
import com.fortes.rh.test.factory.#NOME_PACOTE#.#NOME_CLASSE#Factory;

public class #NOME_CLASSE#ManagerTest
{
	private #NOME_CLASSE#ManagerImpl #NOME_CLASSE_MINUSCULO#Manager = new #NOME_CLASSE#ManagerImpl();
	private #NOME_CLASSE#Dao #NOME_CLASSE_MINUSCULO#Dao;
	
	@Before
	public void setUp() throws Exception
    {
        #NOME_CLASSE_MINUSCULO#Dao = mock(#NOME_CLASSE#Dao.class);
        #NOME_CLASSE_MINUSCULO#Manager.setDao(#NOME_CLASSE_MINUSCULO#Dao);
    }

	@Test
	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<#NOME_CLASSE#> #NOME_CLASSE_MINUSCULO#s = #NOME_CLASSE#Factory.getCollection(1L);

		when(#NOME_CLASSE_MINUSCULO#Dao.findAllSelect(empresaId)).thenReturn(#NOME_CLASSE_MINUSCULO#s);
		assertEquals(#NOME_CLASSE_MINUSCULO#s, #NOME_CLASSE_MINUSCULO#Manager.findAllSelect(empresaId));
	}
}