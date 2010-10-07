package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumEPC extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEPC()
	{
		selenium = getSeleniumInstance();
	}

	public void testEPC() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/epc/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("EPCs", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir EPC", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('descricao'), null)\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("EPCs", selenium.getTitle());
	}

}