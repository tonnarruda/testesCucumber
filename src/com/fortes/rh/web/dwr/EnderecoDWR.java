package com.fortes.rh.web.dwr;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.fortes.rh.util.HttpUtil;

@Component
public class EnderecoDWR {

	 //inserido dessa forma, pois a expresao que adiciona todos os caracter .(ponto) nao funciona neste caso.
	 private String expressaoRegular = "[\\w\\s\\n\\-\\*\\|\\@\\#\\%\\&\\(\\)\\+\\=\\§\\!\\?\\;\\:\\>\\<\\,\\.\\/\\p{L}]*"; 

	public String buscaPorCep(String cep) 
	{
		try {
		    String pagina = HttpUtil.getHtmlViaPost(getUrlParaCep(cep));
			
			Pattern pattern = Pattern.compile("<span class=\"respostadestaque\">("+ expressaoRegular + ")</span>");
			Matcher matcher = pattern.matcher(pagina);

		    String response = "{\"sucesso\":\"1\",";
		    
		    matcher.find();
		    String match = matcher.group(1).replaceAll("\\n", "").trim();
	    	response += "\"logradouro\":\"" + match.substring(0, Math.min(match.length(), 40)) + "\",";
	    	
	    	matcher.find();
	    	match = matcher.group(1).replaceAll("  ", "").replaceAll("\n", "").replaceAll("\t", "").trim();
	    	response += "\"bairro\":\"" + match.substring(0, Math.min(match.length(), 85))  + "\",";

	    	matcher.find();
	    	match = matcher.group(1);
	    	String cidadeEstado = "\"cidade\":\"" + match.replaceAll("  ", "").replaceAll("\n", "").replaceAll("\t", "").trim();
	    	response += cidadeEstado.substring(0,cidadeEstado.length() - 3)  + "\",";
	    	
	    	response += "\"estado\":\"" + cidadeEstado.substring(cidadeEstado.length() - 2,cidadeEstado.length()) + "\"}";
	    	
			return response;
			
		}catch (UnknownHostException ue){ 
			return "{\"sucesso\":\"2\"}";
		}catch (Exception e) {
			return "{\"sucesso\":\"0\"}";
		}
	}

	private String getUrlParaCep(String cep) 
	{
		return "http://m.correios.com.br/movel/buscaCepConfirma.do?cepEntrada=" + cep + "&tipoCep=&cepTemp=&metodo=buscarCep";
	}
}