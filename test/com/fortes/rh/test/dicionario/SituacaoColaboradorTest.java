package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.SituacaoColaborador;

public class SituacaoColaboradorTest extends TestCase
{
	public void testMap()
	{
		SituacaoColaborador situacaoColaborador = new SituacaoColaborador();
	
		assertEquals(3, situacaoColaborador.size());
	}
}
