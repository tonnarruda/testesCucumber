package com.fortes.rh.exception;

public class NotRegistredException extends Exception
{
	public NotRegistredException()
	{
		super("Este sistema não está licenciado para uso ou ocorreu algum erro com o serviço de autenticação.");
	}
}
