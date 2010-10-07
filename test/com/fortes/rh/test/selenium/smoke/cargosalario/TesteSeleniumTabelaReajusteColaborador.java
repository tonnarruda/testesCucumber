package com.fortes.rh.test.selenium.smoke.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumTabelaReajusteColaborador extends TestCase {
	private Selenium selenium;

	public TesteSeleniumTabelaReajusteColaborador()
	{
		selenium = getSeleniumInstance();
	}

	public void testTabelaReajusteColaborador() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		
		selenium.open(getAppUrl() + "/cargosalario/tabelaReajusteColaborador/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Planejamentos de Realinhamentos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Planejamento de Realinhamentos", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('titulo','data'), new Array('data'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Planejamentos de Realinhamentos", selenium.getTitle());
	}


}
