package com.fortes.rh.test.exception;

import junit.framework.TestCase;

import com.fortes.rh.exception.FaixaJaCadastradaException;

public class FaixaJaCadastradaExceptionTest extends TestCase
{
	FaixaJaCadastradaException exception;

	public void testColecaoVaziaException(){
		exception = new FaixaJaCadastradaException("erro");
		assertEquals("erro", exception.getMessage());
	}
}