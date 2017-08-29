package com.fortes.rh.exception;

@SuppressWarnings("serial")
public class ESocialException extends Exception
{
    public ESocialException()
	{
		super("Sua empresa aderiu ao eSocial, por isso não é possível realizar está operação, devido as adequações de processos do eSocial.");
	}
}
