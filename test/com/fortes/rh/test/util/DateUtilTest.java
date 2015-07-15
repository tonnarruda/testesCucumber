package com.fortes.rh.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;

import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.MesComparator;
import com.ibm.icu.util.Calendar;

@SuppressWarnings("deprecation")
public class DateUtilTest extends TestCase
{
	@SuppressWarnings("unused")
	protected void setUp(){
		DateUtil dateUtil = new DateUtil();
	}

	public void testFormataMesAno(){
		assertEquals("01/2007", DateUtil.formataMesAno(new Date(107,0,01)));
	}

	public void testFormataDiaMesAno(){
		assertEquals("01/01/2007", DateUtil.formataDiaMesAno(new Date(107,0,01)));
	}

	public void testFormataDiaMesAnoTime()
	{
		assertEquals("01/01/2007 00:00:00", DateUtil.formataDiaMesAnoTime(new Date(107,0,01)));
	}

	public void testFormataDate(){
		assertEquals("01/01/2007", DateUtil.formataDate(new Date(107,0,1), "dd/MM/yyyy"));
	}

	public void testCriaDataMesAno(){

		Date data = DateUtil.criarDataMesAno("01/2007");
		assertEquals(1, data.getDate());
		assertEquals(0, data.getMonth());
		assertEquals(107, data.getYear());
	}
	

	public void testCriaDataDiaMesAno(){

		Date data = DateUtil.criarDataDiaMesAno("20/11/2014");
		assertEquals(20, data.getDate());
		assertEquals(10, data.getMonth());
		assertEquals(114, data.getYear());
	}
	
	public void testGetDia()
	{
		Date data = DateUtil.criarDataMesAno(02, 12, 2011);
		assertEquals(2, DateUtil.getDia(data));
	}
	
	public void testGetMes()
	{
		Date data = DateUtil.criarDataMesAno(02, 11, 2011);
		assertEquals(11, DateUtil.getMes(data));
	}

	public void testStringToGregorian() throws Exception{
		GregorianCalendar gc = DateUtil.stringToGregorian("01/2007");

		assertEquals("Test 1", 1, gc.get(GregorianCalendar.DAY_OF_MONTH));
		assertEquals("Test 2", 0, gc.get(GregorianCalendar.MONTH));
		assertEquals("Test 3", 2007, gc.get(GregorianCalendar.YEAR));

		Exception exp = null;
		try{
			gc = DateUtil.stringToGregorian("1/2007");
		}catch (Exception e) {
			exp = e;
		}

		assertNotNull("Test 4", exp);

	}

	public void testEquals() throws Exception{
		Date date1 = new Date(107,0,10);
		Date date2 = new Date(107,0,10);
		Date date3 = new Date(107,0,11);

		assertTrue(DateUtil.equals(date1, date2));
		assertFalse(DateUtil.equals(date1, date3));

	}
	
	public void testGetNomeMes() throws Exception{
		assertEquals("Mar", DateUtil.getNomeMes(2));
	}

	public void testRetornaDataMesAnterior()
	{
		Date dateJan = new Date(107,0,10);
		Date dateDez = new Date(107,11,10);
		Date dateMar = new Date(107,2,31);

		Date resultJan = new Date(106,11,10);
		Date resultDez = new Date(107,10,10);
		Date resultMar = new Date(107,1,28);

		assertEquals(DateUtil.retornaDataAnteriorQtdMeses(dateJan, 1, true).compareTo(resultJan), 0);
		assertEquals(DateUtil.retornaDataAnteriorQtdMeses(dateDez, 1, true).compareTo(resultDez), 0);
		assertEquals(DateUtil.retornaDataAnteriorQtdMeses(dateMar, 1, true).compareTo(resultMar), 0);

		Date dataHoje = DateUtil.criarDataMesAno(03, 02, 2010);
		Date data20Meses = DateUtil.criarDataMesAno(01, 06, 2008);
		assertEquals(DateUtil.retornaDataAnteriorQtdMeses(dataHoje, 20, false).compareTo(data20Meses), 0);
	}

