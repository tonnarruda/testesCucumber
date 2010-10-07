package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumEmpresa extends TestCase {
	private Selenium selenium;

	public TesteSeleniumEmpresa()
	{
		selenium = getSeleniumInstance();
	}

	public void testEmpresa() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/empresa/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Empresas", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Empresa Padrão"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Empresa", selenium.getTitle());
		selenium.click("//button[@onclick='enviaForm();']");
		assertEquals("Base CNPJ deve ter 8 dígitos.", selenium.getAlert());
		selenium.type("cnpj", "88888888");
		selenium.click("//button[@onclick='enviaForm();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Empresas", selenium.getTitle());
	}
}
