package com.fortes.rh.test.selenium.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraAreaInteresse extends TestCase {
	Selenium selenium;

	public TesteSeleniumCadastraAreaInteresse()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraAreaInteresse() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText4");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Áreas de Interesse"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Inserir Área de Interesse"));
		selenium.type("nome", "Ruby");
		selenium.click("checkGroupareasCheck1");
		selenium.type("insert_areaInteresse_observacao", "Linguagem de Programação");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','@areasCheck'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Áreas de Interesse"));
	}
}

