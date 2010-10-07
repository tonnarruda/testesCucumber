package com.fortes.rh.test.selenium.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumPesquisa extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumPesquisa()
	{
		selenium = getSeleniumInstance();
	}

	public void testTelaPesquisa() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		
		selenium.click("DHTMLSuite_menuBar_arrow353");
		selenium.click("DHTMLSuite_menuItem28");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisas", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Pesquisa", selenium.getTitle());
		selenium.click("//button[@onclick='validaPeriodo();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisas", selenium.getTitle());
	}
		
	public void testCadastroPesquisa() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		
		selenium.click("DHTMLSuite_menuBar_arrow353");
		selenium.click("menuItemText28");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisas", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Pesquisa", selenium.getTitle());
		selenium.type("titulo", "Teste Selenium");
		selenium.type("dataInicio", "01/01/2000");
		selenium.type("dataFim", "01/01/2009");
		selenium.select("anonima", "label=Não");
		selenium.click("//button[@onclick='validaPeriodo();']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisa - Teste Selenium", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Pesquisa: Teste Selenium"));
		selenium.click("link=Inserir pergunta aqui");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Pergunta", selenium.getTitle());
		selenium.click("//button[@onclick='return validaForm();']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("texto", "teste");
		selenium.click("//button[@onclick='return validaForm();']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisa - Teste Selenium", selenium.getTitle());
		selenium.click("//div[@id='waDiv']/div[2]/div[5]/a");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Pergunta", selenium.getTitle());
		selenium.type("texto", "teste 2");
		selenium.select("tipo", "label=Nota");
		selenium.click("checkComentario");
		selenium.click("//button[@onclick='return validaForm();']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisa - Teste Selenium", selenium.getTitle());
		selenium.click("//div[@id='waDiv']/div[2]/div[7]/a");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Pergunta", selenium.getTitle());
		selenium.type("texto", "teste 3");
		selenium.select("tipo", "label=Objetiva");
		selenium.type("respostaObjetiva", "sim");
		selenium.click("link=Mais uma opção de resposta");
		selenium.type("ro_1", "nao");
		selenium.click("//button[@onclick='return validaForm();']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Pesquisa - Teste Selenium", selenium.getTitle());
		assertTrue(selenium.isTextPresent("teste"));
		assertTrue(selenium.isTextPresent("teste 2"));
		assertTrue(selenium.isTextPresent("Selecione a nota de 1 a 10"));
		assertTrue(selenium.isTextPresent("Justifique sua resposta"));
		assertTrue(selenium.isTextPresent("teste 3"));
		assertTrue(selenium.isTextPresent("sim"));
		assertTrue(selenium.isTextPresent("nao"));

		//não foi termidado por causa do problema da url
		//selenium.click("//button[@onclick=\"window.location='../questionario/prepareAplicar.action?questionario.id=105'\"]");
		//...
	}
	
//	public void testLiberarPesquisa() throws Exception 
//	{
//		selenium.open(getAppUrl());
//		verificaLogin();
//
//		selenium.open(getAppUrl() + "/index.action");
//		selenium.click("//div[@id='logoDiv']/a/img");
//		selenium.waitForPageToLoad("30000");
//		selenium.click("DHTMLSuite_menuBar_arrow353");
//		selenium.click("DHTMLSuite_menuItem28");
//		selenium.waitForPageToLoad("30000");
//		selenium.isTextPresent("Pesquisas");
//		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
//		selenium.waitForPageToLoad("30000");
//		selenium.click("waDivTitulo");
//		selenium.isTextPresent("Inserir Pesquisa");
//		selenium.type("titulo", "teste");
//		selenium.type("dataInicio", "01/10/2008");
//		selenium.type("dataFim", "10/10/2008");
//		selenium.click("//button[@onclick='validaPeriodo();']");
//		selenium.waitForPageToLoad("30000");
//		selenium.isTextPresent("Questionário - teste");
//		selenium.click("DHTMLSuite_menuBar_arrow353");
//		selenium.click("menuItemText28");
//		selenium.isTextPresent("Pesquisas");
//		selenium.waitForPageToLoad("3000");
//		selenium.click("//img[@title='Colaboradores']");
//		selenium.waitForPageToLoad("3000");
//		selenium.isTextPresent("Colaboradores da Pesquisa teste");
//		selenium.click("//button[@id=\"btnInserir\"]");
//		selenium.waitForPageToLoad("5000");
//		selenium.click("checkGroupestabelecimentosCheck1");
//		selenium.click("checkGroupareasCheck1");
//		selenium.click("//button[@onclick='validaForm();']");
//		selenium.waitForPageToLoad("3000");
//		selenium.isTextPresent("Adicionar Colaboradores ŕ Pesquisa teste");
//		selenium.click("//button[@onclick='verificaSelecao(document.formColab);']");
//		selenium.waitForPageToLoad("3000");
//		selenium.click("//button[@id=\"btnVoltar\"]");
//		selenium.waitForPageToLoad("3000");
//		selenium.click("//img[@title='Liberar Pesquisa']");
//		selenium.getConfirmation().matches("^Deseja liberar esta pesquisa[\\s\\S]$");
//		selenium.click("//img[@title='Excluir']");
//		selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$");
//		selenium.waitForPageToLoad("5000");
//		selenium.isTextPresent("Pesquisa excluída com sucesso.");
//	}
}
