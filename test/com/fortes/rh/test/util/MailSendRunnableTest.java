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
	
	//Teste não funfa pq mailtrap não é mais free
//	public void testExecutar() throws Exception
	{
		ParametrosDoSistema params = ParametrosDoSistemaFactory.getEntity();
		params.setEmailSmtp("mailtrap.io");
		params.setEmailPort(465);
		params.setEmailUser("284638900eb69f13d");
		params.setEmailPass("c464f07aac55db");
		params.setAutenticacao(true);
		params.setTls(true);

		mailSendRunnable = new MailSendRunnable(params,"junitremetente@gmail.com", "Teste de email", "body", null,"junitdestinatario@gmail.com");
		
		Exception e = null;
		try {
			mailSendRunnable.executar();
		} catch (Exception ex) {
			e = ex;
		}
		
		assertNull(e);
	}
}