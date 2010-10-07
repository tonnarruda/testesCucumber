package com.fortes.rh.test.selenium.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraAnalisePromocoesReajustes extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCadastraAnalisePromocoesReajustes()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraAnalisePromocoesReajustes() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("menuItemText26");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Planejamentos de Realinhamentos"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.type("titulo", "Promoção1");
		selenium.type("data", "01/10/2008");
		selenium.type("insert_tabelaReajusteColaborador_observacao", "teste");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('titulo','data'), new Array('data'));\"]");
		selenium.waitForPageToLoad("30000");
	}
}

