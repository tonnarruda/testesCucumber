package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.Autenticador;

public class AutenticadorTest extends TestCase
{

	protected void setUp(){
		Autenticador autenticador = new Autenticador();
	}

	/* O teste foi simplicado e apenas cobre as linhas do código, já que trata-se de uma classe externa
	 * e não foi possível mocka-la, nem mesmo uma refatoração foi possível, já que ninguem chama explicitamente
	 * o método.
	*/
	public void testGetQtdCadastrosVersaoDemo(){
		assertEquals(10, Autenticador.getQtdCadastrosVersaoDemo());
	}

}