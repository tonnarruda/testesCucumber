package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.SituacaoFormacao;

public class SituacaoFormacaoTest extends TestCase
{
	public void testMap()
	{
		SituacaoFormacao situacaoFormacao = new SituacaoFormacao();
	
		assertEquals(3, situacaoFormacao.size());
	}
}
