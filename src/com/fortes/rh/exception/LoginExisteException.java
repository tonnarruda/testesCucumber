package com.fortes.rh.exception;

public class LoginExisteException extends Exception
{
	public LoginExisteException()
	{
		super("Este login já existe.");
	}
}
