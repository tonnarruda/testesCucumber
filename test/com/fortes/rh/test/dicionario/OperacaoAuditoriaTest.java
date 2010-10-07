package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.OperacaoAuditoria;

public class OperacaoAuditoriaTest extends TestCase
{
	public void testMap()
	{
		OperacaoAuditoria OperacaoAuditoria = new OperacaoAuditoria();
	
		assertEquals(4, OperacaoAuditoria.size());
	}
}
