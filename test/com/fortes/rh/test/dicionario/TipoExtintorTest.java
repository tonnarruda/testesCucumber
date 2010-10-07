package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoExtintor;

public class TipoExtintorTest extends TestCase
{
	public void testTipoExtintor()
	{
		TipoExtintor tipo = new TipoExtintor();

		assertEquals(5, tipo.size());
		assertEquals("AG - Água Gás (Classe A)", tipo.get("1"));
		assertEquals("AP - Água Pressurizada (Classe A)", tipo.get("2"));
		assertEquals("PQS - Pó Químico Seco (Classes B e C)", tipo.get("3"));
		assertEquals("PQS - Pó Químico Seco (Classes A,B e C)", tipo.get("4"));
		assertEquals("CO2 - Dióxido de Carbono (Classes B e C)", tipo.get("5"));
		
	}
}
