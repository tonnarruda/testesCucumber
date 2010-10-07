package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumAreaOrganizacional extends TestCase {
	private Selenium selenium;

	public TesteSeleniumAreaOrganizacional()
	{
		selenium = getSeleniumInstance();
	}

	public void testAreaOrganizacional() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Áreas Organizacionais");
		selenium.waitForPageToLoad("30000");
		assertEquals("Áreas Organizacionais", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nome"));
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Áreas Organizacionais", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nome"));
		selenium.click("//button[@type='button']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Área Organizacional", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Áreas Organizacionais", selenium.getTitle());
	}
}
