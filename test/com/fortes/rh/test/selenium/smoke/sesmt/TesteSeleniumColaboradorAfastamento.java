package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumColaboradorAfastamento extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumColaboradorAfastamento()
	{
		selenium = getSeleniumInstance();
	}

	public void testColaboradorAfastamento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/colaboradorAfastamento/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Afastamentos", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Afastamentos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Período"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Afastamento", selenium.getTitle());
		selenium.click("//button[@onclick=\"validaFormulario('formFiltro', null, null);\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Afastamento", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nenhum colaborador para o filtro informado."));
		selenium.click("//button[@onclick=\"document.formFiltro.action='list.action';document.formFiltro.submit();\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Afastamentos", selenium.getTitle());
	}

}