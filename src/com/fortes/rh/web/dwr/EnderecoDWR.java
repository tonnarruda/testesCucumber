package com.fortes.rh.web.dwr;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class EnderecoDWR {

	private static final int TIMEOUT = 8000; // 8 sec

	public String buscaPorCep(String cep) 
	{
		HttpClient client = createHttpClientWithTimeout(TIMEOUT);
		GetMethod get = new GetMethod(getUrlParaCep(cep));
		try {
			client.executeMethod( get );
			String response = get.getResponseBodyAsString();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			get.releaseConnection();
		}
	}

	private HttpClient createHttpClientWithTimeout(int timeout) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", timeout);
		client.getParams().setParameter("http.connection.timeout", timeout);
		return client;
	}

	private String getUrlParaCep(String cep) 
	{
		return "http://query.yahooapis.com/v1/public/yql?" +
				"q=select%20*%20from%20brazil.correios.ceplivre%20where%20cep%3D%22" +
				cep +
				"%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
	}
}