package com.fortes.rh.test.dicionario;

import java.util.Date;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.StatusLnt;
import com.fortes.rh.util.DateUtil;

public class StatusLntTest extends TestCase
{
	public void testStatusLnt()
	{
		StatusLnt status = new StatusLnt();
	
		assertEquals(5, status.size());
		assertEquals("Todas", status.get('T'));
		assertEquals("Não iniciada", status.get('I'));
		assertEquals("Em planejamento", status.get('P'));
		assertEquals("Em análise", status.get('A'));
		assertEquals("Finalizada", status.get('F'));
	}

	public void testGetStatus()
	{
		assertNull("Data inicial nula", StatusLnt.getStatus(null, new Date(), null));
		assertNull("Data final nula", StatusLnt.getStatus(new Date(), null, null));
		assertEquals("LNT não iniciada(Inicio: amanhã)", (Character)StatusLnt.NAO_INICIADA, StatusLnt.getStatus(DateUtil.incrementaDias(new Date(), 1), DateUtil.incrementaDias(new Date(), 2), null));
		assertEquals("LNT em planejamento(Inicio: ontem ; Fim: amanhã)", (Character)StatusLnt.EM_PLANEJAMENTO, StatusLnt.getStatus(DateUtil.incrementaDias(new Date(), -1), DateUtil.incrementaDias(new Date(), 1), null));
		assertEquals("LNT em planejamento(Inicio: hoje ; Fim: amanhã)", (Character)StatusLnt.EM_PLANEJAMENTO, StatusLnt.getStatus(DateUtil.criarDataMesAno(new Date()), DateUtil.incrementaDias(new Date(), 1), null));
		assertEquals("LNT em planejamento(Inicio: ontem ; Fim: hoje)", (Character)StatusLnt.EM_PLANEJAMENTO, StatusLnt.getStatus(DateUtil.incrementaDias(new Date(), -1), DateUtil.criarDataMesAno(new Date()), null));
		assertEquals("LNT em planejamento(Inicio: hoje ; Fim: hoje)", (Character)StatusLnt.EM_PLANEJAMENTO, StatusLnt.getStatus(DateUtil.criarDataMesAno(new Date()), DateUtil.criarDataMesAno(new Date()), null));
		assertEquals("LNT em análise(Fim: ontem)", (Character)StatusLnt.EM_ANALISE, StatusLnt.getStatus(DateUtil.incrementaDias(new Date(), -2), DateUtil.incrementaDias(new Date(), -1), null));
		assertEquals("LNT finalizada", (Character)StatusLnt.FINALIZADA, StatusLnt.getStatus(DateUtil.incrementaDias(new Date(), -2), DateUtil.incrementaDias(new Date(), -1), DateUtil.incrementaDias(new Date(), 2)));
	}
	
	public void testGet()
	{
		assertEquals((Character)'I', StatusLnt.getNaoIniciada());
		assertEquals((Character)'P', StatusLnt.getEmPlanejamento());
		assertEquals((Character)'A', StatusLnt.getEmAnalise());
		assertEquals((Character)'F', StatusLnt.getFinalizada());
	}
}
