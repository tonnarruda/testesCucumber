package com.fortes.rh.test.util;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.fortes.rh.util.AuthenticatorImpl;

public class AuthenticatorImplTest extends TestCase
{
	AuthenticatorImpl authenticatorImpl;

	protected void setUp(){
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		authenticatorImpl = new AuthenticatorImpl(senderImpl);
	}

	public void testGetPasswordAuthentication() throws Exception{

		Method metodo = authenticatorImpl.getClass().getDeclaredMethod("getPasswordAuthentication", new Class[]{});

		metodo.setAccessible(true);
		metodo.invoke(authenticatorImpl, new Object[]{});
	}

}