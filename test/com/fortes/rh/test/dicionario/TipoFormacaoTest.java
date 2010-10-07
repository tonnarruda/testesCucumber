package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoFormacao;

public class TipoFormacaoTest extends TestCase
{
	public void testMap()
	{
		TipoFormacao tipoFormacao = new TipoFormacao();
	
		assertEquals(6, tipoFormacao.size());
	}
}
