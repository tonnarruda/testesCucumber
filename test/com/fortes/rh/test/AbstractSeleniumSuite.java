package com.fortes.rh.test;

import junit.framework.TestSuite;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public abstract class AbstractSeleniumSuite extends TestSuite
{
	private static Selenium selenium = null;
	private static String browser = null;
	private static String appUrl = null;

	private static void prepare()
	{
//		Properties properties = new Properties();
//		try
//		{
//			File file = new File("build.properties");
//			FileInputStream fileInput = new FileInputStream(file);
//			properties.load(fileInput);
//			fileInput.close();
//		}
//		catch (IOException ex)
//		{
//		     ex.printStackTrace();
//		}

//		if(properties.getProperty("selenium.browser") != null)
//			browser = properties.getProperty("selenium.browser");
//		else
			browser = "*chrome";
//		if(properties.getProperty("selenium.app.url") != null)
//			appUrl = properties.getProperty("selenium.app.url");
//		else
			appUrl = "http://localhost:8080/fortesrh";
	}


	public static void verificaLogin()
	{
		if(selenium.getTitle().equals("Login"))
		{
			selenium.type("username", "fortes");
			selenium.type("password", "1234");
			selenium.click("submit");
			selenium.waitForPageToLoad("30000");
		}
	}

	public static Selenium getSeleniumInstance()
	{
		if (selenium == null)
		{
			selenium = new DefaultSelenium("localhost", 4444, getBrowser(), "http://localhost:4444");
			selenium.start();
			selenium.open(getAppUrl());
		}

		return selenium;
	}
	
	public static String getPathIntegracaoAC()
	{
		return "./test/com/fortes/rh/test/selenium/integracaoAC/";
	}

	public static String getAppUrl()
	{
		if(appUrl == null)
			prepare();

		return appUrl;
	}

	public static String getBrowser()
	{
		if(browser == null)
			prepare();

		return browser;
	}
}