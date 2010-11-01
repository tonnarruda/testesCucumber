package com.fortes.rh.test.selenium.integracaoAC;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getPathIntegracaoAC;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

import dbunit.DbUnitManager;

public class TesteSeleniumAreaLotacao extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumAreaLotacao()
	{
		selenium = getSeleniumInstance();
	}

	@Override
	protected void setUp() throws Exception
	{
		new DbUnitManager().deleteAll(getPathIntegracaoAC() + "deleteMinimoTestIntegracao.xml ");
		new DbUnitManager().cleanAndInsert(getPathIntegracaoAC() + "dumpMinimoTestIntegracao.xml");
	}

	@Override
	protected void tearDown() throws Exception
	{
		new DbUnitManager().deleteAll(getPathIntegracaoAC() + "deleteMinimoTestIntegracao.xml");
	}

	public void testAreaLotcao() throws Exception
	{
		System.out.println(getPathIntegracaoAC() + "dumpMinimoTestIntegracao.xml");
		selenium.open(getAppUrl() + "/index.action");
		verificaLogin();
		selenium.open(getAppUrl() + "/geral/areaOrganizacional/list.action");
		selenium.waitForPageToLoad("30000");
		selenium.click("//button[@type='button']");
		selenium.waitForPageToLoad("30000");
		selenium.type("nome", "teste ac");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Áreas Organizacionais", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Código AC"));

		String codigoAC = selenium.getText("//table[@id='areaOrganizacional']/tbody/tr/td[4]");
		assertFalse(codigoAC.equals(""));// tem que ter um codigo, exemplo 003

		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Área Organizacional", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		selenium.waitForPageToLoad("30000");
		// verifica se o codigoAC é o mesmo que o AC enviou
		assertTrue(codigoAC.equals(selenium.getText("//table[@id='areaOrganizacional']/tbody/tr/td[4]")));
		assertEquals("Áreas Organizacionais", selenium.getTitle());

		selenium.click("//img[@title='Excluir']");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Área Organizacional excluída com sucesso"));

		selenium.open(getAppUrl() + "/logout.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Login", selenium.getTitle());
	}
}