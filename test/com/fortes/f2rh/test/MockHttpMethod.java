package com.fortes.f2rh.test;

import java.io.IOException;

import org.apache.commons.httpclient.methods.GetMethod;

public class MockHttpMethod extends GetMethod {

	@Override
	public String getResponseBodyAsString() throws IOException {
		String json = "[{\"escolaridade_rh\":\"Superior Incompleto\",\"updated_rh\":\"25/10/2010\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cidade_rh\":\"Fortaleza\",\"id\":15,\"estado\":\"CE\"},{\"escolaridade_rh\":null,\"updated_rh\":\"27/10/2009\",\"nome\":\"Ana Cristina Soares Silva\",\"cidade_rh\":null,\"id\":112,\"estado\":null}]";
		return json;
	}
	
}
