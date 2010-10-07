package com.fortes.rh.test.selenium.smoke.pesquisa;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumEntrevistaDeDesligamento extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEntrevistaDeDesligamento()
	{
		selenium = getSeleniumInstance();
	}

	public void testEntrevistaDeDesligamento() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		
		selenium.open(getAppUrl() + "/pesquisa/entrevista/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Modelos de Entrevistas de Desligamento", selenium.getTitle());
		selenium.click("link=Modelos de Entrevistas de Desligamento");
		selenium.waitForPageToLoad("30000");
		assertEquals("Modelos de Entrevistas de Desligamento", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Modelo de Entrevista", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('titulo','liberado'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Modelos de Entrevistas de Desligamento", selenium.getTitle());
	}

}