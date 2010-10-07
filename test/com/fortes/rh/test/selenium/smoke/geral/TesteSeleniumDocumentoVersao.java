package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumDocumentoVersao extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumDocumentoVersao()
	{
		 selenium = getSeleniumInstance();
	}

	public void testDocumentoVersao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/geral/documentoVersao/list.action");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Histórico de Versões"));
		assertTrue(selenium.isTextPresent("Data"));
		assertTrue(selenium.isTextPresent("Inclusão do campo"));
	}
}
