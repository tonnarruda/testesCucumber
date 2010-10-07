package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumEtapaProcessoEleitoral extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEtapaProcessoEleitoral()
	{
		selenium = getSeleniumInstance();
	}

	public void testEtapaProcessoEleitoral() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/etapaProcessoEleitoral/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("CIPA - Etapas do Processo Eleitoral", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Etapa do Processo Eleitoral", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form',new Array('nome','prazo'),null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("CIPA - Etapas do Processo Eleitoral", selenium.getTitle());

	}
}