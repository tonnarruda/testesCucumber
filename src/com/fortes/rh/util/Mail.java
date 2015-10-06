package com.fortes.rh.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public class Mail
{
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

    public void send(Empresa empresa, String subject, DataSource[] attachedFiles, String body, String... to) throws AddressException, MessagingException
    {
    	ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
    	procSend(empresa, parametros, subject, body, attachedFiles, true, to);
    }
    
    public void send(Empresa empresa, String subject, String body, File[] attachedFiles, String... to) throws AddressException, MessagingException
    {
    	ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
    	DataSource[] dsArray = null;
    	
    	if(attachedFiles != null)
    	{
	    	dsArray = new DataSource[attachedFiles.length];
	    	for (int i = 0; i < attachedFiles.length; i++) 
	    	{
				dsArray[i] = new FileDataSource(attachedFiles[i]);
			}
    	}
    	
    	procSend(empresa, parametros, subject, body, dsArray, true, to);
    }

    public void send(Empresa empresa, ParametrosDoSistema parametros, String subject, String body, boolean utilizarThread, String... to) throws AddressException, MessagingException
    {
    	procSend(empresa, parametros, subject, body, null, utilizarThread, to);
    }
    
    public void send(String from, String subject, String body, String... to) throws AddressException, MessagingException
    {
    	ParametrosDoSistema parametros = parametrosDoSistemaManager.findById(1L);
    	procSend(parametros, from, subject, body, null, true, to);
    }

    private void procSend(Empresa empresa, ParametrosDoSistema parametros, String subject, String body, DataSource[] attachedFiles, boolean utilizarThread, String... to) throws AddressException, MessagingException
    {
    	procSend(parametros, getRemetente(empresa, parametros), subject, body, attachedFiles, utilizarThread, to);
    }

	private String getRemetente(Empresa empresa, ParametrosDoSistema parametros) 
	{
		String from;
    	
    	if(empresa != null && empresa.getEmailRemetente() != null)
		{
			from = empresa.getEmailRemetente();
		}
		else if(parametros.getEmailRemetente() != null && !StringUtil.isBlank(parametros.getEmailRemetente()))
		{
			from = parametros.getEmailRemetente();
		} 
		else 
		{
			from = "fortesrh@grupofortes.com.br";
		}
    	
		return from;
	}
    
    private void procSend(ParametrosDoSistema parametros, String from, String subject, String body, DataSource[] attachedFiles, boolean utilizarThread, String... to) throws AddressException, AddressException, MessagingException
    {
    	if(parametros.isEnvioDeEmailHabilitado())
    	{
			if (to == null || to.length == 0)
				throw new AddressException("Destinatários não informados.");
			
			MailSendRunnable mailSendRunnable = new MailSendRunnable(parametros, from, subject, body, attachedFiles, to);
			
			if(utilizarThread){
				Thread threadSendEmail = new Thread(mailSendRunnable);
				threadSendEmail.start();
			}else{
				try {
					mailSendRunnable.executar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
    	}
    }

	//utilizado pelo DWR, teste do email em configurações
	public void testEnvio(Empresa empresa, String subject, String body, String email, boolean autenticacao, boolean tls) throws Exception 
	{
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findByIdProjection(1L);
		
		if(!parametros.isEnvioDeEmailHabilitado())
			throw new Exception("Envio de Email desabilitado, entre em contato com o suporte.");
		
		MailSendRunnable mailSendRunnable = new MailSendRunnable();
		Message msg = mailSendRunnable.prepareMessage(getRemetente(empresa, parametros), parametros, subject, body, null,  autenticacao, tls);
		List<String> emails = new ArrayList<String>();

		emails.add(email);

		Address[] address = new Address[emails.size()];

		for (int i = 0; i < emails.size(); i++)
			address[i] = new InternetAddress(emails.get(i));

		if (emails.size() > 0)
		{
			msg.setRecipients(RecipientType.TO, address);
			Transport.send(msg);
		}
	}
}