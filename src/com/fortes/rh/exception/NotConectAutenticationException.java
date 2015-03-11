package com.fortes.rh.exception;

@SuppressWarnings("serial")
public class NotConectAutenticationException extends Exception
{
	public NotConectAutenticationException()
	{
		super("Não foi possível se conectar ao serviço de autenticação.<br/> Favor entre em contato com o suporte.");
	}
	
	public NotConectAutenticationException(String msg)
	{
		super(msg);
	}
}
