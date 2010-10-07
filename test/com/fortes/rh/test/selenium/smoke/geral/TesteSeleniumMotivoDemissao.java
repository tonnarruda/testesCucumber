package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumMotivoDemissao extends TestCase {
	 private Selenium selenium;

	 public  TesteSeleniumMotivoDemissao()
	{
		 selenium = getSeleniumInstance();
	}

	public void testMotivoDemissao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Motivos de Desligamento");
		selenium.waitForPageToLoad("30000");
		assertEquals("Motivos de Desligamento", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Motivo de Desligamento", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('motivo'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Motivos de Desligamento", selenium.getTitle());
	}
}
