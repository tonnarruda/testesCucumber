package com.fortes.rh.exception;

public class NotConectAutenticationException extends Exception
{
	public NotConectAutenticationException()
	{
		super("Não foi possível se conectar ao serviço de autenticação.");
	}
}
