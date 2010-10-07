package com.fortes.rh.test.selenium.smoke.indicador;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumProdutividadePrepareProdutividade extends TestCase
{
	private Selenium selenium;

	public  TesteSeleniumProdutividadePrepareProdutividade()
	{
		selenium = getSeleniumInstance();
	}

	public void testDuracaoPreenchimentoVaga() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl()+"/captacao/produtividade/prepareProdutividade.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Relatório de Produtividade do Setor", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('ano'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}
}