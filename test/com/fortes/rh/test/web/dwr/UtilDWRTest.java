package com.fortes.rh.test.web.dwr;

import javax.mail.internet.AddressException;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Service;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.util.Mail;
import com.fortes.rh.web.dwr.UtilDWR;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.fortes.rh.web.ws.AcPessoalClientImpl;

public class UtilDWRTest extends MockObjectTestCase
{
	UtilDWR utilDWR;
	Mock acPessoalClient = null;
	Mock grupoACManager;
	Mock service;
	Mock mail;

	protected void setUp() throws Exception
	{
		super.setUp();
		utilDWR = new UtilDWR();

		acPessoalClient = mock(AcPessoalClientImpl.class);
		grupoACManager = new Mock(GrupoACManager.class);
		service = mock(Service.class);
		mail = mock(Mail.class);

		utilDWR.setAcPessoalClient((AcPessoalClient) acPessoalClient.proxy());
		utilDWR.setGrupoACManager((GrupoACManager) grupoACManager.proxy());
		utilDWR.setMail((Mail) mail.proxy());
//		utilDWR.setService((Service) service.proxy());
	}

	public void testGetToken()
	{
		GrupoAC grupoAC = new GrupoAC();
		grupoACManager.expects(once()).method("findByCodigo").with(ANYTHING).will(returnValue(grupoAC));

		String token = "";

		acPessoalClient.expects(once()).method("getToken").with(ANYTHING).will(returnValue(token));

		String retorno = utilDWR.getToken("XXX");
	}

	public void testGetTokenComAcSenha()
	{
		String token = "";

		GrupoAC grupoAC = new GrupoAC();
		grupoACManager.expects(once()).method("findByCodigo").with(ANYTHING).will(returnValue(grupoAC));
		
		acPessoalClient.expects(once()).method("getToken").with(ANYTHING).will(returnValue(token));

		String retorno = utilDWR.getToken("XXX");

		assertNotNull(retorno);
	}

	public void testGetTokenComException()
	{
		GrupoAC grupoAC = new GrupoAC();
		grupoACManager.expects(once()).method("findByCodigo").with(ANYTHING).will(returnValue(grupoAC));
		
		acPessoalClient.expects(once()).method("getToken").with(ANYTHING).will(throwException(new ServiceException("Erro")));

		String retorno = utilDWR.getToken("XXX");

		assertNotNull(retorno);
	}

	public void testGetTokenComExceptionNaoAutenticado()
	{
		GrupoAC grupoAC = new GrupoAC();
		grupoACManager.expects(once()).method("findByCodigo").with(ANYTHING).will(returnValue(grupoAC));
		
		acPessoalClient.expects(once()).method("getToken").with(ANYTHING).will(throwException(new ServiceException("Usuário Não Autenticado!")));

		String retorno = utilDWR.getToken("XXX");

		assertNotNull(retorno);
	}

	public void testEnviaEmail() throws Exception
	{
		String email="teste@teste.com";

		mail.expects(once()).method("testEnvio").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING});

		String retorno = utilDWR.enviaEmail(email);

		assertNotNull(retorno);
	}

	public void testEnviaEmailComExceptionSemMensagem() throws Exception
	{
		String email="teste@teste.com";

		mail.expects(once()).method("testEnvio").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING}).will(throwException(new AddressException(null)));

		String retorno = utilDWR.enviaEmail(email);

		assertNotNull(retorno);
	}
}
