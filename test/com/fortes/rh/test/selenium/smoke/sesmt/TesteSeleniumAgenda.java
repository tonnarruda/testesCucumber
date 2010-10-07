package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumAgenda extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumAgenda()
	{
		selenium = getSeleniumInstance();
	}

	public void testAgenda() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/agenda/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Agenda", selenium.getTitle());
		selenium.click("//input[@value='']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Agenda", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Estabelecimento"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action?manterFiltro=true&dataMesAnoIni=01/2010&dataMesAnoFim=12/2010'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Agenda", selenium.getTitle());
		selenium.click("//button[@onclick='return validarPeriodos();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action?manterFiltro=true&dataMesAnoIni=01/2010&dataMesAnoFim=12/2010'\"]");
		selenium.waitForPageToLoad("30000");

	}
}