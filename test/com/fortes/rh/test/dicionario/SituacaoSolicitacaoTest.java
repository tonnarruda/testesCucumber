package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.SituacaoSolicitacao;

public class SituacaoSolicitacaoTest extends TestCase
{
	public void testMap()
	{
		SituacaoSolicitacao situacaoSolicitacao = new SituacaoSolicitacao();
	
		assertEquals(3, situacaoSolicitacao.size());
	}
}
