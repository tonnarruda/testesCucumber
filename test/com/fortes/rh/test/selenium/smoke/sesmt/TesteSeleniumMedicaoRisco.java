package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumMedicaoRisco extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumMedicaoRisco()
	{
		selenium = getSeleniumInstance();
	}

	public void testMedicaoRisco() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/medicaoRisco/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Medição dos Riscos", selenium.getTitle());
		selenium.click("//input[@value='']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Medição dos Riscos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Ambiente"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Medição de Riscos", selenium.getTitle());
		selenium.click("//button[@onclick='return validarCampos();;']");
		assertEquals("Clique em Carregar Riscos.", selenium.getAlert());
		selenium.click("//button[@onclick='return populaRiscos();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Medição dos Riscos", selenium.getTitle());

	}

}