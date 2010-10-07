package com.fortes.rh.test.selenium.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumDesligaColaborador extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumDesligaColaborador()
	{
		selenium = getSeleniumInstance();
	}

	public void testDesligaColaborador() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow373");
		selenium.click("menuItemText8");
		selenium.waitForPageToLoad("30000");
		selenium.click("//table[@id='colaborador']/tbody/tr[3]/td[1]/a[1]/img");
		selenium.waitForPageToLoad("30000");
		selenium.type("data", "18/11/2008");
		selenium.select("motivoId", "label=Acordo");
		selenium.type("desliga_observacao", "teste");
		selenium.click("//input[@value=' ']");
		assertTrue(selenium.getConfirmation().matches("^Confirma desligamento[\\s\\S]$"));
		assertTrue(selenium.isTextPresent("Colaboradores"));
	}

	public void testResponderEntrevistaDeDesligamento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow373");
		selenium.click("menuItemText8");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Colaboradores"));
		selenium.click("//img[@title='Entrevista de desligamento']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Entrevista de Desligamento"));
		selenium.select("entrevista", "label=Entrevista de Desligamento 2");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('entrevista'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Entrevista - Entrevista de Desligamento 2"));
		selenium.type("RS158", "teste1");
		selenium.type("RS159", "teste2");
		selenium.click("//button[@onclick='return submete(document.forms[1],false);']");
		assertTrue(selenium.isTextPresent("Colaboradores"));
	}
}