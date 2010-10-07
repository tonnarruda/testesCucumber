package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumLog extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumLog()
	{
		 selenium = getSeleniumInstance();
	}

	public void testDocumentoVersao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/logging/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Arquivos de Log", selenium.getTitle());
	}
}
