package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRelatorioSolicitacaoAberta extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumRelatorioSolicitacaoAberta()
	{
		selenium = getSeleniumInstance();
	}

	public void testRelatorioSolicitacaoAberta() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/captacao/solicitacao/prepareRelatorio.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório de Solicitações Abertas", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('@etapaCheck'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}


}