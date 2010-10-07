package com.fortes.rh.test.selenium.smoke.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.fortes.rh.test.AllAceptTests;
import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumPrioridadeDeTreinamento extends TestCase {
	 private Selenium selenium;

	 public TesteSeleniumPrioridadeDeTreinamento()
	{
		 selenium = AllAceptTests.getSeleniumInstance();
	}

	public void testPrioridadeDeTreinamento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Prioridades de Treinamento");
		selenium.waitForPageToLoad("30000");
		assertEquals("Prioridades de Treinamentos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Prioridade de Treinamento", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('desc','sigla','peso'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Prioridades de Treinamentos", selenium.getTitle());
	}
}
