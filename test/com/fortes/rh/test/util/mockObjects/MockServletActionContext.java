package com.fortes.rh.test.util.mockObjects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class MockServletActionContext
{
	public ServletContext getServletContext()
	{
		return new MockServletContext();
	}

	public HttpServletResponse getResponse()
	{
		return new MockHttpServletResponse();
	}

	public HttpServletRequest getRequest()
	{
		return new MockHttpServletRequest();
	}
	
}
