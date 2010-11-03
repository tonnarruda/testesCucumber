package com.fortes.rh.test.selenium.integracaoAC;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getPathIntegracaoAC;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

import dbunit.DbUnitManager;

public class TesteSeleniumOcorrenciaTipoOcorrencia extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumOcorrenciaTipoOcorrencia()
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

	public void testOcorrenciaTipoOcorrencia() throws Exception
	{
		selenium.open(getAppUrl() + "/index.action");
		verificaLogin();
		selenium.open(getAppUrl() + "/geral/ocorrencia/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Ocorrências", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		
		selenium.waitForPageToLoad("30000");
		selenium.type("descricao", "teste");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('descricao','pontuacao'), null);\"]");

		selenium.waitForPageToLoad("30000");
		assertEquals("Ocorrências", selenium.getTitle());
		selenium.click("//img[@title='Editar']");
		
		selenium.waitForPageToLoad("30000");
		assertEquals("Editar Ocorrência", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('descricao','pontuacao'), null);\"]");
		
		selenium.waitForPageToLoad("30000");
		assertEquals("Ocorrências", selenium.getTitle());
		selenium.click("//img[@title='Excluir']");
		assertTrue(selenium.getConfirmation().matches("^Confirma exclusão[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Ocorrência excluída com sucesso"));
		
		selenium.open(getAppUrl() + "/logout.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Login", selenium.getTitle());
	}
}