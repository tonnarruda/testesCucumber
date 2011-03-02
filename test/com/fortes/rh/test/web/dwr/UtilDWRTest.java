package com.fortes.rh.test.web.dwr;

import javax.mail.internet.AddressException;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Service;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.Mail;
import com.fortes.rh.web.dwr.UtilDWR;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.fortes.rh.web.ws.AcPessoalClientImpl;

public class UtilDWRTest extends MockObjectTestCase
{
	UtilDWR utilDWR;
	Mock acPessoalClient = null;
	Mock empresaManager;
	Mock service;
	Mock mail;

	protected void setUp() throws Exception
	{
		super.setUp();
		utilDWR = new UtilDWR();

		acPessoalClient = mock(AcPessoalClientImpl.class);
		empresaManager = new Mock(EmpresaManager.class);
		service = mock(Service.class);
		mail = mock(Mail.class);

		utilDWR.setAcPessoalClient((AcPessoalClient) acPessoalClient.proxy());
		utilDWR.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		utilDWR.setMail((Mail) mail.proxy());
//		utilDWR.setService((Service) service.proxy());
	}

	public void testGetToken()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("0003");

		empresaManager.expects(once()).method("findByCodigoAC").with(ANYTHING, ANYTHING).will(returnValue(empresa));

		String token = "";

		acPessoalClient.expects(once()).method("getToken").with(ANYTHING).will(returnValue(token));

		String retorno = utilDWR.getToken("", "", "", "", null);
	}

	public void testGetTokenComAcSenha()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("0003");
		empresa.setAcUsuario("Admin");
		empresa.setAcSenha("senha");

		String token = "";

		acPessoalClient.expects(once()).method("getToken").with(ANYTHING).will(returnValue(token));

		String retorno = utilDWR.getToken("Admin", "senha", "", "", null);

		assertNotNull(retorno);
	}

	public void testGetTokenComException()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("0003");
		empresa.setAcUsuario("Admin");
		empresa.setAcSenha("senha");

		acPessoalClient.expects(once()).method("getToken").with(ANYTHING).will(throwException(new ServiceException("Erro")));

		String retorno = utilDWR.getToken("Admin", "senha", "", "", null);

		assertNotNull(retorno);
	}

	public void testGetTokenComExceptionNaoAutenticado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("0003");
		empresa.setAcUsuario("Admin");
		empresa.setAcSenha("senha");

		acPessoalClient.expects(once()).method("getToken").with(ANYTHING).will(throwException(new ServiceException("Usuário Não Autenticado!")));

		String retorno = utilDWR.getToken("Admin", "senha", "", "", null);

		assertNotNull(retorno);
	}

	public void testEnviaEmail()
	{
		String email="teste@teste.com";

		mail.expects(once()).method("send").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING});

		String retorno = utilDWR.enviaEmail(email);

		assertNotNull(retorno);
	}

	public void testEnviaEmailComException()
	{
		String email="teste@teste.com";

		mail.expects(once()).method("send").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(throwException(new AddressException("Erro.")));

		String retorno = utilDWR.enviaEmail(email);

		assertNotNull(retorno);
	}

	public void testEnviaEmailComExceptionSemMensagem()
	{
		String email="teste@teste.com";

		mail.expects(once()).method("send").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(throwException(new AddressException(null)));

		String retorno = utilDWR.enviaEmail(email);

		assertNotNull(retorno);
	}
}
