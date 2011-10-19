package com.fortes.rh.test.util;

import java.util.Collection;

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

	public void testGetModulos()
	{
		//utilizado para pega o id dos modulos do RH - REMPROT
		assertFalse(Autenticador.getModulosNaoConfigurados(1).contains(357L));
		assertFalse(Autenticador.getModulosNaoConfigurados(2).contains(361L));
		assertFalse(Autenticador.getModulosNaoConfigurados(4).contains(353L));
		assertFalse(Autenticador.getModulosNaoConfigurados(8).contains(365L));
		assertFalse(Autenticador.getModulosNaoConfigurados(16).contains(382L));
		assertFalse(Autenticador.getModulosNaoConfigurados(32).contains(75L));
		
		Collection<Long> ids = Autenticador.getModulosNaoConfigurados(3);
		assertFalse(ids.contains(357L));
		assertFalse(ids.contains(361L));
		
		ids = Autenticador.getModulosNaoConfigurados(5);
		assertFalse(ids.contains(357L));
		assertFalse(ids.contains(353L));
		
		ids = Autenticador.getModulosNaoConfigurados(44);
		assertFalse(ids.contains(353L));
		assertFalse(ids.contains(365L));
		assertFalse(ids.contains(75L));

		ids = Autenticador.getModulosNaoConfigurados(63);
		assertFalse(ids.contains(357L));
		assertFalse(ids.contains(361L));
		assertFalse(ids.contains(353L));
		assertFalse(ids.contains(365L));
		assertFalse(ids.contains(382L));
		assertFalse(ids.contains(75L));

		assertEquals(0, ids.size());
		
	}

}