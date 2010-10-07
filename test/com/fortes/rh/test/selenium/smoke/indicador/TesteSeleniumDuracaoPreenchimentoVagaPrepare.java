package com.fortes.rh.test.selenium.smoke.indicador;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumDuracaoPreenchimentoVagaPrepare extends TestCase
{
	private Selenium selenium;

	public  TesteSeleniumDuracaoPreenchimentoVagaPrepare()
	{
		selenium = getSeleniumInstance();
	}

	public void testDuracaoPreenchimentoVaga() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/indicador/duracaoPreenchimentoVaga/prepare.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Recrutamento e Seleção - Duração para Preenchimento de Vaga", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataDe','dataAte','@estabelecimentosCheck','@areasCheck'), new Array('dataDe','dataAte'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}
}