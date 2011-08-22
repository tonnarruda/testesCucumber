package com.fortes.rh.model.ws;

import java.io.Serializable;

public class FeedbackWebService implements Serializable
{
	private boolean sucesso;
	private String mensagem;
	private String exception;
	
	public FeedbackWebService()
	{
		
	}
	
	public FeedbackWebService(boolean sucesso, String mensagem, String exception) 
	{
		super();
		this.sucesso = sucesso;
		this.mensagem = mensagem;
		this.exception = exception;
	}

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
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