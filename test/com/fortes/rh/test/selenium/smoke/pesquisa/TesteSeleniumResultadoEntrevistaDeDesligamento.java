package com.fortes.rh.test.selenium.smoke.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumResultadoEntrevistaDeDesligamento extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumResultadoEntrevistaDeDesligamento()
	{
		selenium = getSeleniumInstance();
	}

	public void testResultadoEntrevistaDeDesligamento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		
		selenium.open(getAppUrl() + "/pesquisa/questionario/prepareResultadoEntrevista.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Gerar Resultado da Entrevista", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('entrevista'), new Array('periodoIni','periodoFim'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}

}