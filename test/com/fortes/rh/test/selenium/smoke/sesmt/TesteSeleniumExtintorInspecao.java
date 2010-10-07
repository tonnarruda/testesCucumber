package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumExtintorInspecao extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumExtintorInspecao()
	{
		selenium = getSeleniumInstance();
	}

	public void testExtintorInspecao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/extintorInspecao/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Extintores - Inspeção", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Extintores - Inspeção", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Estabelecimento"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Inspeção", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('estabelecimento','extintor','data'), new Array('data'))\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Extintores - Inspeção", selenium.getTitle());
	}

}