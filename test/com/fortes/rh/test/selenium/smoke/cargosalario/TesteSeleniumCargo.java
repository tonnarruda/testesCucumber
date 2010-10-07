package com.fortes.rh.test.selenium.smoke.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCargo extends TestCase {
	private Selenium selenium;

	public TesteSeleniumCargo()
	{
		selenium = getSeleniumInstance();
	}

	public void testCargo() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Cargos e Faixas");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cargos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nomenclatura"));
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cargos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nomenclatura"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Cargo", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','nomeMercado','resp','@areasCheck'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action?page=0'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cargos", selenium.getTitle());
	}

}
