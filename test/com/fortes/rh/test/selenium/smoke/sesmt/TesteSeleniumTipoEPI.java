package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumTipoEPI extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumTipoEPI()
	{
		selenium = getSeleniumInstance();
	}

	public void testTipoEPI() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/tipoEPI/list.action");
		 selenium.waitForPageToLoad("30000");
		 assertEquals("Tipos de EPI", selenium.getTitle());
		 selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		 selenium.waitForPageToLoad("30000");
		 assertEquals("Inserir Tipo de EPI", selenium.getTitle());
		 selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		 assertEquals("Preencha os campos indicados.", selenium.getAlert());
		 selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		 selenium.waitForPageToLoad("30000");
		 assertEquals("Tipos de EPI", selenium.getTitle());

	}
}