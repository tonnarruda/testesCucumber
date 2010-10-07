package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.Sexo;

public class SexoTest extends TestCase
{
	public void testMap()
	{
		Sexo sexo = new Sexo();
	
		assertEquals(3, sexo.size());
	}

	public void testGetDescSexoParaFicha()
	{
		assertEquals("(x) Masc.  ( ) Fem.", Sexo.getDescSexoParaFicha('M'));
		assertEquals("( ) Masc.  (x) Fem.", Sexo.getDescSexoParaFicha('F'));
		assertEquals("( ) Masc.  ( ) Fem.", Sexo.getDescSexoParaFicha('I'));
	}
}
