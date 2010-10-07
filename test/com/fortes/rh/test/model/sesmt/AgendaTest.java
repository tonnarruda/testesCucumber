package com.fortes.rh.test.model.sesmt;

import junit.framework.TestCase;

import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.test.factory.sesmt.AgendaFactory;
import com.fortes.rh.util.DateUtil;

public class AgendaTest extends TestCase
{
	public void testSetDataAndEmpresa()
	{
		Agenda agenda = AgendaFactory.getEntity(1L);
		
		agenda.setDataMesAno("02/2009");
		
		assertEquals(agenda.getMesAno() , DateUtil.formataMesAno(agenda.getData()));
	}

	public void testGetMesAno()
	{
		Agenda agenda = AgendaFactory.getEntity(1L);
		agenda.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		assertEquals(DateUtil.formataMesAno(agenda.getData()), agenda.getMesAno());
	}
	
	public void testSetNovaData()
	{
		Agenda agenda = AgendaFactory.getEntity(1L);
		agenda.setData(DateUtil.criarDataMesAno(01, 02, 2000));

		Agenda agendaTmp = AgendaFactory.getEntity(2L);
		agendaTmp.setNovaData(agenda.getData(), 2, 0);// de dois em dois meses
		
		assertEquals("01/04/2000", DateUtil.formataDiaMesAno(agendaTmp.getData()));
	}

	public void testSetNovaDataAno()
	{
		Agenda agenda = AgendaFactory.getEntity(1L);
		agenda.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		
		Agenda agendaTmp = AgendaFactory.getEntity(2L);
		agendaTmp.setNovaData(agenda.getData(), 2, 1);// de dois em dois ano
		
		assertEquals("01/02/2002", DateUtil.formataDiaMesAno(agendaTmp.getData()));
	}
}
