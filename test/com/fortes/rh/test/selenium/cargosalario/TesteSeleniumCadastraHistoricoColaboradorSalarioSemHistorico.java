package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraHistoricoColaboradorSalarioSemHistorico extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraHistoricoColaboradorSalarioSemHistorico()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraHistoricoColaboradorComSalarioSemHistorico() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/fortesrh/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow373");
		selenium.click("DHTMLSuite_menuItem8");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Colaboradores"));
		selenium.click("//table[@id='colaborador']/tbody/tr[4]/td[1]/a[3]/img");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Progressão do Colaborador"));
		selenium.click("//button[@onclick=\"window.location='historicoColaboradorList.action?colaborador.id=4'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Editar Históricos do Colaborador"));
		selenium.click("//table[@id='historicoColaborador']/tbody/tr[2]/td[1]/a[1]/img");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Editar Histórico Colaborador"));
		selenium.select("tipoSalario", "label=Índice");
		selenium.click("//select[@id='tipoSalario']/option[3]");
		selenium.select("indice", "label=Salario selenium");
		assertEquals("Atenção: Não existe Histórico nessa data para o tipo de salário selecionado.", selenium.getAlert());

	}
}