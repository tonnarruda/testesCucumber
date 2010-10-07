package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.Estado;

public class EstadoTest extends TestCase
{
	public void testMap()
	{
		Estado estados = new Estado();
	
		assertEquals(27, estados.size());
	}
}
