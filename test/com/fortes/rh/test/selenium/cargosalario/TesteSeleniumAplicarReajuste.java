package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumAplicarReajuste extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumAplicarReajuste()
	{
		selenium = getSeleniumInstance();
	}

	public void testAplicarReajuste() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText26");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.open(getAppUrl() + "/cargosalario/tabelaReajusteColaborador/prepareUpdate.action?tabelaReajusteColaborador.id=50");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.open(getAppUrl() + "/cargosalario/tabelaReajusteColaborador/prepareUpdate.action?tabelaReajusteColaborador.id=50");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.click("//button[@onclick=\"if (confirm('Deseja realmente aplicar o reajuste?')) window.location='aplicar.action?tabelaReajusteColaborador.id=50'\"]");
		assertTrue(selenium.getConfirmation().matches("^Deseja realmente aplicar o reajuste[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.click("//img[@title='Cancelar Reajuste']");
		assertTrue(selenium.getConfirmation().matches("^Tem certeza que deseja desfazer as Promoçőes/Reajustes[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Cancelamento efetuado com sucesso."));
	}
}