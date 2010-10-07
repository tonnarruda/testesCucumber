package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumFichaMedica extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumFichaMedica()
	{
		selenium = getSeleniumInstance();
	}

	public void testFichaMedica() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/pesquisa/fichaMedica/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Modelos de Fichas Médicas", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Modelo de Ficha Médica", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('titulo','liberado'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Modelos de Fichas Médicas", selenium.getTitle());

	}
}