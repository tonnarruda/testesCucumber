package com.fortes.rh.test.selenium.acesso;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumAlterarSenha extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumAlterarSenha()
	{
		selenium = getSeleniumInstance();
	}

	public void testAlterarSenha() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/acesso/usuario/prepareUpdateSenhaUsuario.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Alterar senha de Usuário", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('senha','novaSenha','confSenha'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}
}
