package com.fortes.rh.test.selenium.pesquisa;

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
		
		selenium.open(getAppUrl() + "/index.action");
		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow353");
		selenium.click("menuItemText28");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Pesquisas"));
		selenium.click("//img[@title='Colaboradores']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Colaboradores da Pesquisa"));
		selenium.click("//img[@title='Responder a pesquisa por este colaborador']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Pesquisa - Perfil de Liderança"));
		assertTrue(selenium.isTextPresent("Colaborador: Robertson Bob Freitas"));
	}
}