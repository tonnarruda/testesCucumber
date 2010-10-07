package com.fortes.rh.test;

import com.thoughtworks.selenium.SeleneseTestCase;

public class TesteSelenium extends SeleneseTestCase
{
	@Override
	public void setUp() throws Exception
	{
		setUp("http://localhost:4444", "*iexplore");
	}

	@Override
	public void tearDown() throws Exception
	{
		selenium.stop();
	}

	public void testLogin()
	{
		selenium.open(AllAceptTests.getAppUrl() + "/");

		assertEquals("Login", selenium.getTitle());

		selenium.type("username", "fortes");
		selenium.type("password", "1234");
		selenium.click("submit");

		selenium.waitForPageToLoad("30000");

		assertEquals("Login", selenium.getTitle());
		assertTrue(selenium.isTextPresent("Usuário sem permissão de acesso"));

		selenium.type("username", "fortes");
		selenium.type("password", "funkthecat");
		selenium.click("submit");

		selenium.waitForPageToLoad("50000");

		assertEquals("FortesRH", selenium.getTitle());
	}
}