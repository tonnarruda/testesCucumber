package com.fortes.f2rh.test;

import java.io.IOException;

import org.apache.commons.httpclient.methods.GetMethod;

public class MockHttpMethod2 extends GetMethod {

	@Override
	public String getResponseBodyAsString() throws IOException {
		String json = "[{\"escolaridade_rh\":null,\"updated_at\":\"2009-10-29T14:25:51Z\",\"nome\":\"Laysa Lourenco Feitosa \",\"cidade_rh\":null,\"id\":1560,\"estado\":null},{\"escolaridade_rh\":\"Superior Completo\",\"updated_at\":\"2010-10-28T10:54:52Z\",\"nome\":\"marcio elvis oliveira da silva\",\"cidade_rh\":null,\"id\":5382,\"estado\":\"\"}]";
		return json;
	}
	
}
