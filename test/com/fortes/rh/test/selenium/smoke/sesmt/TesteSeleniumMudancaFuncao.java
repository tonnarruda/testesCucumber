package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumMudancaFuncao extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumMudancaFuncao()
	{
		selenium = getSeleniumInstance();
	}

	public void testMudancaFuncao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/funcao/mudancaFuncaoFiltro.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Mudança de Função", selenium.getTitle());
		selenium.click("//button[@onclick=\"document.getElementById('pagina').value = 1; document.formBusca.submit();\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Mudança de Função", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Área Organizacional"));
		assertTrue(selenium.isTextPresent("Não existem mudanças de função a serem listadas!"));
	}

}