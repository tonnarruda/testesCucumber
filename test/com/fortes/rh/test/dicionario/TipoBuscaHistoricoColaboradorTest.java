package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;

public class TipoBuscaHistoricoColaboradorTest extends TestCase
{
	public void testConstante()
	{
		//Serve apenas para a cobertura
		TipoBuscaHistoricoColaborador tipoBuscaHistoricoColaborador = new TipoBuscaHistoricoColaborador();
		
		assertEquals(0, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals(1, TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO);
	}

}
