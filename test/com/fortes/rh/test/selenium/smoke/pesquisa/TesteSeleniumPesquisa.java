package com.fortes.rh.test.selenium.smoke.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumPesquisa extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumPesquisa()
	{
		selenium = getSeleniumInstance();
	}

	public void testResultadoPesquisa() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/pesquisa/pesquisa/list.action");
		assertEquals("Pesquisas", selenium.getTitle());
		selenium.click("//a[contains(text(),'Pesquisas')]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisas", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Pesquisa", selenium.getTitle());
		selenium.click("//button[@onclick='validaPeriodo();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisas", selenium.getTitle());
	}
}