package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraIndice extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraIndice()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraIndice() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText404");
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Novo Índice", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','dataHist','valor'), new Array('dataHist'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("nome", "Salario A");
		selenium.type("dataHist", "01/01/2000");
		selenium.type("valor", "3.000,00");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','dataHist','valor'), new Array('dataHist'));\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Índice", selenium.getTitle());
		selenium.type("nome", "");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("nome", "Salario B");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Índice", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Histórico do Índice", selenium.getTitle());
		selenium.type("valor", "");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataHist','valor'), new Array('dataHist'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("valor", "3.000,00");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataHist','valor'), new Array('dataHist'));\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Índice", selenium.getTitle());
		assertTrue(selenium.isTextPresent("3.000,00"));
		selenium.click("//table[@id='indiceHistorico']/tbody/tr[1]/td[1]/a[1]/img");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Histórico do Índice", selenium.getTitle());
		selenium.type("dataHist", "01/01/2000");
		selenium.type("valor", "3.000,00");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataHist','valor'), new Array('dataHist'));\"]");
		selenium.waitForPageToLoad("30000");
		selenium.click("//button[@onclick=\"window.location='../indiceHistorico/prepareInsert.action?indiceAux.id=2'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Novo Histórico do Índice", selenium.getTitle());
		selenium.type("dataHist", "01/01/2000");
		selenium.type("valor", "3.000,00");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataHist','valor'), new Array('dataHist'));\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Já existe um histórico"));
		selenium.type("dataHist", "01/02/2000");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataHist','valor'), new Array('dataHist'));\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Índice", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Salario B"));

		//Deleta historicos e indice
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Índice", selenium.getTitle());
		selenium.click("//table[@id='indiceHistorico']/tbody/tr[2]/td[1]/a[2]/img");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		assertEquals("Editar Índice", selenium.getTitle());
		selenium.click("//img[@title='Excluir']");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		assertEquals("Editar Índice", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		selenium.click("//table[@id='indice']/tbody/tr[1]/td[1]/a[2]/img");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Índice excluído com sucesso"));
		assertTrue(selenium.isTextPresent("Salario Minimo"));
	}
}
