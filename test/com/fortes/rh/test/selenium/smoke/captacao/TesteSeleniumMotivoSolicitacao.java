package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumMotivoSolicitacao extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumMotivoSolicitacao()
	{
		selenium = getSeleniumInstance();
	}

	public void testMotivoSolicitacao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.click("link=Motivos de Solicitação de Pessoal");
		selenium.waitForPageToLoad("30000");
		assertEquals("Motivos de Solicitação", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Motivo de Solicitação", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('descricao'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Motivos de Solicitação", selenium.getTitle());
	}

}