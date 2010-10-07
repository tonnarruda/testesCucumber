package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRelatorioPPP extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumRelatorioPPP()
	{
		selenium = getSeleniumInstance();
	}

	public void testRelatorioPPP() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/ppp/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Lista de Colaboradores", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		assertTrue(selenium.isTextPresent("Ações"));
	}

}