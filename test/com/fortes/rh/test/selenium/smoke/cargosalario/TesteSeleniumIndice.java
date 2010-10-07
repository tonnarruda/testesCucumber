package com.fortes.rh.test.selenium.smoke.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumIndice extends TestCase {
	private Selenium selenium;

	public TesteSeleniumIndice()
	{
		selenium = getSeleniumInstance();
	}

	public void testCargo() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Índices");
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Novo Índice", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','dataHist','valor'), new Array('dataHist'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}

}
