package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoAcidente;

public class TipoAcidenteTest extends TestCase
{
	public void testMap()
	{
		TipoAcidente TipoAcidente = new TipoAcidente();
	
		assertEquals(3, TipoAcidente.size());
		
		assertEquals(1, TipoAcidente.tipico);
		assertEquals(2, TipoAcidente.trajeto);
		assertEquals(3, TipoAcidente.doenca_trabalho);
	}

}
