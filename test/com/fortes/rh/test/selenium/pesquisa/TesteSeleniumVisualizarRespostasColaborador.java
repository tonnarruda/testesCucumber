package com.fortes.rh.test.selenium.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumVisualizarRespostasColaborador extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumVisualizarRespostasColaborador()
	{
		selenium = getSeleniumInstance();
	}

	public void testVisualizarRespostasColaborador() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/pesquisa/colaboradorQuestionario/list.action?questionario.id=141");
		selenium.click("//table[@id='colaboradorQuestionario']/tbody/tr[4]/td[1]/a/img");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Questionário - Perfil de Liderança (CLONE)"));
		selenium.click("//button[@onclick=\"window.location='list.action?questionario.id=141'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Colaboradores da Pesquisa"));
	}
}