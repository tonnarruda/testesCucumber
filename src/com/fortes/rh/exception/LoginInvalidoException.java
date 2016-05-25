package com.fortes.rh.exception;

@SuppressWarnings("serial")
public class LoginInvalidoException extends Exception
{
	private String retorno;
	
	public LoginInvalidoException(String msg, String retorno)
	{
		super(msg);
		setRetorno(retorno);
	}

	public String getRetorno()
	{
		return retorno;
	}

	public void setRetorno(String retorno)
	{
		this.retorno = retorno;
	}
}
