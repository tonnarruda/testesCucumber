package com.fortes.rh.exception;

public class ColecaoVaziaException extends Exception
{
	private static final long serialVersionUID = 6311475691521625228L;

	public ColecaoVaziaException() {
		this("Não existem dados para o filtro informado.");
	}
	
	public ColecaoVaziaException(String msg)
	{
		super(msg);
	}
}
