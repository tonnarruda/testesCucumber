package com.fortes.rh.test.selenium.smoke.indicador;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumHistoricoColaboradorPrepareRelatorioPromocoes extends TestCase
{
	private Selenium selenium;

	public  TesteSeleniumHistoricoColaboradorPrepareRelatorioPromocoes()
	{
		selenium = getSeleniumInstance();
	}

	public void testDuracaoPreenchimentoVaga() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl()+"/cargosalario/historicoColaborador/prepareRelatorioPromocoes.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório de Promoções", selenium.getTitle());
		selenium.click("//button[@onclick='imprimir();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}
}