package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumEmpresaBDS extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEmpresaBDS()
	{
		 selenium = getSeleniumInstance();
	}

	public void testEmpresaBDS() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl()+"/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText6");
		selenium.waitForPageToLoad("50000");
		assertTrue(selenium.isTextPresent("Empresas BDS"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("50000");
		assertTrue(selenium.isTextPresent("Inserir Empresa BDS"));
		assertTrue(selenium.isTextPresent("Empresa"));
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("50000");
	}
}