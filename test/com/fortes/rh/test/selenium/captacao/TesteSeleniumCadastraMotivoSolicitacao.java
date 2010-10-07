package com.fortes.rh.test.selenium.captacao;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCadastraMotivoSolicitacao extends TestCase {

	private Selenium selenium;

	public TesteSeleniumCadastraMotivoSolicitacao()
	{
		selenium = getSeleniumInstance();
	}

	public void testCadastraMotivoSolicitacao() throws Exception {
		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");
		selenium.click("DHTMLSuite_menuBar_arrow357");
		selenium.click("menuItemText57");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Motivos de Solicitação"));
		selenium.click("//button[@onclick=\"window.location='prepareInsert.action'\"]");
		selenium.waitForPageToLoad("30000");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('descricao'), null);\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
		selenium.type("descricao", "Exame de Seleção");
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('descricao'), null);\"]");
		selenium.waitForPageToLoad("30000");
	}
}

