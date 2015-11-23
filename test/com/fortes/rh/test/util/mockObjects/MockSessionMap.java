package com.fortes.rh.test.util.mockObjects;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class MockSessionMap extends AbstractMap implements Serializable
{
	private HttpServletRequest request;

	public MockSessionMap(HttpServletRequest request)
	{
		this.request = request;
	}

	public Set entrySet() {
		return null;
	}
}
