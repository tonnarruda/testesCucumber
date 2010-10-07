package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumSolicitacaoPessoal extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumSolicitacaoPessoal()
	{
		selenium = getSeleniumInstance();
	}

	public void testSolicitacaoPessoal() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Solicitação de Pessoal");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitações de Pessoal", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Visualizar"));
		selenium.click("//input[@value='']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitações de Pessoal", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Visualizar"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Solicitação de Pessoal", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('estabelecimento','area','faixa','quantidade','motivoSolicitacaoId','cidade'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Solicitações de Pessoal", selenium.getTitle());
	}


}