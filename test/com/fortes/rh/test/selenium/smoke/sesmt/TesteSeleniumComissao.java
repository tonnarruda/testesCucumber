package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumComissao extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumComissao()
	{
		selenium = getSeleniumInstance();
	}

	public void testComissao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/comissao/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Comissões", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Comissão", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form',new Array('eleicao','dataIni','dataFim'),new Array('dataIni','dataFim'))\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Comissões", selenium.getTitle());

	}
}