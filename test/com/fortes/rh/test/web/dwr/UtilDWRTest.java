package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.rmi.RemoteException;

import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import mockit.Mockit;

import org.apache.axis.client.Service;
import org.junit.Before;
import org.junit.Test;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

import com.fortes.rh.business.geral.CartaoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.CartaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
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
	CartaoManager cartaoManager;

	@Before
	public void setUp() throws Exception
	{
		utilDWR = new UtilDWR();

		acPessoalClient = mock(AcPessoalClientImpl.class);
		grupoACManager = mock(GrupoACManager.class);
		service = mock(Service.class);
		mail = mock(Mail.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		cartaoManager = mock(CartaoManager.class);
		
		utilDWR.setAcPessoalClient(acPessoalClient);
		utilDWR.setGrupoACManager(grupoACManager);
		utilDWR.setMail(mail);
		utilDWR.setParametrosDoSistemaManager(parametrosDoSistemaManager);
		utilDWR.setCartaoManager(cartaoManager);
		
		//Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
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
	
	@Test
	public void testEnviaEmailCartaoBoasVindasException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		when(cartaoManager.findByEmpresaIdAndTipo(empresa.getId(), TipoCartao.BOAS_VINDAS)).thenReturn(null);
		
		String retorno = utilDWR.enviaEmailCartaoBoasVindas("email", empresa.getId(), empresa.getNome(), empresa.getEmailRemetente());
		assertEquals("Erro desconhecido, entre em contato com o suporte.",retorno);
	}
}
