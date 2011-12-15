package com.fortes.rh.web.dwr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fortes.rh.util.StringUtil;


public class EnderecoDWR {

	public String buscaPorCep(String cep) 
	{
		try {
			String pagina = StringUtil.getHTML(getUrlParaCep(cep));
			
		    Pattern pattern = Pattern.compile("<span class=\"respostadestaque\">([\\/\\w\\s\\n\\p{L}]*)</span>");
		    Matcher matcher = pattern.matcher(pagina);

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

	private String getUrlParaCep(String cep) 
	{
		return "http://m.correios.com.br/movel/buscaCepConfirma.do?cepEntrada=" + cep + "&tipoCep=&cepTemp=&metodo=buscarCep";
	}
}