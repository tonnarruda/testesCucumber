package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRelatorioMotivoDeDesligamento extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumRelatorioMotivoDeDesligamento()
	{
		selenium = getSeleniumInstance();
	}

	public void testNew() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		
		selenium.open(getAppUrl() + "/geral/motivoDemissao/prepareRelatorioMotivoDemissao.action");
		assertEquals("Relatório de Desligamento", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataIni', 'dataFim', '@estabelecimentosCheck'), new Array('dataIni', 'dataFim'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}

}