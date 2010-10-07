package com.fortes.rh.test.model.sesmt;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EtapaProcessoEleitoralFactory;
import com.fortes.rh.util.DateUtil;

public class EtapaProcessoEleitoralTest extends TestCase
{
	private EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity();

	public void testGetPrazoFormatado()
	{
		etapaProcessoEleitoral.setPrazo(-50);
		assertEquals("50 dias antes da posse", etapaProcessoEleitoral.getPrazoFormatado());
		etapaProcessoEleitoral.setPrazo(0);
		assertEquals("No dia da posse", etapaProcessoEleitoral.getPrazoFormatado());
		etapaProcessoEleitoral.setPrazo(3);
		assertEquals("3 dias depois da posse", etapaProcessoEleitoral.getPrazoFormatado());
	}

	public void testGetAntesOuDepois()
	{
		etapaProcessoEleitoral.setPrazo(-10);
		assertEquals("antes", etapaProcessoEleitoral.getAntesOuDepois());
		etapaProcessoEleitoral.setPrazo(0);
		assertEquals("", etapaProcessoEleitoral.getAntesOuDepois());
		etapaProcessoEleitoral.setPrazo(1);
		assertEquals("depois", etapaProcessoEleitoral.getAntesOuDepois());
	}

	@SuppressWarnings("deprecation")
	public void testGetDataFormatada()
	{
		Date posse = DateUtil.criarAnoMesDia(2009, 1, 1);
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicao.setPosse(posse);
		etapaProcessoEleitoral.setEleicao(eleicao);

		Calendar data = Calendar.getInstance();
		data.setTime(posse);
		etapaProcessoEleitoral.setData(data.getTime());

		assertEquals(etapaProcessoEleitoral.getDataFormatadaPrazo(), "01/01/2009 qui (No dia da posse)");

		data.add(Calendar.DAY_OF_YEAR, +11);
		etapaProcessoEleitoral.setData(data.getTime());
		assertEquals(etapaProcessoEleitoral.getDataFormatadaPrazo(), "12/01/2009 seg (11 dias depois da posse)");

		data.add(Calendar.DAY_OF_YEAR, -16);
		etapaProcessoEleitoral.setData(data.getTime());
		assertEquals(etapaProcessoEleitoral.getDataFormatadaPrazo(), "27/12/2008 sáb (5 dias antes da posse)");
	}

	@SuppressWarnings("deprecation")
	public void testSetDataPrazoCalculadaGetDataDiaSemana()
	{
		Date dataPosse = DateUtil.criarAnoMesDia(2009, 1, 11);
		etapaProcessoEleitoral.setPrazo(1);
		// Calculando data a partir do prazo
		etapaProcessoEleitoral.setDataPrazoCalculada(dataPosse, false);

		assertEquals("segunda-feira", etapaProcessoEleitoral.getDataDiaSemana());

		Calendar novaData = Calendar.getInstance();
		novaData.setTime(dataPosse);
		novaData.add(Calendar.DAY_OF_YEAR, +2);
		// Recalculando prazo
		etapaProcessoEleitoral.setData(novaData.getTime());
		etapaProcessoEleitoral.setDataPrazoCalculada(dataPosse, true);

		assertEquals("terça-feira", etapaProcessoEleitoral.getDataDiaSemana());
	}
}
