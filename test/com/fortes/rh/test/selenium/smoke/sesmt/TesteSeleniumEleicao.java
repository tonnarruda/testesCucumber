package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumEleicao extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumEleicao()
	{
		selenium = getSeleniumInstance();
	}

	public void testEleicao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/eleicao/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Eleicões", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Eleição", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('posse','estabelecimento'), new Array('posse'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());

	}
}