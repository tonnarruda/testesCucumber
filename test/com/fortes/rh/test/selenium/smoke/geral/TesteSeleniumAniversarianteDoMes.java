package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumAniversarianteDoMes extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumAniversarianteDoMes()
	{
		 selenium = getSeleniumInstance();
	}

	public void testDocumentoVersao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/colaborador/relatorioAniversariantes.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório - Aniversariantes do mês", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', null, null);\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório - Aniversariantes do mês", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Não existem colaboradores para o filtro informado."));
	}
}
