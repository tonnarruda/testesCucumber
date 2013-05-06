package com.fortes.rh.model.ws;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.fortes.rh.util.StringUtil;

public class TRetornoFeedbackPessoalWebService extends TFeedbackPessoalWebService implements Serializable
{
	private String retorno;
	
	public TRetornoFeedbackPessoalWebService(Boolean sucesso, String mensagem, String exception, String codigoretorno, String retorno) {
		super(sucesso, mensagem, exception, codigoretorno);
		this.retorno = retorno;
	}
	
	public TRetornoFeedbackPessoalWebService()
	{
		
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
	
}