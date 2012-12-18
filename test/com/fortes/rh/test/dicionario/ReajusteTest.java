package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.Reajuste;

public class ReajusteTest extends TestCase
{
	public void testMap()
	{
		Reajuste reajuste = new Reajuste();
	
		assertEquals(3, reajuste.size());
	}
}
