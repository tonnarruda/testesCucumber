package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumInvestimentosDaEmpresa extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumInvestimentosDaEmpresa()
	{
		selenium = getSeleniumInstance();
	}

	public void testInvestimentosDaEmpresa() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		
		selenium.open(getAppUrl() + "/geral/gastoEmpresa/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Investimentos da Empresa - por Colaborador", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.waitForCondition("selenium.isTextPresent('Inserir Investimentos da Empresa - por Colaborador')", "30000");
		
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('mesAno','colab'), new Array('mesAno'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Investimentos da Empresa - por Colaborador", selenium.getTitle());
	}
}