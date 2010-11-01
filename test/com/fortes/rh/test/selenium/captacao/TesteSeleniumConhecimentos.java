package com.fortes.rh.test.selenium.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

import dbunit.DbUnitManager;

public class TesteSeleniumConhecimentos extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumConhecimentos()
	{
		selenium = getSeleniumInstance();
	}

	@Override
	protected void setUp() throws Exception {
		new DbUnitManager().clear();
		new DbUnitManager(false).insert("./test/com/fortes/rh/test/selenium/captacao/conhecimento.xml");
	}
	
	public void testConhecimento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText5");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Conhecimentos"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Inserir Conhecimento"));
		selenium.type("nome", "Selenium");
		selenium.click("//span[@onclick=\"marcarDesmarcarListCheckBox(document.forms[0], 'areasCheck',true); \"]");
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Conhecimentos"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.type("nome", "Selenium");
		selenium.click("//span[@onclick=\"marcarDesmarcarListCheckBox(document.forms[0], 'areasCheck',true); \"]");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','@areasCheck'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Conhecimentos"));
		selenium.open(getAppUrl() + "/captacao/conhecimento/prepareUpdate.action?conhecimento.id=1");
		selenium.waitForPageToLoad("30000");
		selenium.type("nome", "");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','@areasCheck'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("nome", "Windows");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','@areasCheck'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Conhecimentos"));
		selenium.click("//img[@title='Excluir']");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Conhecimento excluído com sucesso."));
	}
}