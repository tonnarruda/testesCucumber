package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRelatorioPPRALTCAT extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumRelatorioPPRALTCAT()
	{
		selenium = getSeleniumInstance();
	}

	public void testRelatorioPPRALTCAT() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/ppra/prepareRelatorio.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("PPRA e LTCAT", selenium.getTitle());
		selenium.click("//button[@onclick='return valida();']");
		assertEquals("Selecione o relatório a ser gerado.", selenium.getAlert());
		selenium.click("ppra");
		selenium.click("//button[@onclick='return valida();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}

}