package com.fortes.rh.exception;

public class SenhaNaoConfereException extends Exception
{
	public SenhaNaoConfereException()
	{
		super("Senhas não correspondem.");
	}
}
