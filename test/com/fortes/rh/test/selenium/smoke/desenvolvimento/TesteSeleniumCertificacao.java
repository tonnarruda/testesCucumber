package com.fortes.rh.test.selenium.smoke.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCertificacao extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCertificacao()
	{
		selenium = getSeleniumInstance();
	}

	public void testImpressaoCertificado() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Certificações");
		selenium.waitForPageToLoad("30000");
		assertEquals("Certificações", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Nova Certificação", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Certificações", selenium.getTitle());
	}
}