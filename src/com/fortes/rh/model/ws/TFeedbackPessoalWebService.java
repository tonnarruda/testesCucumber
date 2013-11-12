package com.fortes.rh.model.ws;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.fortes.rh.util.StringUtil;

public class TFeedbackPessoalWebService implements Serializable
{
	private Boolean sucesso = false;
	private String mensagem;
	private String exception;
	private String codigoretorno;
	private String retorno;
	private String codigoerro;
	
	public TFeedbackPessoalWebService(Boolean sucesso, String mensagem, String exception, String codigoretorno, String retorno, String codigoerro) {
		super();
		this.sucesso = sucesso;
		this.mensagem = mensagem;
		this.exception = exception;
		this.codigoretorno = codigoretorno;
		this.retorno = retorno;
		this.codigoerro = codigoerro;
	}
	
	public TFeedbackPessoalWebService(Boolean sucesso, String mensagem, String exception, String codigoretorno) 
	{
		super();
		this.sucesso = sucesso;
		this.mensagem = mensagem;
		this.exception = exception;
		this.codigoretorno = codigoretorno;
	}

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
				logger.error("funcao: " + metodo);
				logger.error("parametros: " + paramToJson);
				logger.error("codigo retornado: " + codigoretorno);
				logger.error("codigo erro: " + codigoerro);
				logger.error("mensagem: " + mensagem);
				logger.error("exception: " + exception);				
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

	public String getCodigoretorno() {
		return codigoretorno;
	}

	public void setCodigoretorno(String codigoretorno) {
		this.codigoretorno = codigoretorno;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
	
	public String getCodigoerro()
	{
		return codigoerro;
	}
	
	public void setCodigoerro(String codigoerro)
	{
		this.codigoerro = codigoerro;
	}
}