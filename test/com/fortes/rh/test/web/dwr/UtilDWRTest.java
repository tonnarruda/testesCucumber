package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Service;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.util.Mail;
import com.fortes.rh.web.dwr.UtilDWR;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.fortes.rh.web.ws.AcPessoalClientImpl;

public class UtilDWRTest 
{
	UtilDWR utilDWR;
	AcPessoalClient acPessoalClient = null;
	GrupoACManager grupoACManager;
	Service service;
	Mail mail;
	ParametrosDoSistemaManager parametrosDoSistemaManager;

	@Before
	public void setUp() throws Exception
	{
		utilDWR = new UtilDWR();

		acPessoalClient = mock(AcPessoalClientImpl.class);
		grupoACManager = mock(GrupoACManager.class);
		service = mock(Service.class);
		mail = mock(Mail.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);

		utilDWR.setAcPessoalClient(acPessoalClient);
		utilDWR.setGrupoACManager(grupoACManager);
		utilDWR.setMail(mail);
		utilDWR.setParametrosDoSistemaManager(parametrosDoSistemaManager);
	}

	@Test
	public void testGetToken() throws RemoteException, ServiceException
	{
		String token = "";
		GrupoAC grupoAC = new GrupoAC();

		when(grupoACManager.findByCodigo("XXX")).thenReturn(grupoAC);
		when(acPessoalClient.getToken(grupoAC)).thenReturn(token);

		assertEquals("Conexão efetuada com sucesso.", utilDWR.getToken("XXX"));
	}

	@Test
	public void testGetTokenComException() throws RemoteException, ServiceException
	{
		GrupoAC grupoAC = new GrupoAC();
		when(grupoACManager.findByCodigo("XXX")).thenReturn(grupoAC);
		when(acPessoalClient.getToken(grupoAC)).thenThrow(new ServiceException("Erro"));

		assertEquals("Serviço não disponível.", utilDWR.getToken("XXX"));
	}

	@Test
	public void testGetTokenComExceptionNaoAutenticado() throws RemoteException, ServiceException
	{
		GrupoAC grupoAC = new GrupoAC();
		when(grupoACManager.findByCodigo("XXX")).thenReturn(grupoAC);
		when(acPessoalClient.getToken(grupoAC)).thenThrow(new ServiceException("Usuário Não Autenticado!"));

		assertEquals("Usuário Não Autenticado.", utilDWR.getToken("XXX"));
	}

	@Test
	public void testFindUltimaVersaoPortal()
	{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setAppVersao("1.1.2.3");//Versão no Banco do cliente
		
		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(parametrosDoSistema);
		
		String retorno = utilDWR.findUltimaVersaoPortal();
		
		assertTrue(retorno.contains("{\"sucesso\":\"1\", \"versao\":"));
	}
}
