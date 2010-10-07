package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoDeExposicao;

public class TipoDeExposicaoTest extends TestCase
{
	public void testMap()
	{
		TipoDeExposicao tipoDeExposicao = new TipoDeExposicao();
	
		assertEquals(3, tipoDeExposicao.size());
	}
}
