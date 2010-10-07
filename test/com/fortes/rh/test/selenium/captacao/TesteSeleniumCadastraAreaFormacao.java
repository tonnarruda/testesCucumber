package com.fortes.rh.test.selenium.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraAreaFormacao extends TestCase {
	Selenium selenium;

	public TesteSeleniumCadastraAreaFormacao()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraAreaFormacao() throws Exception {
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText397");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Áreas de Formação"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.type("nome", "Teste");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		selenium.waitForPageToLoad("30000");
	}
}
