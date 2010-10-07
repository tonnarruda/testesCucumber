package com.fortes.rh.test.selenium.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraPrioridadeTreinamento extends TestCase {
	private Selenium selenium;

	public TesteSeleniumCadastraPrioridadeTreinamento()
	{
		selenium = getSeleniumInstance();
	}
	public void testCadastraPrioridadeTreinamento() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow365");
		selenium.click("menuItemText13");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Prioridades de Treinamentos"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.type("desc", "Teste");
		selenium.type("sigla", "tst");
		selenium.type("peso", "2");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('desc','sigla','peso'), null);\"]");
		selenium.waitForPageToLoad("30000");

	}
}
