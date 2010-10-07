package com.fortes.rh.test.selenium.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumClonarPesquisa extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumClonarPesquisa()
	{
		selenium = getSeleniumInstance();
	}

	public void testClonarPesquisa() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("50000");
		selenium.click("DHTMLSuite_menuBar_arrow353");
		selenium.click("DHTMLSuite_menuItem28");
		selenium.waitForPageToLoad("50000");
		selenium.isTextPresent("Pesquisas");
		selenium.click("//img[@title='Clonar']");
		selenium.waitForPageToLoad("50000");
		selenium.isTextPresent("teste (CLONE)");
		selenium.click("//table[@id='pesquisa']/tbody/tr[2]/td[1]/a[4]/img");
		selenium.waitForPageToLoad("3000");
		selenium.isTextPresent("Pesquisa excluída com sucesso.");
	}
}
