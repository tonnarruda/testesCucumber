package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;

public class StatusCandidatoSolicitacaoTest extends TestCase
{
	public void testStatusCandidato()
	{
		StatusCandidatoSolicitacao status = new StatusCandidatoSolicitacao();

		assertEquals(4, status.size());
		assertEquals("Indiferente", status.get('I'));
		assertEquals("Contratado", status.get('C'));
		assertEquals("Promovido", status.get('P'));
		assertEquals("A ser promovido", status.get('A'));
	}
}
