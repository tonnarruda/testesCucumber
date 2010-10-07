package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoReajusteColaborador;

public class TipoReajusteColaboradorTest extends TestCase
{
	public void testMap()
	{
		TipoReajusteColaborador tipoReajusteColaborador = new TipoReajusteColaborador();
	
		assertEquals(3, tipoReajusteColaborador.size());
	}
}
