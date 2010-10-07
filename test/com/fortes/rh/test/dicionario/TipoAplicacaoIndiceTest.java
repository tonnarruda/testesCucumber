package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;

public class TipoAplicacaoIndiceTest extends TestCase
{
	public void testTipoAplicacaoIndice()
	{
		TipoAplicacaoIndice tipo = new TipoAplicacaoIndice();
	
		assertEquals(3, tipo.size());
		assertEquals("Por cargo", tipo.get(1));
		assertEquals("Por índice", tipo.get(2));
		assertEquals("Por valor", tipo.get(3));
	}

	public void testGet()
	{
		assertEquals(1, TipoAplicacaoIndice.getCargo());
		assertEquals(2, TipoAplicacaoIndice.getIndice());
		assertEquals(3, TipoAplicacaoIndice.getValor());
	}
}
