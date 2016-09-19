package com.fortes.rh.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.fortes.rh.model.geral.ParametrosDoSistema;

public class MailSendRunnable implements Runnable {

	ParametrosDoSistema parametros;
	String from;
	String subject;
	String body;
	DataSource[] attachedFiles;
	String[] to;
	
	private JavaMailSenderImpl mailSender;
	private SimpleMailMessage message;
	
	public MailSendRunnable(){}
	
	public MailSendRunnable(ParametrosDoSistema parametros, String from, String subject, String body, DataSource[] attachedFiles, String... to)
	{
		this.parametros= parametros;
		this.from= from;
		this.subject = subject;
		this.body = body;
		this.attachedFiles = attachedFiles;
		this.to = to;
	}
	
	public void run() {
		try {
			executar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public void executar() throws Exception, MessagingException 
    {
		Message msg = prepareMessage(from, parametros, subject, body, attachedFiles,  parametros.isAutenticacao(), parametros.isTls());

		List<String> emails = new ArrayList<String>();

		for (int i = 0; i < to.length; i++)
			if (to[i] != null && !to[i].trim().equals(""))
				emails.add(to[i].trim());

		Address[] address = new Address[emails.size()];

		for (int i = 0; i < emails.size(); i++)
			address[i] = new InternetAddress(emails.get(i));

		if (emails.size() > 0)
		{
			msg.setRecipients(RecipientType.TO, address);
			Transport.send(msg);
		}
    }

	@SuppressWarnings("deprecation")
	public Message prepareMessage(String from, ParametrosDoSistema params, String subject, String body, DataSource[] attachedFiles, Boolean autenticacao, Boolean tls) throws MessagingException, AddressException, UnsupportedEncodingException
	{
		Session session;

		BodyMessage bodyMessage = new BodyMessage(body);

		message = (SimpleMailMessage) SpringUtil.getBeanOld("mailMessage");
		mailSender = (JavaMailSenderImpl) SpringUtil.getBeanOld("mailSender");
		
		message.setFrom(from);

        mailSender.setHost(params.getEmailSmtp());
        mailSender.setPort(params.getEmailPort());
        mailSender.setUsername(params.getEmailUser());
        mailSender.setPassword(params.getEmailPass());

    	Properties properties = new Properties();
        properties.put("mail.smtp.host", mailSender.getHost());
        properties.put("mail.smtp.port", mailSender.getPort() + "");
        properties.put("mail.smtp.sendpartial", "true");
        
        properties.put("mail.smtp.starttls.enable",tls.toString());
		properties.put("mail.smtp.auth", autenticacao.toString());
    	if (autenticacao)
    	{
    		session = Session.getInstance(properties, new AuthenticatorImpl(mailSender));
    	}
    	else
    	{
    		session = Session.getInstance(properties);
    	}
    	MimeMessage msg = new MimeMessage(session);

        Multipart mimesContainer = new MimeMultipart();
        MimeBodyPart text = new MimeBodyPart();
        			// Original (10:48 2/1/2008) text.setContent(body, "text/html");
        text.setContent(bodyMessage.toString(), "text/html; charset=UTF-8");
        text.setHeader("Content-Type", "text/html; charset=UTF-8");
        
        msg.setFrom(new InternetAddress(message.getFrom(), "RH"));
        msg.setSubject(subject, "utf-8");

        			//Original (10:48 2/1/2008) msg.setText(body);
        msg.setText(bodyMessage.toString());

        mimesContainer.addBodyPart(text);

        if (attachedFiles != null)
        {
	    	for (DataSource file : attachedFiles)
	    	{
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setFileName(file.getName());
				mimesContainer.addBodyPart(mimeBodyPart);
				mimeBodyPart.setDataHandler(new DataHandler(file));
			}
        }

    	msg.setContent(mimesContainer);
		return msg;
	}
	
	private class BodyMessage
	{
		private final String header = initHeader();
		private final String body = initBody();
		private final String footer = initFooter();
		private String content;

		BodyMessage(String content)
		{
			this.content = content;
		}

		private String initHeader()
		{
			StringBuilder header = new StringBuilder();
			header.append("<html><head>");
				header.append("<style type='text/css'>");
				header.append("<!--");
				header.append("*{");
					header.append("font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;");
					header.append("font-size: 11px;");
					header.append("font-weight: normal;");
					header.append("list-style-type: none;");
					header.append("margin: 0;");
					header.append("padding: 0;");
					header.append("text-decoration: none;");
				header.append("}");
				header.append("a img{ border: 0; }");
				header.append("body,td,th { font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 10px; color: #666666; }");
				header.append(".corpo{ padding: 40px 20px; height: 100px; background:#FFF; }");
				header.append(".table{ border: 0px solid #FFF; width: 570px; background-color: #FFF; }");
				header.append("table{ background-color: #FFF; }");
				header.append("-->");
				header.append("</style>");
			header.append("</head><body>");
			header.append("<table cellpadding='0' cellspacing='0' border=0 style='width: 100%; background-color: #DDDEE6;'>");
				header.append("<tr>");
				header.append("<td align='center'>");
					header.append("<table cellpadding='0' cellspacing='0' align='center' class='table'>");
					header.append("<thead><tr>");
					header.append("<td colspan='2' bgcolor='#FFFFFF'><a href='http://www.fortestecnologia.com.br'><img src='http://www.fortesinformatica.com.br/fortesrh/images/topo_fortesrh.gif' /></a></td></tr></thead>");

			return header.toString();
		}
		private String initBody()
		{
			StringBuilder body = new StringBuilder();
			body.append("<tbody>");
			body.append("<tr><td class='corpo' bgcolor='#FFFFFF'><div style='width:550px; margin:0px 10px'>");

			return body.toString();
		}
		private String initFooter()
		{
			StringBuilder footer = new StringBuilder();
							footer.append("</div></td></tr>");
						footer.append("</tbody>");
							footer.append("<tfoot>");
								footer.append("<tr>");
									footer.append("<td>");
										footer.append("<a href='http://www.fortestecnologia.com.br'><img src='http://www.fortesinformatica.com.br/fortesrh/images/assinatura.gif' /></a>");
									footer.append("</td>");
								footer.append("</tr>");
							footer.append("</tfoot>");
							footer.append("</table>");
						footer.append("</td>");
					footer.append("</tr>");
				footer.append("</table>");
			footer.append("</body>");
			footer.append("</html>");

			return footer.toString();
		}

		@Override
		public String toString()
		{
			return header + body + content + footer;
		}
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setMessage(SimpleMailMessage message) {
		this.message = message;
	}
}