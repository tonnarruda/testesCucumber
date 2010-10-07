package com.fortes.rh.test.selenium;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteModeloSelenium extends TestCase
{
	private Selenium selenium;

	public TesteModeloSelenium()
	{
		selenium = getSeleniumInstance();
	}

	public void testMUDE_ESSE_NOME_MILOMBENTO() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/captacao/etapaSeletiva/list.action");
		assertTrue(selenium.isTextPresent("Etapas Seletivas"));
	}
}