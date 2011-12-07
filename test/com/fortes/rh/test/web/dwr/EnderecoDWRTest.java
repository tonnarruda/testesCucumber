package com.fortes.rh.test.web.dwr;

import junit.framework.TestCase;
import mockit.Mockit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;

import com.fortes.f2rh.test.MockHttpClient;
import com.fortes.f2rh.test.MockHttpMethod;
import com.fortes.rh.web.dwr.EnderecoDWR;

public class EnderecoDWRTest extends TestCase {

	EnderecoDWR enderecoDwr;
	private String cep;
	
	public void setUp() {
		enderecoDwr = new EnderecoDWR();
	}
	
	public void testDeveBuscarPorCep() {
		
		dadoQueServidorDoYahooEstaOnline();
		
		String json = enderecoDwr.buscaPorCep("63657520325");
		
		assertNotNull("json", json);
	}
	
	private void dadoQueServidorDoYahooEstaOnline() {
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);		
	}

	public void testDeveRetornarErroQuandoServidorDoYahooEstiverOffline() {
			assertEquals("{\"sucesso\":\"0\"}", enderecoDwr.buscaPorCep("63657520325"));
	}
}
