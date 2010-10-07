package com.fortes.rh.test.selenium;

import junit.framework.TestCase;

import com.fortes.rh.test.AllAceptTests;
import com.thoughtworks.selenium.DefaultSelenium;

public class Teste extends TestCase
{
	private DefaultSelenium selenium;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		String url = "http://localhost:8080";
		selenium = new DefaultSelenium("localhost", 4444, "*firefox /usr/lib/iceweasel/iceweasel", url);
		selenium.start();
	}

	@Override
	protected void tearDown() throws Exception
	{
		selenium.stop();
		super.tearDown();
	}

//	public void testUsuario() throws Exception
//	{
//		selenium.open(AllAceptTests.getAppUrl());
//		selenium.waitForPageToLoad("5000");
//		selenium.type("j_username", "fortes");
//		selenium.type("j_password", "1234");
//		selenium.click("submit");
//		selenium.waitForPageToLoad("5000");
//		assertEquals("medium", selenium.getTitle());
//	}

	/*public void testTesteJava() throws Exception
	{
		selenium.open("/TesteSelinium/");
		selenium.type("vMatricula", "12345");
		selenium.type("vNome", "Jose da Silva");
		selenium.type("vNota1", "3");
		selenium.type("vNota2", "3");
		selenium.type("vNotaFinal", "7");
		selenium.type("vFrequencia", "75");
		selenium.click("Enviar");
		assertTrue(selenium.isTextPresent("Aprovado"));
    }*/
}