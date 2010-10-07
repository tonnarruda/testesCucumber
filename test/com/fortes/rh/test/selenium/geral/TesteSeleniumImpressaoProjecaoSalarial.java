package com.fortes.rh.test.selenium.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumImpressaoProjecaoSalarial extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumImpressaoProjecaoSalarial()
	{
		selenium = getSeleniumInstance();
	}

	public void testProjecaoSalarial() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText406");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Relatório de Projeção Salarial"));
		selenium.click("//button[@onclick='validaCamposProjecaoSalarial();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("data", "06/01/2009");
		selenium.click("//button[@onclick='validaCamposProjecaoSalarial();']");
	}
}