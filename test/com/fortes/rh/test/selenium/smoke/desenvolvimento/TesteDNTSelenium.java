package com.fortes.rh.test.selenium.smoke.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteDNTSelenium extends TestCase
{
	private Selenium selenium;

	public TesteDNTSelenium()
	{
		selenium = getSeleniumInstance();
	}

	public void testDNT() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=DNT");
		selenium.waitForPageToLoad("30000");
		assertEquals("DNT - Diagnóstico de Necessidade de Treinamento", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir DNT", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','data'), new Array('data'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("DNT - Diagnóstico de Necessidade de Treinamento", selenium.getTitle());
	}
}