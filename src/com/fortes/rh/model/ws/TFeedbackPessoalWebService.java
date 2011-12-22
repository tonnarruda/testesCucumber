package com.fortes.rh.model.ws;

import java.io.Serializable;

public class TFeedbackPessoalWebService implements Serializable
{
	private Boolean sucesso;
	private String mensagem;
	private String exception;
	
	public TFeedbackPessoalWebService()
	{
		
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	
}