package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;

public class MotivoHistoricoColaboradorTest extends TestCase
{
	public void testMap()
	{
		MotivoHistoricoColaborador motivos = new MotivoHistoricoColaborador();
	
		assertEquals(8, motivos.size());
	}
}
