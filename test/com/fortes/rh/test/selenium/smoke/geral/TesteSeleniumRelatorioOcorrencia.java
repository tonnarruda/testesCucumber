package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRelatorioOcorrencia extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumRelatorioOcorrencia()
	{
		selenium = getSeleniumInstance();
	}

	public void testRelatorioOcorrencia() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/ocorrencia/prepareRelatorioOcorrencia.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório de Ocorrências", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('@estabelecimentoCheck','@colaboradorCheck','@ocorrenciaCheck','dataPrevIni','dataPrevFim'), new Array('dataPrevIni','dataPrevFim'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Ocorrências", selenium.getTitle());
	}
}