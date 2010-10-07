package com.fortes.rh.test.selenium.smoke.desenvolvimento;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumPreenchimentoDaDNT extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumPreenchimentoDaDNT()
	{
		 selenium = getSeleniumInstance();
	}

	public void testNew() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.click("link=Preenchimento da DNT");
		selenium.waitForPageToLoad("30000");
		assertEquals("DNT - Colaborador X Curso X Prioridade", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Estabelecimento"));
		selenium.click("//button[@onclick=\"return validaFormulario('frmFiltro', new Array('estabelecimento','area'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		assertTrue(selenium.isTextPresent("Selecione Área Organizacional e Estabelecimento."));
	}


}
