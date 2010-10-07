package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.Mes;

public class MesTest extends TestCase
{
	public void testMap()
	{
		Mes mes = new Mes();
	
		assertEquals(12, mes.size());
	}
}
