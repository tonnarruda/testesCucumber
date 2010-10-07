package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraReajusteColetivo extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraReajusteColetivo()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraReajusteColetivo() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText395");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Reajuste Coletivo/Dissídio"));
		selenium.select("tabelaReajuste", "label=Promoção1");
		selenium.click("checkGroupareasCheck1");
		selenium.click("checkGroupestabelecimentosCheck1");
		selenium.type("valorDissidio", "10");
		selenium.click("//button[@onclick='aplicar();']");
		selenium.waitForPageToLoad("30000");
	}
}
