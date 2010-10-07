package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumInvestimentos extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumInvestimentos()
	{
		 selenium = getSeleniumInstance();
	}

	public void testInvestimento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Investimentos");
		selenium.waitForPageToLoad("30000");
		assertEquals("Investimentos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Investimentos da Empresa", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','grupo'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Investimentos", selenium.getTitle());
	}
}
