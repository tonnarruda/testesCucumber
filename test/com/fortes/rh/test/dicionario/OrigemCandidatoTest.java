package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.OrigemCandidato;

public class OrigemCandidatoTest extends TestCase
{
	public void testMap()
	{
		OrigemCandidato OrigemCandidato = new OrigemCandidato();
	
		assertEquals(6, OrigemCandidato.size());
	}
}
