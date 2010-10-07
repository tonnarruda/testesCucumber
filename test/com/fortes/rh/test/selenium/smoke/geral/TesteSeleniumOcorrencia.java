package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumOcorrencia extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumOcorrencia()
	{
		 selenium = getSeleniumInstance();
	}

	public void testOcorrencia() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/geral/ocorrencia/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Ocorrências", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Ocorrência", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('descricao','pontuacao'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Ocorrências", selenium.getTitle());
	}
}
