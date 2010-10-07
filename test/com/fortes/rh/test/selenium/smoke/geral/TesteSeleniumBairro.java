package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumBairro extends TestCase {
	private Selenium selenium;

	public TesteSeleniumBairro()
	{
		selenium = getSeleniumInstance();
	}

	public void testBairro() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/bairro/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Bairros", selenium.getTitle());
		selenium.click("//button[@onclick=\"document.getElementById('pagina').value = 1; document.formPage.submit();\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Bairros", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Estado"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Novo Bairro", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('cidade','nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Bairros", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareMigrar.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Unir Bairros", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('bairro','bairroDestino'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Bairros", selenium.getTitle());
	}
}
