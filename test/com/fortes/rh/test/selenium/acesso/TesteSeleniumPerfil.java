package com.fortes.rh.test.selenium.acesso;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumPerfil extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumPerfil()
	{
		selenium = getSeleniumInstance();
	}

	public void testPerfil() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/acesso/perfil/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Perfis", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Administrador"));
		assertTrue(selenium.isTextPresent("Usuário"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Perfil", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Perfis", selenium.getTitle());
	}
}
