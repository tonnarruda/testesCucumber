package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.Deficiencia;

public class DeficienciaTest extends TestCase
{
	public void testMap()
	{
		Deficiencia deficiencias = new Deficiencia();
	
		assertEquals(7, deficiencias.size());
	}
}
