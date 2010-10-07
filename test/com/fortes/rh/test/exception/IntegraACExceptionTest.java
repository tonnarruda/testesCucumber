package com.fortes.rh.test.exception;

import junit.framework.TestCase;

import com.fortes.rh.exception.IntegraACException;

public class IntegraACExceptionTest extends TestCase
{
	IntegraACException exception;

	public void testColecaoVaziaException(){
		exception = new IntegraACException("erro");
		assertEquals("erro", exception.getMessage());
	}
}