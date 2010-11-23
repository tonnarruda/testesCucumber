package com.fortes.f2rh.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;

public class MockHttpClient {

	public int executeMethod(HttpMethod method) throws IOException, HttpException {
		//method = new MockHttpMethod();
		return 0;
	}

}
