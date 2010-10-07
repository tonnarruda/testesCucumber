package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRelatorioFichaEPI extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumRelatorioFichaEPI()
	{
		selenium = getSeleniumInstance();
	}

	public void testRelatorioFichaEPI() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/epi/prepareImprimirFicha.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Ficha de EPI", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Ficha de EPI", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nenhum colaborador para o filtro informado."));
		selenium.click("//button[@onclick=\"return mudaAction('imprimir');\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}

}