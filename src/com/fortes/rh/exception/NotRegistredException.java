package com.fortes.rh.exception;

@SuppressWarnings("serial")
public class NotRegistredException extends Exception
{
	public NotRegistredException()
	{
		super("Este sistema não está licenciado para uso ou ocorreu algum erro com o serviço de autenticação.");
	}
	
	public NotRegistredException(String msg)
	{
		super(msg);
	}
}
