package com.fortes.rh.test.selenium.smoke.indicador;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumGastoEmpresaPrepareImprimir extends TestCase
{
	private Selenium selenium;

	public  TesteSeleniumGastoEmpresaPrepareImprimir()
	{
		selenium = getSeleniumInstance();
	}

	public void testDuracaoPreenchimentoVaga() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl()+"/geral/gastoEmpresa/prepareImprimir.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório de Investimentos da Empresa", selenium.getTitle());
		selenium.click("//button[@onclick=\"if(compararData('dataIni','dataFim')) imprimir()\"]");
		assertEquals("Datas inválidas, data Inicial deve ser menor que Final.", selenium.getAlert());
		selenium.open(getAppUrl() + "/geral/ocorrencia/list.action");
	}
}