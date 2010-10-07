package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoPergunta;

public class TipoPerguntaTest extends TestCase
{
	public void testTipoPergunta()
	{
		TipoPergunta tipo = new TipoPergunta();
	
		assertEquals(4, tipo.size());
		assertEquals("Objetiva", tipo.get(1));
		assertEquals("Subjetiva", tipo.get(3));
		assertEquals("Nota", tipo.get(4));
		assertEquals("Múltipla Escolha", tipo.get(5));
	}

	public void testGet()
	{
		TipoPergunta tipo = new TipoPergunta();
		assertEquals(1, tipo.getObjetiva());
		assertEquals(3, tipo.getSubjetiva());
		assertEquals(4, tipo.getNota());
		assertEquals(5, tipo.getMultiplaEscolha());
	}
}
