package com.fortes.rh.util;

import javax.mail.PasswordAuthentication;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class AuthenticatorImpl extends javax.mail.Authenticator
{

	private JavaMailSenderImpl mailSender;

	public AuthenticatorImpl(JavaMailSenderImpl mailSender){
		this.mailSender = mailSender;
	}

	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(mailSender.getUsername(), mailSender.getPassword());
	}
}