	public void testDiferencaEntreDatas()
	{
		Date data1 = new Date(2008, 0, 1);
		Date data2 = new Date(2008, 0, 10);

		assertEquals("Desconsiderando data inicial", 9, DateUtil.diferencaEntreDatas(data1, data2, false));
		assertEquals("Considerando data inicial", 10, DateUtil.diferencaEntreDatas(data1, data2, true));
	}

	public void testCriarDataMesAno()
	{
		Date data1 = new Date(2008-1900, 0, 1);
		Date data2 = new Date(2008-1900, 11, 31);

		assertEquals(data1, DateUtil.criarDataMesAno(1, 1, 2008));
		assertEquals(data2, DateUtil.criarDataMesAno(31, 12, 2008));
	}

	public void testCriarAnoMesDia()
	{
		Date data1 = new Date(2008-1900, 0, 1);
		Date data2 = new Date(2008-1900, 11, 31);

		assertEquals(data1, DateUtil.criarAnoMesDia(2008, 1, 1));
		assertEquals(data2, DateUtil.criarAnoMesDia(2008, 12, 31));
	}

	public void testCalcularIdade()
	{
		assertEquals("Test 1", 29, DateUtil.calcularIdade(new Date(78,11,10), new Date(108,03,15)));
		assertEquals("Test 2", 30, DateUtil.calcularIdade(new Date(78,03,15), new Date(108,03,15)));
		assertEquals("Test 3", 29, DateUtil.calcularIdade(new Date(78,03,16), new Date(108,03,15)));
		assertEquals("Test 4", 0, DateUtil.calcularIdade(new Date(108,01,15), new Date(108,03,15)));
		assertEquals("Test 5", 0, DateUtil.calcularIdade(null, new Date(108,03,15)));
	}
	
