package com.fortes.rh.test.dicionario;

import static com.fortes.rh.model.dicionario.StatusRetornoAC.AGUARDANDO;
import static com.fortes.rh.model.dicionario.StatusRetornoAC.CANCELADO;
import static com.fortes.rh.model.dicionario.StatusRetornoAC.CONFIRMADO;
import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.StatusRetornoAC;

public class StatusRetornoACTest extends TestCase
{
	public void testStatusRetornoAC()
	{
		StatusRetornoAC status = new StatusRetornoAC();
	
		assertEquals(3, status.size());
		assertEquals("Confirmado", status.get(1));
		assertEquals("Aguardando Confirmação", status.get(2));
		assertEquals("Cancelado", status.get(3));
	}

	public void testGetImg()
	{
		assertEquals("flag_green.gif", StatusRetornoAC.getImg(CONFIRMADO));
		assertEquals("iconWarning.gif", StatusRetornoAC.getImg(AGUARDANDO));
		assertEquals("flag_red.gif", StatusRetornoAC.getImg(CANCELADO));
	}
	
	public void testGetDescricao()
	{
		assertEquals("Confirmado", StatusRetornoAC.getDescricao(CONFIRMADO));
		assertEquals("Aguardando Confirmação", StatusRetornoAC.getDescricao(AGUARDANDO));
		assertEquals("Cancelado", StatusRetornoAC.getDescricao(CANCELADO));
	}
	
	public void testGet()
	{
		assertEquals(1, StatusRetornoAC.getConfirmado());
		assertEquals(2, StatusRetornoAC.getAguardando());
		assertEquals(3, StatusRetornoAC.getCancelado());
	}
}
