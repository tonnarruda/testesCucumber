package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;

public class StatusAprovacaoSolicitacaoTest extends TestCase
{
	public void testStatus()
	{
		StatusAprovacaoSolicitacao status = new StatusAprovacaoSolicitacao();
	
		assertEquals(3, status.size());
		assertEquals("Em análise", status.get('I'));
		assertEquals("Aprovada", status.get('A'));
		assertEquals("Reprovada", status.get('R'));
	}

	public void testDescricao()
	{
		assertEquals("Em análise", StatusAprovacaoSolicitacao.getDescricao('I'));
		assertEquals("Aprovada", StatusAprovacaoSolicitacao.getDescricao('A'));
		assertEquals("Reprovada", StatusAprovacaoSolicitacao.getDescricao('R'));
	}
	
}
