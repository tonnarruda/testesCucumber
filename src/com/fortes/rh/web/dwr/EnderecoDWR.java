package com.fortes.rh.web.dwr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		    Pattern pattern = Pattern.compile("<span class=\"respostadestaque\">([\\w\\s\\n\\/\\p{L}]*)</span>");
		    Matcher matcher = pattern.matcher(get.getResponseBodyAsString());

		    String response = "{\"sucesso\":\"1\",";
		    
		    matcher.find();
		    String match = matcher.group(1);
	    	response += "\"logradouro\":\"" + match.replaceAll("\\n", "").trim() + "\",";
	    	
	    	matcher.find();
	    	match = matcher.group(1);
	    	response += "\"bairro\":\"" + match.replaceAll("  ", "").replaceAll("\n", "").replaceAll("\t", "").trim()  + "\",";

	    	matcher.find();
	    	match = matcher.group(1);
	    	String cidadeEstado = "\"cidade\":\"" + match.replaceAll("  ", "").replaceAll("\n", "").replaceAll("\t", "").trim();
	    	response += cidadeEstado.substring(0,cidadeEstado.length() - 3)  + "\",";
	    	
	    	response += "\"estado\":\"" + cidadeEstado.substring(cidadeEstado.length() - 2,cidadeEstado.length()) + "\"}";
	    	
			return response;
			
		} catch (Exception e) {
			return "{\"sucesso\":\"0\"}";
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
		return "http://m.correios.com.br/movel/buscaCepConfirma.do?cepEntrada=" + cep + "&tipoCep=&cepTemp=&metodo=buscarCep";
	}
}