package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastroGrupoOcupacional extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastroGrupoOcupacional()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraGrupoOcupacional() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText10");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Grupos Ocupacionais"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.type("nome", "Teste Desenvolvimento");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Teste Desenvolvimento"));
	}
}

