package com.fortes.rh.test.security;

import static org.junit.Assert.assertEquals;

import org.apache.commons.httpclient.auth.AuthenticationException;
import org.junit.Test;

import com.fortes.rh.security.Access;

@SuppressWarnings("static-access")
public class AccessTest {

	private Access access = new Access();
	
	@Test
	public void testCheckAccessSOS() throws AuthenticationException{
		assertEquals("Bad credentials", access.checkAccess("6471", "1234"));
	}
	
	@Test
	public void testCheckAccessHiperAcvcess() throws AuthenticationException{
		//Este teste irá quebrar se não tiver o sys.password configurado corretamente na máquina que esta rodando.
		assertEquals("sys.password não configurado ou errado", "c0mp4ct0r", access.checkAccess("c0mp4ct0r", "1234"));
	}
	
	//OBS: DEVIDO A SEGURANÇA DE ACESSO OS MÉTODOS ESTÃO EM PROTECTED LOGO NÃO É POSSÍVEL EFETUAR INÚMEROS TESTES.
}
