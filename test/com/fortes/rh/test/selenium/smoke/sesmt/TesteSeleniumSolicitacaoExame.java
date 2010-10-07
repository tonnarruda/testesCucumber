package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumSolicitacaoExame extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumSolicitacaoExame()
	{
		selenium = getSeleniumInstance();
	}

	public void testSolicitacaoExame() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/solicitacaoExame/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitações/Atendimentos Médicos", selenium.getTitle());
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitações/Atendimentos Médicos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Período"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Acesso Negado", selenium.getTitle());
		assertEquals("Solicitações/Atendimentos Médicos", selenium.getTitle());
	}

}