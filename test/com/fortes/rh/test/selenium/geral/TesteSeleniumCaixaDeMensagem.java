package com.fortes.rh.test.selenium.geral;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumCaixaDeMensagem extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumCaixaDeMensagem()
	{
		selenium = getSeleniumInstance();
	}

	public void testCaixaDeMensagem() throws Exception
	{
		selenium.open(getAppUrl());
		verificaLogin();

		assertTrue(selenium.isTextPresent("Caixa de Mensagens"));
		selenium.click("//img[@title='Visualizar mensagem']");
		selenium.setTimeout("50000");
		selenium.waitForPopUp("Janela1", "10000");
		selenium.selectWindow("name=Janela1");
		assertTrue(selenium.isTextPresent(":: Caixa de Mensagem ::"));
		selenium.select("lida", "label=Não");
		selenium.click("//button[@onclick='document.form.submit();']");
		selenium.waitForPageToLoad("30000");
		selenium.click("//button[@onclick='window.opener.location.reload();window.close();']");
		selenium.selectWindow("");
		selenium.keyPress("", "");
	}
}