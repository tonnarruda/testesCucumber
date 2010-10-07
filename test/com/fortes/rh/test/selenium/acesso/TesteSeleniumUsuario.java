package com.fortes.rh.test.selenium.acesso;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumUsuario extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumUsuario()
	{
		selenium = getSeleniumInstance();
	}

	public void testUsuario() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/acesso/usuario/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Usuários", selenium.getTitle());
		selenium.click("//button[@onclick=\"document.getElementById('pagina').value = 1; document.formPage.submit();\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Usuários", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nome do usuário"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Usuário", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','login','senha','confNovaSenha'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Usuários", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareAutoInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Criar Usuários Automaticamente", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('senhaPadrao'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Usuários", selenium.getTitle());
	}
}
