package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoAvaliacao;

public class TipoAvaliacaoTest extends TestCase
{
	public void testMap()
	{
		TipoAvaliacao tipo = new TipoAvaliacao();
	
		assertEquals(3, tipo.size());
	}

}
