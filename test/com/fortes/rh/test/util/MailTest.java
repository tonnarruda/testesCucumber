package com.fortes.rh.test.util;

import java.io.File;

import javax.mail.Transport;

import mockit.Mockit;

import org.apache.commons.lang.SystemUtils;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockTransport;
import com.fortes.rh.util.Mail;

public class MailTest extends MockObjectTestCase
{
	public Mail mail;

	public Mock parametrosDoSistemaManager;

	protected void setUp()
	{
		mail = new Mail();

		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		mail.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());

		Mockit.redefineMethods(Transport.class, MockTransport.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown()
	{
    	MockSecurityUtil.verifyRole = false;
		Mockit.restoreOriginalDefinition(SecurityUtil.class);
	}

	public void testSend() throws Exception
	{
		
		if (!SystemUtils.IS_OS_WINDOWS)
			return;

		ParametrosDoSistema param = new ParametrosDoSistema();
		param.setEmailPass("x@x.com");
		param.setEmailPort(1);
		param.setEmailSmtp("smtp.x.com.br");
		param.setEmailUser("x");
		param.setEnviarEmail(true);

		Empresa empresa = new Empresa();
		empresa.setEmailRemetente("x@x.com");

		parametrosDoSistemaManager.expects(atLeastOnce()).method("findById").with(eq(1L)).will(returnValue(param));

		mail.send(empresa, "", "corpo", new File[] {}, new String[] { "rodrigomaia@grupofortes.com.br" });
		//Teste para empresa null
		mail.send(null, "", "corpo", new File[] {}, new String[] { "rodrigomaia@grupofortes.com.br" });
	}
	
	public void testSendComAnexo() throws Exception
	{
		
		if (!SystemUtils.IS_OS_WINDOWS)
			return;

		ParametrosDoSistema param = new ParametrosDoSistema();
		param.setEmailPass("x@x.com");
		param.setEmailPort(1);
		param.setEmailSmtp("smtp.x.com.br");
		param.setEmailUser("x");
		param.setEnviarEmail(true);

		Empresa empresa = new Empresa();
		empresa.setEmailRemetente("x@x.com");

		parametrosDoSistemaManager.expects(atLeastOnce()).method("findById").with(eq(1L)).will(returnValue(param));

		mail.send(empresa, "", "corpo", new File[] {}, "rodrigomaia@grupofortes.com.br");

		param.setEmailPass("");
		param.setEmailPort(1);
		param.setEmailSmtp("smtp.x.com.br");
		param.setEmailUser("");

		File file = new File("/teste.txt");
		file.deleteOnExit();

		mail.send(empresa, "", "corpo", new File[] { file }, "rodrigomaia@grupofortes.com.br");
	}

}
