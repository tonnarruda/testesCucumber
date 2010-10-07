package com.fortes.rh.test.selenium.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumResponderPesquisa extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumResponderPesquisa()
	{
		selenium = getSeleniumInstance();
	}

	public void testResponderPesquisa() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.waitForPageToLoad("3000");
		selenium.open(getAppUrl() + "/pesquisa/colaboradorResposta/prepareResponderPesquisaPorOutroUsuario.action?pesquisa.id=1&colaborador.id=4&voltarPara=../colaboradorQuestionario/list.action?questionario.id=1&tela=colaboradorQuestionarioList");
		assertTrue(selenium.isTextPresent("Questionário - Perfil de Liderança"));
		selenium.click("RO25");
		selenium.type("RS96", "muito bem");
		selenium.click("RO27");
		selenium.select("RN98", "label=9");
		selenium.type("RS99", "e muito bom");
		selenium.click("//button[@onclick='return validaForm(document.forms[1]);']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Colaboradores da Pesquisa"));
	}

}