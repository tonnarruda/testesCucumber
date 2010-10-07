package com.fortes.rh.test.selenium.smoke.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRespondePesquisaPorAlguem extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumRespondePesquisaPorAlguem()
	{
		selenium = getSeleniumInstance();
	}

	public void testRespoderPesquisaPorAlguem() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/fortesrh/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow353");
		selenium.click("DHTMLSuite_menuItem28");
		assertTrue(selenium.isTextPresent("Pesquisas"));
		selenium.waitForPageToLoad("3000");
		selenium.click("//table[@id='pesquisa']/tbody/tr[2]/td[1]/a[2]/img");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Colaboradores da Pesquisa"));
		selenium.click("//img[@title='Responder']");
		selenium.waitForPageToLoad("30000");
		selenium.click("//button[@onclick=\"window.location='../colaboradorQuestionario/list.action?questionario.id=105'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Colaboradores da Pesquisa"));
	}
}