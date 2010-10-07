package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumEditarSolicitacaoReajuste extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEditarSolicitacaoReajuste()
	{
		selenium = getSeleniumInstance();
	}

	public void testEditarSolicitacaoReajuste() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText26");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.open(getAppUrl() + "/cargosalario/tabelaReajusteColaborador/prepareUpdate.action?tabelaReajusteColaborador.id=50");
		selenium.waitForPageToLoad("30000");
		selenium.open(getAppUrl() + "/cargosalario/reajusteColaborador/prepareUpdate.action?reajusteColaborador.id=45&tabelaReajusteColaborador.id=50");
		assertTrue(selenium.isTextPresent("Editar Solicitação de Realinhamento"));
		selenium.click("//button[@onclick=\"window.location='../tabelaReajusteColaborador/visualizar.action?tabelaReajusteColaborador.id=50'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.open(getAppUrl() + "/cargosalario/reajusteColaborador/prepareUpdate.action?reajusteColaborador.id=45&tabelaReajusteColaborador.id=50");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Editar Solicitação de Realinhamento"));
		selenium.select("faixa", "label=Coordenador de Projeto II");
		selenium.type("salarioProposto", "3.000,00");
		selenium.click("//button[@onclick='enviaForm(2);']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
	}
}