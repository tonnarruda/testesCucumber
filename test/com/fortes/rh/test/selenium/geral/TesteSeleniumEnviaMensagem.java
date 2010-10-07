package com.fortes.rh.test.selenium.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumEnviaMensagem extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEnviaMensagem()
	{
		selenium = getSeleniumInstance();
	}

	public void testMUDE_ESSE_NOME_MILOMBENTO() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow37");
		selenium.click("menuItemText408");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Enviar Mensagem"));
		selenium.click("checkGroupusuariosCheck1");
		selenium.type("mensagem", "teste");
		selenium.click("//button[@onclick='document.form.submit();']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Mensagem enviada com sucesso!"));
	}
}