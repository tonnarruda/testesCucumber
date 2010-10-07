package com.fortes.rh.test.selenium.integracaoAC;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getPathIntegracaoAC;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.fortes.rh.test.db.geral.DbUnitManager;
import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumColaboradorOcorrenciaOcorrencia extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumColaboradorOcorrenciaOcorrencia()
	{
		selenium = getSeleniumInstance();
	}

	@Override
	protected void setUp() throws Exception
	{
		new DbUnitManager(false).deleteAll(getPathIntegracaoAC() + "deleteColaboradorOcorrencia.xml");
		new DbUnitManager(false).deleteAll(getPathIntegracaoAC() + "deleteMinimoTestIntegracao.xml ");
		new DbUnitManager(false).cleanAndInsert(getPathIntegracaoAC() + "dumpMinimoTestIntegracao.xml");
		new DbUnitManager(false).cleanAndInsert(getPathIntegracaoAC() + "dumpColaboradorOcorrencia.xml");
	}

	@Override
	protected void tearDown() throws Exception
	{
		new DbUnitManager(false).deleteAll(getPathIntegracaoAC() + "deleteColaboradorOcorrencia.xml");
		new DbUnitManager(false).deleteAll(getPathIntegracaoAC() + "deleteMinimoTestIntegracao.xml");
	}
	
	public void testColaboradorOcorrenciaOcorrencia() throws Exception
	{
		selenium.open(getAppUrl() + "/index.action");
		verificaLogin();
		selenium.open(getAppUrl() + "/geral/colaborador/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Colaboradores", selenium.getTitle());
		selenium.click("//img[@title='Ocorrências']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Ocorrências", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action?colab.id=1'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Ocorrência do Colaborador", selenium.getTitle());
		selenium.select("ocorrencia", "label=ATRASO");
		selenium.type("dataIni", "01/01/2008");
		selenium.type("dataFim", "04/04/2010");
		selenium.type("insert_colaboradorOcorrencia_observacao", "teste");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('ocorrencia','dataIni'), new Array('dataIni','dataFim'));\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Ocorrências", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Colaborador: Teste Comercial"));
		assertTrue(selenium.isTextPresent("ATRASO"));
		
		selenium.open(getAppUrl() + "/logout.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Login", selenium.getTitle());
	}
}