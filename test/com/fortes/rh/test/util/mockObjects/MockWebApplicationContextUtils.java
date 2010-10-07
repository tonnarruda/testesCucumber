package com.fortes.rh.test.util.mockObjects;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

public class MockWebApplicationContextUtils
{
	public static WebApplicationContext getWebApplicationContext(ServletContext sc)
	{
		return new MockWebApplicationContext();
	}
}
