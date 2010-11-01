package com.fortes.rh.test.selenium.integracaoAC;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getPathIntegracaoAC;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

import dbunit.DbUnitManager;

public class TesteSeleniumIndice extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumIndice()
	{
		selenium = getSeleniumInstance();
	}

	@Override
	protected void setUp() throws Exception
	{
		new DbUnitManager(false).deleteAll(getPathIntegracaoAC() + "deleteMinimoTestIntegracao.xml ");
		new DbUnitManager(false).cleanAndInsert(getPathIntegracaoAC() + "dumpMinimoTestIntegracao.xml");
	}

	@Override
	protected void tearDown() throws Exception
	{
		new DbUnitManager(false).deleteAll(getPathIntegracaoAC() + "deleteMinimoTestIntegracao.xml");
	}
	
	public void testIndice() throws Exception
	{
		selenium.open(getAppUrl() + "/index.action");
		verificaLogin();
		
		selenium.open(getAppUrl() + "/cargosalario/indice/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		assertTrue(selenium.isTextPresent("A manutenção do Cadastro de Índices deve ser realizada no AC Pessoal."));
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("A manutenção no cadastro de índice deve ser realizada no AC Pessoal."));
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Índices", selenium.getTitle());
		
		selenium.open(getAppUrl() + "/logout.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Login", selenium.getTitle());
	}
}