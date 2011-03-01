package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.Apto;

public class AptoTest extends TestCase
{
	public void testMap()
	{
		Apto apto = new Apto();
	
		assertEquals(3, apto.size());
	}

	public void testGetDescSexoParaFicha()
	{
		assertEquals("-", Apto.getDescApto('I'));
		assertEquals("Sim", Apto.getDescApto('S'));
		assertEquals("Não", Apto.getDescApto('N'));
	}
}
