package com.fortes.f2rh.test;

import java.io.IOException;

import org.apache.commons.httpclient.methods.GetMethod;

public class MockHttpMethod2 extends GetMethod {

	@Override
	public String getResponseBodyAsString() throws IOException {
		String json = "[{\"escolaridade_rh\":null,\"updated_rh\":\"29/10/2009\",\"nome\":\"Laysa Lourenco Feitosa \",\"cidade_rh\":null,\"id\":1560,\"estado\":null},{\"escolaridade_rh\":\"Superior Completo\",\"updated_rh\":\"28/10/2010\",\"nome\":\"marcio elvis oliveira da silva\",\"cidade_rh\":null,\"id\":5382,\"estado\":\"\"}]";
		return json;
	}
	
}
