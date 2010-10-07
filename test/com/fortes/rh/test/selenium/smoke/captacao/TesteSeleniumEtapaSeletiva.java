package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumEtapaSeletiva extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEtapaSeletiva()
	{
		 selenium = getSeleniumInstance();
	}

	public void testEtapaSeletiva() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl()+"/index.action");
		selenium.click("link=Etapas Seletivas");
		selenium.waitForPageToLoad("30000");
		assertEquals("Etapas Seletivas", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Etapa Seletiva", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('ordem','nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Etapas Seletivas", selenium.getTitle());
	}
}