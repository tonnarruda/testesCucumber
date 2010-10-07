package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumColaborador extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumColaborador()
	{
		 selenium = getSeleniumInstance();
	}

	public void testColaborador() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/colaborador/list.action");
		selenium.click("link=Colaboradores");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Não existem colaboradores a serem listados"));
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Não existem colaboradores a serem listados"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Colaborador", selenium.getTitle());
		selenium.click("link=Dados Funcionais");
		selenium.waitForCondition("selenium.isTextPresent('Dados do 1º Histórico')", "30000");
		selenium.click("link=Formação Escolar");
		selenium.waitForCondition("selenium.isTextPresent('Formação Escolar')", "30000");
		selenium.waitForCondition("selenium.isTextPresent('Idiomas')", "30000");
		selenium.click("link=Experiências");
		selenium.waitForCondition("selenium.isTextPresent('Experiência Profissional')", "30000");
		selenium.click("link=Documentos");
		selenium.waitForCondition("selenium.isTextPresent('Identidade')", "30000");
		selenium.click("gravar");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());

	}
}
