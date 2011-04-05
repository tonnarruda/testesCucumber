package com.fortes.f2rh.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.log4j.Logger;

public class MockHttpClient {

	public static final Logger logger = Logger.getLogger(MockHttpClient.class);
	
	public int executeMethod(HttpMethod method) throws IOException, HttpException {
		logger.warn("Executando MockHttpClient.executeMethod()...");
		return 0;
	}

}
