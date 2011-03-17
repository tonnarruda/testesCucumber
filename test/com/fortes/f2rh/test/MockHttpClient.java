package com.fortes.f2rh.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.log4j.Logger;

import com.fortes.rh.test.MockFileUtils;

public class MockHttpClient {

	public static final Logger logger = Logger.getLogger(MockFileUtils.class);
	
	public int executeMethod(HttpMethod method) throws IOException, HttpException {
		logger.warn("Executando MockHttpClient.executeMethod()...");
		return 0;
	}

}
