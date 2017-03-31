package com.fortes.rh.test.web.dwr;

import junit.framework.TestCase;

import com.fortes.rh.web.dwr.EnderecoDWR;

public class EnderecoDWRTest extends TestCase {

	EnderecoDWR enderecoDwr;
	
	public void setUp() {
		enderecoDwr = new EnderecoDWR();
	}
	
	public void testSeparaDadosDoEndereco() 
	{
		String json = enderecoDwr.buscaPorCep("60743-760");
		
		assertNotNull("json", json);
	}
	
	public void testDeveRetornarErroQuandoServidorDoYahooEstiverOffline() {
			assertTrue(enderecoDwr.buscaPorCep("63657520325aaaa").contains("404"));
	}
}
