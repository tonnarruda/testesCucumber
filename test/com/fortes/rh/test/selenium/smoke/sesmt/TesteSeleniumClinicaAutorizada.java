package com.fortes.rh.test.selenium.smoke.sesmt;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


public class TesteSeleniumClinicaAutorizada extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumClinicaAutorizada()
	{
		selenium = getSeleniumInstance();
	}

	public void testClinicaAutorizada() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/sesmt/clinicaAutorizada/list.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Clínicas e Médicos Autorizados", selenium.getTitle());
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Inserir Clínica ou Médico Autorizado", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('nome','data','tipo'), new Array('data', 'cnpj', 'dataInat'))\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.click("//button[@onclick=\"window.location='list.action'\"]");
		selenium.waitForPageToLoad("30000");
		assertEquals("Clínicas e Médicos Autorizados", selenium.getTitle());
	}
}