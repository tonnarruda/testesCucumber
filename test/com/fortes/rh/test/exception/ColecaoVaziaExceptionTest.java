package com.fortes.rh.test.exception;

import junit.framework.TestCase;

import com.fortes.rh.exception.ColecaoVaziaException;

public class ColecaoVaziaExceptionTest extends TestCase
{
	ColecaoVaziaException exception;

	public void testColecaoVaziaException(){
		exception = new ColecaoVaziaException("erro");
		assertEquals("erro", exception.getMessage());
	}
}