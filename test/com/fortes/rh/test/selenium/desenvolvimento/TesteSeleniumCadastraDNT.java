package com.fortes.rh.test.selenium.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraDNT extends TestCase {
	private Selenium selenium;

	public TesteSeleniumCadastraDNT()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraDNT() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");

		selenium.click("DHTMLSuite_menuBar_arrow365");
		selenium.click("menuItemText64");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("DNT - Diagnóstico de Necessidade de Treinamento"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.type("nome", "Teste");
		selenium.type("data", "01/10/2008");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','data'), new Array('data'));\"]");
		selenium.waitForPageToLoad("30000");
	}
}
