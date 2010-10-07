package com.fortes.rh.test.selenium.smoke.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumGrupoOcupacional extends TestCase {
	private Selenium selenium;

	public TesteSeleniumGrupoOcupacional()
	{
		selenium = getSeleniumInstance();
	}

	public void testGrupoOcupacional() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Grupos Ocupacionais");
		selenium.waitForPageToLoad("30000");
		assertEquals("Grupos Ocupacionais", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Grupo Ocupacional", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Grupos Ocupacionais", selenium.getTitle());
	}
}
