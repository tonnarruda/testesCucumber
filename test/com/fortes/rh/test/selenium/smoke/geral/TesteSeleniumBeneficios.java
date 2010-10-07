package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumBeneficios extends TestCase {
	 private Selenium selenium;

	 public  TesteSeleniumBeneficios()
	{
		 selenium = getSeleniumInstance();
	}

	public void testBeneficio() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Benefícios");
		selenium.waitForPageToLoad("30000");
		assertEquals("Benefícios", selenium.getTitle());
		selenium.click("//button[@type='button']");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Benefício", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('dataHist','nome','valor','perColab','perDir','perInd'), new Array('dataHist'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Benefícios", selenium.getTitle());
	}
}
