package com.fortes.rh.test.selenium.integracaoAC;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

import dbunit.DbUnitManager;

public class TesteSeleniumCargo extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCargo()
	{
		selenium = getSeleniumInstance();
	}

	@Override
	protected void setUp() throws Exception {
		new DbUnitManager(false).deleteAll("./test/com/fortes/rh/test/selenium/integracaoAC/cargoDelete.xml");
		new DbUnitManager(false).cleanAndInsert("./test/com/fortes/rh/test/selenium/integracaoAC/cargoInsert.xml");
	}

	@Override
	protected void tearDown() throws Exception {
		new DbUnitManager(false).deleteAll("./test/com/fortes/rh/test/selenium/integracaoAC/cargoDelete.xml");
	}

	public void testCargo() throws Exception
	{
		selenium.open(getAppUrl() + "/index.action");
		verificaLogin();
		
		selenium.open(getAppUrl() + "/cargosalario/cargo/list.action?page=0");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cargos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Cargo", selenium.getTitle());
		selenium.type("nome", "Cargo");
		selenium.type("nomeMercado", "Cargo");
		selenium.type("resp", "Responsabilidades");
		selenium.click("checkGroupareasCheck1");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','nomeMercado','resp','@areasCheck'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Cargo: Cargo"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action?cargoAux.id=1'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.type("nome", "I");
		selenium.type("data", "01/01/2010");
		selenium.select("tipo", "label=Por valor");
		selenium.type("valor", "100,00");
		selenium.click("//button[@onclick='validaForm(2);']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Status no AC"));
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		selenium.click("//button[@onclick='validaForm(2);']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Faixa Salarial", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Status no AC"));
		selenium.click("//button[@onclick=\"window.location='list.action?cargo.id=1'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Faixas Salariais", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='../cargo/list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Cargo"));
		
		selenium.open(getAppUrl() + "/logout.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Login", selenium.getTitle());
	}
}