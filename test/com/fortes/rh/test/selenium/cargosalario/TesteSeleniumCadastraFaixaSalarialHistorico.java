package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraFaixaSalarialHistorico extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraFaixaSalarialHistorico()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraFaixaSalarialHistorico() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText11");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cargos", selenium.getTitle());
		selenium.click("//img[@title='Faixas Salariais']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action?cargoAux.id=5'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Faixa Salarial", selenium.getTitle());
		selenium.click("//button[@onclick='validaForm(2);']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.select("tipo", "label=Valor");
		selenium.click("//button[@onclick='validaForm(2);']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action?cargo.id=5'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());

		selenium.click("//button[@onclick=\"window.location='prepareInsert.action?cargoAux.id=5'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Faixa Salarial", selenium.getTitle());
		selenium.type("nome", "IV");
		selenium.type("data", "10/12/2008");
		selenium.select("indice", "label=Salario Minimo");
		selenium.type("quantidade", "5");
		selenium.click("//button[@onclick='validaForm(2);']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());
		selenium.click("//table[@id='faixaSalarial']/tbody/tr[4]/td[1]/a[1]/img");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Faixa Salarial", selenium.getTitle());
		selenium.type("nome", "");
		selenium.click("//button[@onclick='validaForm(2);']");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("nome", "IV");
		selenium.click("//button[@onclick='validaForm(2);']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());
		selenium.click("//table[@id='faixaSalarial']/tbody/tr[4]/td[1]/a[1]/img");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Faixa Salarial", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Consultor Técnico"));
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Histórico da Faixa Salarial", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='../faixaSalarial/prepareUpdate.action?faixaSalarialAux.id=132'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Faixa Salarial", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='../faixaSalarialHistorico/prepareInsert.action?faixaSalarialAux.id=132'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Novo Histórico da Faixa Salarial", selenium.getTitle());
		selenium.type("data", "10/12/2008");
		selenium.type("quantidade", "3");
		selenium.click("//button[@onclick='validaForm(2);']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Novo Histórico da Faixa Salarial", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Já existe"));
		selenium.type("data", "10/12/2008");
		selenium.select("tipo", "label=Valor");
		selenium.select("indice", "label=Salario Minimo");
		selenium.type("valor", "500,00");
		selenium.click("//button[@onclick='validaForm(2);']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Faixa Salarial", selenium.getTitle());
		selenium.click("//table[@id='faixaSalarialHistorico']/tbody/tr[1]/td[1]/a[2]/img");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Faixa Salarial", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Consultor Técnico"));
		selenium.click("//button[@onclick=\"window.location='list.action?cargo.id=5'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());
		selenium.click("//table[@id='faixaSalarial']/tbody/tr[4]/td[1]/a[2]/img");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Faixa Salarial excluída com sucesso"));
	}
}
