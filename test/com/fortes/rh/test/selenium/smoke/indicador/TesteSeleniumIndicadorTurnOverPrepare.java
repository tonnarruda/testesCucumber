package com.fortes.rh.test.selenium.smoke.indicador;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumIndicadorTurnOverPrepare extends TestCase
{
	private Selenium selenium;

	public  TesteSeleniumIndicadorTurnOverPrepare()
	{
		selenium = getSeleniumInstance();
	}

	public void testIndicadorTurnOver() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/indicador/indicadorTurnOver/prepare.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Turnover (rotatividade de colaboradores)", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataDe','dataAte','@estabelecimentosCheck'), new Array('dataDe','dataAte'));;\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}
}