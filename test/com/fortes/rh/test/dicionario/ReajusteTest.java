package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoReajuste;

public class ReajusteTest extends TestCase
{
	public void testMap()
	{
		TipoReajuste tipoReajuste = new TipoReajuste();
	
		assertEquals(3, tipoReajuste.size());
	}
}
