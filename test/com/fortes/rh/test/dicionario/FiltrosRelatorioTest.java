package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.FiltrosRelatorio;

public class FiltrosRelatorioTest extends TestCase
{
	public void testMap()
	{
		FiltrosRelatorio filtros = new FiltrosRelatorio();
	
		assertEquals(4, filtros.size());
	}
}
