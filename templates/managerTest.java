package com.fortes.rh.test.business.#NOME_PACOTE#;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.#NOME_PACOTE#.#NOME_CLASSE#ManagerImpl;
import com.fortes.rh.dao.#NOME_PACOTE#.#NOME_CLASSE#Dao;
import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;
import com.fortes.rh.test.factory.#NOME_PACOTE#.#NOME_CLASSE#Factory;

public class #NOME_CLASSE#ManagerTest extends MockObjectTestCase
{
	private #NOME_CLASSE#ManagerImpl #NOME_CLASSE_MINUSCULO#Manager = new #NOME_CLASSE#ManagerImpl();
	private Mock #NOME_CLASSE_MINUSCULO#Dao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        #NOME_CLASSE_MINUSCULO#Dao = new Mock(#NOME_CLASSE#Dao.class);
        #NOME_CLASSE_MINUSCULO#Manager.setDao((#NOME_CLASSE#Dao) #NOME_CLASSE_MINUSCULO#Dao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<#NOME_CLASSE#> #NOME_CLASSE_MINUSCULO#s = #NOME_CLASSE#Factory.getCollection(1L);

		#NOME_CLASSE_MINUSCULO#Dao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(#NOME_CLASSE_MINUSCULO#s));
		assertEquals(#NOME_CLASSE_MINUSCULO#s, #NOME_CLASSE_MINUSCULO#Manager.findAllSelect(empresaId));
	}
}