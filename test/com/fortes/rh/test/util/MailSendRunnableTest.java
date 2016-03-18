package com.fortes.rh.test.util;

import javax.mail.Message;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.util.MailSendRunnable;

public class MailSendRunnableTest extends MockObjectTestCase
{
	MailSendRunnable mailSendRunnable;
	Mock simpleMailMessage;
	Mock javaMailSender;

	protected void setUp()
	{
		mailSendRunnable = new MailSendRunnable();
	}

	public void testPrepareMessage() throws Exception
	{
		ParametrosDoSistema params = ParametrosDoSistemaFactory.getEntity();
		params.setEmailSmtp("samuel@gmail.com");
		params.setEmailPort(587);
		
		Message msg = mailSendRunnable.prepareMessage("João", params, "Teste de email", "body", null, true, true);
		
		assertEquals("Teste de email", msg.getSubject());
	}

}