package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumParametrosDoSistema extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumParametrosDoSistema()
	{
		selenium = getSeleniumInstance();
	}

	public void testParametrosDoSistema() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/parametrosDoSistema/prepareUpdate.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Configurações do Sistema", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('appUrl','appContext','atualizadorPath','servidorRemprot','diasLembretePesquisa'), null)\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());

	}
}