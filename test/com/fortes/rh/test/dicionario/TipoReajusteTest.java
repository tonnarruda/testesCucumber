package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoAplicacaoReajuste;

public class TipoReajusteTest extends TestCase
{
	public void testMap()
	{
		TipoAplicacaoReajuste tipoAplicacaoReajuste = new TipoAplicacaoReajuste();
	
		assertEquals(2, tipoAplicacaoReajuste.size());
	}
}
