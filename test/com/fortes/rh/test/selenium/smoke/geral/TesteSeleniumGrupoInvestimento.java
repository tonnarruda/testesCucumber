package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumGrupoInvestimento extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumGrupoInvestimento()
	{
		 selenium = getSeleniumInstance();
	}

	public void testGrupoInvestimento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Grupos de Investimento");
		selenium.waitForPageToLoad("30000");
		assertEquals("Grupos de Investimentos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Grupo de Investimentos", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
	}
}

