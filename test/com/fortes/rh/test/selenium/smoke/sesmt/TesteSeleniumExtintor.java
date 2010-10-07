package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumExtintor extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumExtintor()
	{
		selenium = getSeleniumInstance();
	}

	public void testExtintor() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/extintor/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Extintores", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Extintores", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Número do cilindro"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Extintor", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('estabelecimento','descricao','tipo'), null)\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Extintores", selenium.getTitle());

	}
}