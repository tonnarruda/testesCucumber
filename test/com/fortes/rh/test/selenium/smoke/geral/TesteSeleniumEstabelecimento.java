package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumEstabelecimento extends TestCase {
	private Selenium selenium;

	public TesteSeleniumEstabelecimento()
	{
		selenium = getSeleniumInstance();
	}

	public void testEstabelecimento() throws Exception {

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/estabelecimento/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cadastro de Estabelecimentos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Estabelecimento Padrão"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Novo Estabelecimento", selenium.getTitle());
		selenium.click("//button[@onclick='verificaCnpj();;']");
		assertEquals("Complemento do CNPJ deve ter 4 dígitos.", selenium.getAlert());
		selenium.type("complementoCnpj", "4444");
		selenium.click("//button[@onclick='verificaCnpj();;']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cadastro de Estabelecimentos", selenium.getTitle());

	}
}
