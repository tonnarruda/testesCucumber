package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumFichaMedicaPreenchida extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumFichaMedicaPreenchida()
	{
		selenium = getSeleniumInstance();
	}

	public void testFichaMedicaPreenchida() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/pesquisa/fichaMedica/listPreenchida.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Fichas Médicas", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Fichas Médicas", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Período"));
		selenium.click("//button[@onclick=\"window.location='prepareInsertFicha.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Fichas Médicas", selenium.getTitle());
		selenium.click("//div[@id='prepareResponderQuestionarioPorOutroUsuario_']/ul/a/img");
		selenium.click("//button[@onclick='enviaForm();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='listPreenchida.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Fichas Médicas", selenium.getTitle());
	}

}