	public void testGetAno()
	{
		assertEquals("Test 1", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)), DateUtil.getAno());
	}	
	
	public void testGetIntervalDateString()
	{
		assertEquals("Test 1", "(29 anos e 4 meses)", DateUtil.getIntervalDateString(new Date(78,11,10), new Date(108,03,15)));
		assertEquals("Test 2", "(1 ano e 11 meses)", DateUtil.getIntervalDateString(new Date(106,04,02), new Date(108,03,15)));
		assertEquals("Test 3", "(1 ano e 1 mês)", DateUtil.getIntervalDateString(new Date(106,04,02), new Date(107,05,02)));
		assertEquals("Test 4", "(2 meses)", DateUtil.getIntervalDateString(new Date(108,01,02), new Date(108,03,15)));

		assertEquals("Test 5", "(Data inicial maior que data final!)", DateUtil.getIntervalDateString(new Date(108,03,15), new Date(108,03,14)));
		assertNotNull("Test 6", DateUtil.getIntervalDateString(new Date(108,03,15), null));
	}

	public void testRetornaDataDiaAnterior()
	{
		assertEquals("Test 1", new Date(108,11,9), DateUtil.retornaDataDiaAnterior(new Date(108,11,10)));
		assertEquals("Test 2", new Date(108,10,30), DateUtil.retornaDataDiaAnterior(new Date(108,11,1)));
	}

	public void testGetDiaSemanaDescritivo()
	{
		assertEquals("Test 1", "quarta-feira", DateUtil.getDiaSemanaDescritivo(new Date(108,3,16)));
	}

	public void testMontaDataByString() throws Exception
	{
		Date data = DateUtil.montaDataByString("30/11/2008");

		assertEquals("Test 1", 30, data.getDate());
		assertEquals("Test 2", 10, data.getMonth());
		assertEquals("Test 3", 108, data.getYear());
	}
	
	public void testMontaDataByStringJson() throws Exception
	{
		Date data = DateUtil.montaDataByStringJson("2008-04-09T11:52:34Z");
		
		assertEquals("Test 1", 9, data.getDate());
		assertEquals("Test 2", 03, data.getMonth());
		assertEquals("Test 3", 108, data.getYear());
	}
	
	public void testIncrementaDias() throws Exception
	{
		Date data = DateUtil.montaDataByString("05/02/2009");
		assertEquals("Test dia", "10/02/2009", DateUtil.formataDiaMesAno(DateUtil.incrementaDias(data, 5)));
		
		data = DateUtil.montaDataByString("30/09/2010");
		assertEquals("Test mes", "05/10/2010", DateUtil.formataDiaMesAno(DateUtil.incrementaDias(data, 5)));

		data = DateUtil.montaDataByString("30/12/2010");
		assertEquals("Test ano", "04/01/2011", DateUtil.formataDiaMesAno(DateUtil.incrementaDias(data, 5)));
		
		data = DateUtil.montaDataByString("01/12/2010");
		assertEquals("Test dia anterior", "30/11/2010", DateUtil.formataDiaMesAno(DateUtil.incrementaDias(data, -1)));
	}
	
	public void testGetUltimoDiaMesAnterior() throws Exception
	{
		Date data = DateUtil.montaDataByString("01/12/2010");
		assertEquals("Test dia anterior", "30/11/2010", DateUtil.formataDiaMesAno(DateUtil.getUltimoDiaMesAnterior(data)));
	}
	
	public void testIncrementaMes() throws Exception
	{
		Date data = DateUtil.montaDataByString("31/12/2009");
		assertEquals("Test 1", "28/02/2010", DateUtil.formataDiaMesAno(DateUtil.incrementaMes(data, 2)));

		data = DateUtil.montaDataByString("05/02/2009");
		assertEquals("Test 2", "05/07/2009", DateUtil.formataDiaMesAno(DateUtil.incrementaMes(data, 5)));
	}
	
	public void testIncrementaAno() throws Exception
	{
		Date data = DateUtil.montaDataByString("29/01/2007");
		assertEquals("Test 1", "29/01/2009", DateUtil.formataDiaMesAno(DateUtil.incrementaAno(data, 2)));
		
		data = DateUtil.montaDataByString("29/02/2004");
		assertEquals("Test 2", "28/02/2005", DateUtil.formataDiaMesAno(DateUtil.incrementaAno(data, 1)));

		data = DateUtil.montaDataByString("28/02/2003");
		assertEquals("Test 2", "28/02/2004", DateUtil.formataDiaMesAno(DateUtil.incrementaAno(data, 1)));
	}
	
	public void testIncrementaMesOuAno() throws Exception
	{
		Date data = DateUtil.montaDataByString("31/12/2009");
		assertEquals("Test 1", "28/02/2010", DateUtil.formataDiaMesAno(DateUtil.incrementa(data, 2, 0)));
		
		data = DateUtil.montaDataByString("29/02/2004");
		assertEquals("Test 2", "28/02/2005", DateUtil.formataDiaMesAno(DateUtil.incrementa(data, 1, 1)));

		data = DateUtil.montaDataByString("30/04/2004");
		assertEquals("Test 2", "10/05/2004", DateUtil.formataDiaMesAno(DateUtil.incrementa(data, 10, 2)));
	}

	public void testMontaDataByStringEmBranco() throws Exception
	{
		Date data = DateUtil.montaDataByString("  /  /    ");
		assertNull(data);
	}

	public void testMontaDataByStringException() throws Exception
	{
		Exception excep = null;
		try
		{
			DateUtil.montaDataByString(" 2/ 2/   2");
			fail("Deveria ter lançado uma exceção quando data inválida.");
		}
		catch (Exception e)
		{
			excep = e;
		}

	}

	public void testSetaUltimaHora(){

		Date data = new Date(108,10,30);
		data = DateUtil.setaUltimaHoraDoDia(data);

		assertEquals("Test 1", 30, data.getDate());
		assertEquals("Test 2", 10, data.getMonth());
		assertEquals("Test 3", 108, data.getYear());
		assertEquals("Test 4", 23, data.getHours());
		assertEquals("Test 5", 59, data.getMinutes());
		assertEquals("Test 6", 59, data.getSeconds());
	}

	public void testSetaMesPosterior(){

		Date data = new Date(108,10,2);

		assertEquals("Test 1", data.getDate(), DateUtil.setaMesPosterior(data).getDate());
		assertEquals("Test 2", data.getMonth()+1, DateUtil.setaMesPosterior(data).getMonth());
		assertEquals("Test 3", data.getYear(), DateUtil.setaMesPosterior(data).getYear());
		assertEquals("Test 4", data.getHours(), DateUtil.setaMesPosterior(data).getHours());
		assertEquals("Test 5", data.getMinutes(), DateUtil.setaMesPosterior(data).getMinutes());
		assertEquals("Test 6", data.getSeconds(), DateUtil.setaMesPosterior(data).getSeconds());

		data = new Date(108,0,30);

		assertEquals("Test 7", 29, DateUtil.setaMesPosterior(data).getDate());
		assertEquals("Test 8", data.getMonth()+1, DateUtil.setaMesPosterior(data).getMonth());
		assertEquals("Test 9", data.getYear(), DateUtil.setaMesPosterior(data).getYear());
		assertEquals("Test 10", data.getHours(), DateUtil.setaMesPosterior(data).getHours());
		assertEquals("Test 11", data.getMinutes(), DateUtil.setaMesPosterior(data).getMinutes());
		assertEquals("Test 12", data.getSeconds(), DateUtil.setaMesPosterior(data).getSeconds());

	}

	public void testGetUltimoDiaMes(){

		assertEquals("Test 1", new Date(108, 10, 30), DateUtil.getUltimoDiaMes(new Date(108, 10, 1)));

	}

	public void testGetInicioMesData(){

		Date data = new Date(108,10,15);
		Date dataRetorno = DateUtil.getInicioMesData(data);

		assertEquals("Test 1", 1, dataRetorno.getDate());
		assertEquals("Test 2", data.getMonth(), dataRetorno.getMonth());
		assertEquals("Test 3", data.getYear(), dataRetorno.getYear());
		assertEquals("Test 4", data.getHours(), dataRetorno.getHours());
		assertEquals("Test 5", data.getMinutes(), dataRetorno.getMinutes());
		assertEquals("Test 6", data.getSeconds(), dataRetorno.getSeconds());
	}

	public void testAnosEntreDatas()
	{
		Date dataIni = DateUtil.criarDataMesAno(02, 01, 1998);
		Date dataFim = DateUtil.criarDataMesAno(02, 10, 2001);

		assertEquals(3, DateUtil.anosEntreDatas(dataIni,dataFim));

	}

	public void testMesesEntreDatas()
	{
		Date dataIni = DateUtil.criarDataMesAno(02, 01, 2001);
		Date dataFim = DateUtil.criarDataMesAno(02, 10, 2001);

		assertEquals(9, DateUtil.mesesEntreDatas(dataIni,dataFim));
		
		dataFim = DateUtil.criarDataMesAno(02, 01, 2002);

		assertEquals(12, DateUtil.mesesEntreDatas(dataIni,dataFim));
	}

	public void testFormataDataExtensoPadraoDeAta()
	{
		Date data = DateUtil.criarDataMesAno(10, 10, 2010);

		String esperado = "Aos dez dias do mês de Outubro do ano de dois mil e dez, às 14:15";
		String resultado = DateUtil.formataDataExtensoPadraoDeAta(data, "14:15");
		assertEquals(esperado, resultado);

		Date data2 = DateUtil.criarDataMesAno(1,1, 2005);
		String esperado2 = "No primeiro dia do mês de Janeiro do ano de dois mil e cinco, às 8:00";
		String resultado2 = DateUtil.formataDataExtensoPadraoDeAta(data2, "8:00");

		assertEquals(esperado2, resultado2);
	}

	public void testFormataMesExtensoAno()
	{
		Date data = DateUtil.criarDataMesAno(10, 10, 2010);
		
		assertEquals("OUT/2010", DateUtil.formataMesExtensoAno(data));
	}
	
	@SuppressWarnings("unchecked")
	public void testComparatorData()
	{
		Collection<String> datas = new ArrayList<String>();
		datas.add("DEZ/2009");
		datas.add("JAN/2009");
		datas.add("MAR/2009");
		datas.add("MAR/2008");
		
		Comparator comp = new MesComparator();
		Collections.sort((List) datas, comp);
		
		assertEquals("MAR/2008", datas.toArray()[0]);
		assertEquals("JAN/2009", datas.toArray()[1]);
	}
	
	public void testEqualsMesAno() throws Exception{
		Date date1 = DateUtil.criarAnoMesDia(2009, 07, 30);
		Date date2 = DateUtil.criarDataMesAno("07/2009");
		Date date3 = DateUtil.criarDataMesAno(19, 07, 2009);
		
		assertFalse(DateUtil.equals(date1, date2));
		assertTrue(DateUtil.equalsMesAno(date1, date2));
		assertTrue(DateUtil.equalsMesAno(date1, date3));
	}
	
	public void testBetween()
	{
		Date data1 = DateUtil.criarDataMesAno(28, 3, 2009);
		Date dataInicio = DateUtil.criarDataMesAno(28, 3, 2009);
		Date dataFim = DateUtil.criarDataMesAno(1, 12, 2009);
		
		assertTrue(DateUtil.between(data1, dataInicio, dataFim));
		
		dataInicio = DateUtil.criarDataMesAno(1, 5, 2005); 
		assertTrue(DateUtil.between(data1, dataInicio, dataFim));
		
		data1 = DateUtil.criarDataMesAno(1, 04, 2005);
		assertFalse(DateUtil.between(data1, dataInicio, dataFim));
	}

	public void testMontaDataByStringComHora()
	{
		Date data = DateUtil.montaDataByStringComHora("17/07/2013", "12:00");
		Date dataInicio = DateUtil.montaDataByStringComHora("17/07/2013", "08:30");
		Date dataFim = DateUtil.montaDataByStringComHora("17/07/2013", "13:01");
		
		assertTrue(DateUtil.between(data, dataInicio, dataFim));
	}

	public void testMontaDatatComHora()
	{
		Date data1 = DateUtil.montaDataComHora("26/02/2015 12:00", "14:30");
		Date data2 = DateUtil.montaDataComHora("27/02/2015", "15:32");
		Date data3 = DateUtil.montaDataComHora("01/03/2015", null);
		
		assertEquals(DateUtil.montaDataByStringComHora("26/02/2015", "12:00"), data1);
		assertEquals(DateUtil.montaDataByStringComHora("27/02/2015", "15:32"), data2);
		assertEquals(DateUtil.montaDataByStringComHora("01/03/2015", "00:00"), data3);
		
		assertNull(DateUtil.montaDataComHora(null, "15:32"));
		assertNull(DateUtil.montaDataComHora(null, null));
		
		try {
			DateUtil.montaDataComHora("ab/12/20000", "15:32");
			assertTrue("Digitação incorreta não verificada", false);
		} catch (Exception e) {
			assertTrue("Digitação incorreta verificada corretamente", true);
		}
	}
	
	public void testContaDiasUteis()
	{
		assertEquals(20, DateUtil.contaDiasUteisMes(DateUtil.montaDataByString("28/02/2011"), false, false));
		assertEquals(21, DateUtil.contaDiasUteisMes(DateUtil.montaDataByString("26/04/2011"), false, false));
		assertEquals(22, DateUtil.contaDiasUteisMes(DateUtil.montaDataByString("26/05/2011"), false, false));
		assertEquals(22, DateUtil.contaDiasUteisMes(DateUtil.montaDataByString("26/06/2011"), false, false));
	}
}