package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumSolicitacaoEpi extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumSolicitacaoEpi()
	{
		selenium = getSeleniumInstance();
	}

	public void testSolicitacaoEpi() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/solicitacaoEpi/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitações de EPI", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitações de EPI", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Situação"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Nova Solicitação de EPIs", selenium.getTitle());
		selenium.click("//button[@onclick=\"validaFormulario('formFiltro', null, null);\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Nova Solicitação de EPIs", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nenhum colaborador para o filtro informado."));
		selenium.click("//button[@onclick=\"document.forms[0].action='list.action';document.forms[0].submit();\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitações de EPI", selenium.getTitle());
	}

}