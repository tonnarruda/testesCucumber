package com.fortes.rh.test.util.mockObjects;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.fortes.rh.model.geral.Empresa;

public class MockMail
{
	public void send(Empresa empresa, String subject, String body, File[] attachedFiles, String... to) throws AddressException, MessagingException
	{
		System.out.println("Envio de email mockado!");
	}
}
