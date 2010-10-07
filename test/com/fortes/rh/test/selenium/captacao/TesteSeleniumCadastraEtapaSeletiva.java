package com.fortes.rh.test.selenium.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraEtapaSeletiva extends TestCase {
	public Selenium selenium;

	public TesteSeleniumCadastraEtapaSeletiva()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraEtapaSeletiva() throws Exception {
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText3");
		selenium.waitForPageToLoad("50000");
		assertTrue(selenium.isTextPresent("Etapas Seletivas"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("50000");
		selenium.type("nome", "teste");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('ordem','nome'), null);\"]");
		selenium.waitForPageToLoad("50000");
		assertTrue(selenium.isTextPresent("teste"));
	}
}

