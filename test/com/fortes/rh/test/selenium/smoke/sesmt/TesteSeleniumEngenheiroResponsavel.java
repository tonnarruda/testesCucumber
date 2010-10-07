package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumEngenheiroResponsavel extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEngenheiroResponsavel()
	{
		selenium = getSeleniumInstance();
	}

	public void testEngenheiroResponsavel() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/engenheiroResponsavel/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Engenheiros Responsáveis do Trabalho", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Engenheiro do Trabalho", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','inicio','crea'), new Array('inicio','fim'))\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Engenheiros Responsáveis do Trabalho", selenium.getTitle());

	}
}