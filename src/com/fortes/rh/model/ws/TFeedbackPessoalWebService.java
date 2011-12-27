package com.fortes.rh.model.ws;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.fortes.rh.util.StringUtil;

public class TFeedbackPessoalWebService implements Serializable
{
	private Boolean sucesso = false;
	private String mensagem;
	private String exception;
	
	public TFeedbackPessoalWebService(Boolean sucesso, String mensagem, String exception) {
		super();
		this.sucesso = sucesso;
		this.mensagem = mensagem;
		this.exception = exception;
	}
	
	public TFeedbackPessoalWebService()
	{
		
	}

	public Boolean getSucesso(String metodo, Object[] param, Class classe) {
		
		try {
			if(!sucesso)
			{
				String paramToJson = "";
				
				try {paramToJson = StringUtil.toJSON(param, null);					
				} catch (Exception e) {e.printStackTrace();}
				
				Logger logger = Logger.getLogger(classe);
				logger.error("Erro WebService do AC:");
				logger.error(metodo);
				logger.error(paramToJson);
				logger.error(mensagem);
				logger.error(exception);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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