package com.fortes.rh.test.selenium.smoke.cargosalario;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumRelatorioSalario extends TestCase {
	private Selenium selenium;

	public TesteSeleniumRelatorioSalario()
	{
		selenium = getSeleniumInstance();
	}

	public void testRelatorioSalario() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("//div[@id='logoDiv']/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.click("DHTMLSuite_menuBar_arrow361");
		selenium.click("DHTMLSuite_menuItem53");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Relatório de Colaboradores/Salário"));
		assertTrue(selenium.isTextPresent("Filtrar Por"));
		selenium.select("optFiltro", "label=Grupo Ocupacional");
		assertTrue(selenium.isTextPresent("Grupos Ocupacionais"));
		selenium.select("optFiltro", "label=Área Organizacional");
		assertTrue(selenium.isTextPresent("Áreas Organizacionais"));
	}



}
