package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumProntuario extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumProntuario()
	{
		selenium = getSeleniumInstance();
	}

	public void testMedicaoRisco() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/prontuario/list.action");

		selenium.waitForPageToLoad("30000");
		assertEquals("Registros de Prontuários", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Registros de Prontuários", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Colaborador"));
	}

}