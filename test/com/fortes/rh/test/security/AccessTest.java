package com.fortes.rh.test.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fortes.rh.security.Access;

public class AccessTest {

	//Estes testes irão quebrar se não tiver o sys.password configurado corretamente na máquina que esta rodando.
	
	@Test
	public void testCheckAccessSOS() throws Exception{
		assertEquals("Bad credentials", Access.check("6471", "1234"));
	}
	
	@Test
	public void testCheckAccessHiperAccess() throws Exception{
		assertEquals("sys.password não configurado ou errado", "c0mp4ct0r", Access.check("c0mp4ct0r", "1234"));
	}
	
	//OBS: DEVIDO A SEGURANÇA DE ACESSO OS MÉTODOS ESTÃO EM PROTECTED LOGO NÃO É POSSÍVEL EFETUAR INÚMEROS TESTES.
}
