package com.fortes.rh.test.selenium.smoke.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumAvaliacaoCandidato extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumAvaliacaoCandidato()
	{
		selenium = getSeleniumInstance();
	}

	public void testConhecimento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/captacao/candidato/prepareRelatorioAvaliacaoCandidatos.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório de Avaliações de Candidatos", selenium.getTitle());
		selenium.click("//button[@onclick='validaForm();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		
	}
}