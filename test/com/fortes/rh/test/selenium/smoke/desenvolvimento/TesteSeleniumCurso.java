package com.fortes.rh.test.selenium.smoke.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCurso extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumCurso()
	{
		 selenium = getSeleniumInstance();
	}

	public void testCurso() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Cursos/Treinamentos");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cursos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nome"));
		selenium.click("//input[@value='' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cursos", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Nome"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Curso", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action?page=0'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Cursos", selenium.getTitle());
	}

}
