package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRelatorioProcessosSeletivos extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumRelatorioProcessosSeletivos()
	{
		selenium = getSeleniumInstance();
	}

	public void testRelatorioProcessosSeletivos() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/captacao/solicitacao/prepareRelatorioProcessoSeletivo.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório de Processos Seletivos", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('ano', 'cargo', '@etapaCheck'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());

	}


}