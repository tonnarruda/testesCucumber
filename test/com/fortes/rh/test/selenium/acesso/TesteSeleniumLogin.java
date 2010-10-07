package com.fortes.rh.test.selenium.acesso;

import static com.fortes.rh.test.AbstractSeleniumSuite.getAppUrl;
import static com.fortes.rh.test.AbstractSeleniumSuite.getSeleniumInstance;
import static com.fortes.rh.test.AbstractSeleniumSuite.verificaLogin;
import junit.framework.TestCase;

import com.fortes.rh.test.db.geral.DbUnitManager;
import com.thoughtworks.selenium.Selenium;

public class TesteSeleniumLogin extends TestCase
{
	private Selenium selenium;

	public TesteSeleniumLogin()
	{
		selenium = getSeleniumInstance();
	}

	@Override
	protected void setUp() throws Exception {
		new DbUnitManager().clear();
	}
	
	public void loginErradoFortesRH(){
		selenium.type("username", "fortes");
		selenium.type("password", "12345");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
	}

	public void testLogin() throws Exception
	{
		selenium.open(getAppUrl());
		this.loginErradoFortesRH();
		assertTrue(selenium.isTextPresent("Usuário sem permissão de acesso"));

		selenium.open(getAppUrl());
		verificaLogin();

		selenium.open(getAppUrl() + "/index.action");

	}
}
