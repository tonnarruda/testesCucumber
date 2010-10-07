package com.fortes.rh.test.selenium.smoke.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumResultadoEntrevistaDeDesligamentoMenu extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumResultadoEntrevistaDeDesligamentoMenu()
	{
		selenium = getSeleniumInstance();
	}

	public void testResultadoEntrevistaDeDesligamento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		
		selenium.open(getAppUrl() + "/index.action");
		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow373");
		selenium.click("menuItemText403");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Gerar Relatório da Entrevista"));
		assertTrue(selenium.isTextPresent("Modelo de Entrevista"));
		selenium.select("entrevista", "label=Entrevista de Desligamento");
		selenium.click("//option[@value='3']");
		selenium.click("agruparPorAspectos");
		assertTrue(selenium.isTextPresent("Agrupar perguntas por aspecto"));
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('entrevista'), new Array('periodoIni','periodoFim'));\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Resultado da Entrevista"));
		assertTrue(selenium.isTextPresent("De uma nota para sua função"));
		assertTrue(selenium.isTextPresent("Média: 9"));
		assertTrue(selenium.isTextPresent("1 resposta"));
		selenium.click("//button[@onclick=\"window.location='prepareResultadoEntrevista.action?questionario.id=3'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Gerar Relatório da Entrevista"));
	}

}