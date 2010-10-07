package com.fortes.rh.test.util.mockObjects;

/**
 * Classe criada para auxiliar a Mockagem estática de métodos de Transport da API JavaMail
 */
public class MockTransport
{
	public static void send(javax.mail.Message message) throws javax.mail.MessagingException{
		System.out.println("envia o email");
	}
}
