package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumSolicitacaoRealinhamentoSalarioSemHistorico extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumSolicitacaoRealinhamentoSalarioSemHistorico()
	{
		selenium = getSeleniumInstance();
	}

	public void testSolicitacaoRealinhamentoSalarioSemHistorico() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText49");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Solicitação de Realinhamento de Cargos & Salários"));
		selenium.select("tabelaReajuste", "label=Tabela 2009");
		selenium.select("areaOrganizacional", "label=Desenvolvimento > Desenvolvimento Java");
		selenium.click("//option[@value='2']");
		selenium.select("colaborador", "label=Igo Coelho");
		selenium.click("//select[@id='colaborador']/option[2]");
		selenium.select("tipoSalario", "label=Índice");
		selenium.click("//select[@id='tipoSalario']/option[3]");
		selenium.select("indice", "label=Salario selenium");
		assertEquals("Atenção: Não existe Histórico nessa data para o tipo de salário selecionado.", selenium.getAlert());
	}
}