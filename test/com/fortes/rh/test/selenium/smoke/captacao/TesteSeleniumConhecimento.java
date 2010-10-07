package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumConhecimento extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumConhecimento()
	{
		selenium = getSeleniumInstance();
	}

	public void testConhecimento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Conhecimentos");
		selenium.waitForPageToLoad("30000");
		assertEquals("Conhecimentos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Conhecimento", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','@areasCheck'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Conhecimentos", selenium.getTitle());
	}
}