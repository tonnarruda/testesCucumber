package com.fortes.rh.test.selenium.smoke.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumAvaliacaoCurso extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumAvaliacaoCurso()
	{
		 selenium = getSeleniumInstance();
	}

	public void testCurso() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Avaliações dos Cursos");
		selenium.waitForPageToLoad("30000");
		assertEquals("Avaliacões dos Cursos", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Nova Avaliação do Aluno", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('titulo','tipo'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Avaliacões dos Cursos", selenium.getTitle());
	}

}
