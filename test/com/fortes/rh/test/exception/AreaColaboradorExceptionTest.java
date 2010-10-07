package com.fortes.rh.test.exception;

import junit.framework.TestCase;

import com.fortes.rh.exception.AreaColaboradorException;

public class AreaColaboradorExceptionTest extends TestCase
{
	AreaColaboradorException exception;

	public void testColecaoVaziaException(){
		exception = new AreaColaboradorException("erro");
		assertEquals("erro", exception.getMessage());
	}
}