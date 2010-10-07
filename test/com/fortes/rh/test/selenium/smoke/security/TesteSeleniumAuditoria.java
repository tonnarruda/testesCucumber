package com.fortes.rh.test.selenium.smoke.security;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumAuditoria extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumAuditoria()
	{
		selenium = getSeleniumInstance();
	}

	public void testAuditoria() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/security/auditoria/prepareList.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Auditoria do Sistema", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}
}