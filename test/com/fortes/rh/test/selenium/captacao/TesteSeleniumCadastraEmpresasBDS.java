package com.fortes.rh.test.selenium.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraEmpresasBDS extends TestCase {
	Selenium selenium;

	public TesteSeleniumCadastraEmpresasBDS()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraEmpresaBDS() throws Exception {
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText6");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Empresas BDS"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','email','ddd','fone'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("nome", "Teste");
		selenium.type("email", "teste@teste.com");
		selenium.type("ddd", "85");
		selenium.type("fone", "99999999");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','email','ddd','fone'), null);\"]");
		selenium.waitForPageToLoad("30000");
	}
}
