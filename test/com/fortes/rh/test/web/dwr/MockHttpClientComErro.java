package com.fortes.rh.test.web.dwr;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.log4j.Logger;

import com.fortes.rh.test.MockFileUtils;

public class MockHttpClientComErro {

	public static final Logger logger = Logger.getLogger(MockFileUtils.class);
	
	public int executeMethod(HttpMethod method) throws IOException, HttpException {
		logger.warn("Executando MockHttpClient.executeMethod() que lançará um HttpException...");
		throw new HttpException("Erro ao acessar servidor");
	}

}
