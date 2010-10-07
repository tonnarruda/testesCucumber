package com.fortes.rh.test.selenium.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumResultadoPesquisa extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumResultadoPesquisa()
	{
		selenium = getSeleniumInstance();
	}

	public void testFiltroResultadoPesquisa() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow353");
		selenium.click("menuItemText28");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Pesquisas"));
		selenium.click("//img[@title='Resultado']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Gerar Relatório da pesquisa"));
		assertTrue(selenium.isTextPresent("Pesquisa: Perfil de Liderança"));
		selenium.click("checkGroupareasCheck1");
		selenium.click("checkGroupareasCheck3");
		selenium.click("checkGroupareasCheck3");
		selenium.click("checkGroupareasCheck2");
		selenium.click("exibirRespostas");
		selenium.click("exibirComentarios");
		selenium.click("//button[@onclick=\"return validaFormulario('form', null, null);\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Resultado da Pesquisa"));
		assertTrue(selenium.isTextPresent("Liderança"));
		assertTrue(selenium.isTextPresent("1) Você tem um bom lider?"));
		assertTrue(selenium.isTextPresent("2 Respostas"));
		assertTrue(selenium.isTextPresent("Comentários:"));
		assertTrue(selenium.isTextPresent("Charles Freitas: sim ele é um bom lider"));
		assertTrue(selenium.isTextPresent("Igo Coelho: não gosto dele"));
		assertTrue(selenium.isTextPresent("4) De uma nota de 0 a 10 para sua equipe."));
		assertTrue(selenium.isTextPresent("3 Respostas"));
		assertTrue(selenium.isTextPresent("Média: 8,333"));
		selenium.click("//button[@onclick=\"window.location='prepareResultado.action?pesquisa.id=1'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Gerar Relatório da pesquisa"));
	}
}
