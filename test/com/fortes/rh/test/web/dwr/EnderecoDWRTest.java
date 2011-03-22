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
		
		dadoUmCepValido();
		dadoQueServidorDoYahooEstaOnline();
		
		String json = enderecoDwr.buscaPorCep(cep);
		
		assertNotNull("json", json);
	}
	
	private void dadoQueServidorDoYahooEstaOnline() {
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);		
	}

	public void testDeveRetornarErroQuandoServidorDoYahooEstiverOffline() {
		
		dadoUmCepValido();
		dadoQueServidorDoYahooEstaOffline();
		
		try {
			enderecoDwr.buscaPorCep(cep);
			fail("Deveria ter lançado um RuntimeException");
		} catch (Exception e) {
			assertTrue("erro ao acessar yahoo", e.getCause() instanceof HttpException);
		}
	}

	private void dadoQueServidorDoYahooEstaOffline() {
		Mockit.redefineMethods(HttpClient.class, MockHttpClientComErro.class);
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
	}

	private void dadoUmCepValido() {
		cep = "63657520325";
	}
	
}
