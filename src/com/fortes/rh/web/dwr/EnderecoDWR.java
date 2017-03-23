package com.fortes.rh.web.dwr;

import com.fortes.rh.util.HttpUtil;

public class EnderecoDWR {

	public String buscaPorCep(String cep) 
	{
		try {
		    String pagina = HttpUtil.getHtmlViaGet(getUrlParaCep(cep));
		    String response = pagina.substring(0, pagina.length() -1 ) + ",\"sucesso\":\"1\"}";
			return response;

		}catch (Exception e) {
			return "{\"sucesso\":\"0\"}";
		}
	}

	private String getUrlParaCep(String cep) 
	{
		return "http://api.postmon.com.br/v1/cep/" + cep;
	}
}