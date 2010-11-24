package com.fortes.f2rh.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.methods.GetMethod;

public class MockHttpMethod extends GetMethod {

	@Override
	public String getResponseBodyAsString() throws IOException {
		String json = "[{\"escolaridade_rh\":\"Superior Incompleto\",\"updated_at\":\"2010-10-25T16:24:27Z\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cidade_rh\":\"Fortaleza\",\"id\":15,\"estado\":\"CE\"},{\"escolaridade_rh\":null,\"updated_at\":\"2009-10-27T13:06:24Z\",\"nome\":\"Ana Cristina Soares Silva\",\"cidade_rh\":null,\"id\":112,\"estado\":null}]";
		System.out.println("passou?");
		return json;
	}
	
}
