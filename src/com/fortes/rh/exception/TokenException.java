package com.fortes.rh.exception;

@SuppressWarnings("serial")
public class TokenException extends Exception
{
	public TokenException()
	{
		super("Token Incorreto.");
	}
	
	public TokenException(String msg)
	{
		super(msg);
	}
}
