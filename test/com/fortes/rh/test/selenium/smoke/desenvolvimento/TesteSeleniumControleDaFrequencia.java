package com.fortes.rh.test.selenium.smoke.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumControleDaFrequencia extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumControleDaFrequencia()
	{
		 selenium = getSeleniumInstance();
	}

	public void testNew() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Frequência");
		selenium.waitForPageToLoad("30000");
		assertEquals("Controle de Freqüência", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('curso'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		assertEquals("Controle de Freqüência", selenium.getTitle());
	}

}
