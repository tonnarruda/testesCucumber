package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumEditarRealinhamentoSalarioSemHistorico extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEditarRealinhamentoSalarioSemHistorico()
	{
		selenium = getSeleniumInstance();
	}

	public void testEditarRealinhamentoSalarioSemHistorico() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText26");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.click("//img[@title='Editar']");
		selenium.waitForPageToLoad("30000");
		selenium.open(getAppUrl() + "/cargosalario/reajusteColaborador/prepareUpdate.action?reajusteColaborador.id=45&tabelaReajusteColaborador.id=50");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Editar Solicitação de Realinhamento"));
		selenium.select("tipoSalario", "label=Índice");
		selenium.click("//select[@id='tipoSalario']/option[3]");
		selenium.select("indice", "label=Salario selenium");
		assertEquals("Atenção: Não existe Histórico nessa data para o tipo de salário selecionado.", selenium.getAlert());
	}
}