package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.NivelIdioma;

public class NivelIdiomaTest extends TestCase
{
	public void testMap()
	{
		NivelIdioma nivel = new NivelIdioma();
	
		assertEquals(3, nivel.size());
	}
}
