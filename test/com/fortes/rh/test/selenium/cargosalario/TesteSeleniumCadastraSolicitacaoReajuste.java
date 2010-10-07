package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraSolicitacaoReajuste extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraSolicitacaoReajuste()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraSolicitacaoReajuste() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/fortesrh/index.action");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText49");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Solicitação de Realinhamento de Cargos & Salários"));
		selenium.select("tabelaReajuste", "label=Tabela 2009");
		selenium.select("areaOrganizacional", "label=Desenvolvimento > Desenvolvimento Java");
		selenium.click("//option[@value='2']");
		selenium.waitForCondition("selenium.isTextPresent('Maria do Selenium')", "5000");
		selenium.select("colaborador", "label=Maria do Selenium");
		selenium.click("//option[@value='532']");
		selenium.waitForCondition("selenium.isTextPresent('Desenvolvedor III')", "10000");
		selenium.select("faixa", "label=Desenvolvedor III");
		selenium.select("tipoSalario", "label=Valor");
		selenium.type("salarioProposto", "3.000,00");
		selenium.click("//button[@onclick='enviaForm(2);']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Solicitação de Realinhamento incluída com sucesso"));
	}
}