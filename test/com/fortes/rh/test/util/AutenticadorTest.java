package com.fortes.rh.test.util;

import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.security.licenca.AutenticadorJarvis;

public class AutenticadorTest extends TestCase
{

	protected void setUp(){
		AutenticadorJarvis autenticador = new AutenticadorJarvis();
	}

	/* O teste foi simplicado e apenas cobre as linhas do código, já que trata-se de uma classe externa
	 * e não foi possível mocka-la, nem mesmo uma refatoração foi possível, já que ninguem chama explicitamente
	 * o método.
	*/
	public void testGetQtdCadastrosVersaoDemo(){
		assertEquals(10, AutenticadorJarvis.getQtdCadastrosVersaoDemo());
	}

	public void testGetModulos()
	{
		//utilizado para pega o id dos modulos do RH - REMPROT
		assertFalse(AutenticadorJarvis.getModulosNaoConfigurados(1).contains(357L));
		assertFalse(AutenticadorJarvis.getModulosNaoConfigurados(2).contains(361L));
		assertFalse(AutenticadorJarvis.getModulosNaoConfigurados(4).contains(353L));
		assertFalse(AutenticadorJarvis.getModulosNaoConfigurados(8).contains(365L));
		assertFalse(AutenticadorJarvis.getModulosNaoConfigurados(16).contains(382L));
		assertFalse(AutenticadorJarvis.getModulosNaoConfigurados(32).contains(75L));
		
		Collection<Long> ids = AutenticadorJarvis.getModulosNaoConfigurados(3);
		assertFalse(ids.contains(357L));
		assertFalse(ids.contains(361L));
		
		ids = AutenticadorJarvis.getModulosNaoConfigurados(5);
		assertFalse(ids.contains(357L));
		assertFalse(ids.contains(353L));
		
		ids = AutenticadorJarvis.getModulosNaoConfigurados(44);
		assertFalse(ids.contains(353L));
		assertFalse(ids.contains(365L));
		assertFalse(ids.contains(75L));

		ids = AutenticadorJarvis.getModulosNaoConfigurados(63);
		assertFalse(ids.contains(357L));
		assertFalse(ids.contains(361L));
		assertFalse(ids.contains(353L));
		assertFalse(ids.contains(365L));
		assertFalse(ids.contains(382L));
		assertFalse(ids.contains(75L));

		assertEquals(0, ids.size());
		
	}

}