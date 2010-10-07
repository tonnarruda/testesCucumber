package com.fortes.rh.test.selenium.smoke.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumEnviarMensagem extends TestCase {
	private Selenium selenium;

	public  TesteSeleniumEnviarMensagem()
	{
		 selenium = getSeleniumInstance();
	}

	public void testDocumentoVersao() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();
		selenium.open(getAppUrl() + "/geral/usuarioMensagem/prepareUpdate.action");
		selenium.waitForPageToLoad("30000");
		assertEquals("Enviar Mensagem", selenium.getTitle());
		selenium.click("//button[@onclick=\"return validaFormulario('form', new Array('@usuariosCheck'));\"]");
		assertEquals("Preencha os campos indicados.", selenium.getAlert());
	}
}
