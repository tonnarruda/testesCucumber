package com.fortes.rh.test.model.sesmt;

import junit.framework.TestCase;

import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.test.factory.sesmt.AfastamentoFactory;

public class AfastamentoTest extends TestCase
{
	public void testGetInssFmt()
	{
		Afastamento afastamento = AfastamentoFactory.getEntity(1L);
		
		afastamento.setInss(true);
		assertEquals("Sim", afastamento.getInssFmt());
		
		afastamento.setInss(false);
		assertEquals("Não", afastamento.getInssFmt());
	}
}
