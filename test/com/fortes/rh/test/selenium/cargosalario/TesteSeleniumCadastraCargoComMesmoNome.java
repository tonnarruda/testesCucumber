package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraCargoComMesmoNome extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraCargoComMesmoNome()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraCargoComMesmoNome() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/fortesrh/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("DHTMLSuite_menuItem11");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Cargos"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Inserir Cargo"));
		selenium.type("nome", "Desenvolvedor");
		selenium.type("nomeMercado", "Desenvolvedor");
		selenium.type("cboCodigo", "1234");
		selenium.type("resp", "teste");
		selenium.click("checkGroupareasFormacaoCheck1");
		selenium.click("checkGroupareasCheck2");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','nomeMercado','resp','@areasCheck'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Já existe um cargo com este nome"));

	}
}