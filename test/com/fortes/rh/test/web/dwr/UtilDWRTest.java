package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

import com.fortes.rh.business.geral.CartaoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.util.Mail;
import com.fortes.rh.web.dwr.UtilDWR;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.fortes.rh.web.ws.AcPessoalClientImpl;
import com.sun.mail.smtp.SMTPAddressFailedException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WebContextFactory.class, SecurityUtil.class })
public class UtilDWRTest {
	UtilDWR utilDWR;
	AcPessoalClient acPessoalClient = null;
	GrupoACManager grupoACManager;
	Service service;
	Mail mail;
	ParametrosDoSistemaManager parametrosDoSistemaManager;
	CartaoManager cartaoManager;
	MockHttpServletRequest request = new MockHttpServletRequest();

	@Before
	public void setUp() throws Exception {
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

		PowerMockito.mockStatic(SecurityUtil.class);
		PowerMockito.mockStatic(WebContextFactory.class);

	}

	@Test
	public void testGetToken() throws RemoteException, ServiceException {
		String token = "";
		GrupoAC grupoAC = new GrupoAC();

		when(grupoACManager.findByCodigo("XXX")).thenReturn(grupoAC);
		when(acPessoalClient.getToken(grupoAC)).thenReturn(token);

		assertEquals("Conexão efetuada com sucesso.", utilDWR.getToken("XXX"));
	}

	@Test
	public void testGetTokenComException() throws RemoteException, ServiceException {
		GrupoAC grupoAC = new GrupoAC();
		when(grupoACManager.findByCodigo("XXX")).thenReturn(grupoAC);
		when(acPessoalClient.getToken(grupoAC)).thenThrow(new ServiceException("Erro"));

		assertEquals("Serviço não disponível.", utilDWR.getToken("XXX"));
	}

	@Test
	public void testGetTokenComExceptionNaoAutenticado() throws RemoteException, ServiceException {
		GrupoAC grupoAC = new GrupoAC();
		when(grupoACManager.findByCodigo("XXX")).thenReturn(grupoAC);
		when(acPessoalClient.getToken(grupoAC)).thenThrow(new ServiceException("Usuário Não Autenticado!"));

		assertEquals("Usuário Não Autenticado.", utilDWR.getToken("XXX"));
	}

	@Test
	public void testFindUltimaVersaoPortal() {
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setAppVersao("1.1.2.3");// Versão no Banco do
													// cliente

		when(parametrosDoSistemaManager.findByIdProjection(1L)).thenReturn(parametrosDoSistema);

		String retorno = utilDWR.findUltimaVersaoPortal();

		assertTrue(retorno.contains("{\"sucesso\":\"1\", \"versao\":"));
	}

	@Test
	public void testEnviaEmailCartaoBoasVindasException() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		when(cartaoManager.findByEmpresaIdAndTipo(empresa.getId(), TipoCartao.BOAS_VINDAS)).thenReturn(null);

		String retorno = utilDWR.enviaEmailCartaoBoasVindas("email", empresa.getId(), empresa.getNome(), empresa.getEmailRemetente());
		assertEquals("Erro desconhecido, entre em contato com o suporte.", retorno);
	}

	@Test
	public void testEnviaEmailConfiguracaoSMTPException() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		String msgError = "550";
		String email = "teste@mail.com";

		when(WebContextFactory.get()).thenReturn(mock(WebContext.class));
		when(WebContextFactory.get().getHttpServletRequest()).thenReturn(mock(HttpServletRequest.class));
		when(WebContextFactory.get().getHttpServletRequest().getSession()).thenReturn(null);
		when(SecurityUtil.getEmpresaByDWR(null)).thenReturn(empresa);

		MessagingException messagingException = new MessagingException("1", new SMTPAddressFailedException(null, "", 0, msgError));

		doThrow(messagingException).when(mail).testEnvio(empresa, "Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, true, false);

		String msgRetorno = utilDWR.enviaEmail(email, true, false);
		assertEquals(getSMTPAddressFailedExceptionMessageQuandoAlgumaRestricaoDoServidorSMTPForViolada(), msgRetorno);
	}


	@Test
	public void testEnviaEmailConfiguracaoSMTPExceptionQuandoAlgumCampoEstiverMarcadoDeFormaIndevida() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		String email = "teste@mail.com";

		when(WebContextFactory.get()).thenReturn(mock(WebContext.class));
		when(WebContextFactory.get().getHttpServletRequest()).thenReturn(mock(HttpServletRequest.class));
		when(WebContextFactory.get().getHttpServletRequest().getSession()).thenReturn(null);
		when(SecurityUtil.getEmpresaByDWR(null)).thenReturn(empresa);

		MessagingException messagingException = new MessagingException("teste");

		doThrow(messagingException).when(mail).testEnvio(empresa, "Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, true, false);

		String msgRetorno = utilDWR.enviaEmail(email, true, false);
		assertEquals(getMessageExceptionMessageQuandoAlgumCampoEstiverMarcadoDeFormaIndevida(), msgRetorno);
	}

	@Test
	public void testEnviaEmailComSucesso() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		String email = "teste@mail.com";

		when(WebContextFactory.get()).thenReturn(mock(WebContext.class));
		when(WebContextFactory.get().getHttpServletRequest()).thenReturn(mock(HttpServletRequest.class));
		when(WebContextFactory.get().getHttpServletRequest().getSession()).thenReturn(null);
		when(SecurityUtil.getEmpresaByDWR(null)).thenReturn(empresa);

		doNothing().when(mail).testEnvio(empresa, "Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, true, false);

		String msgRetorno = utilDWR.enviaEmail(email, true, false);
		assertEquals("Email enviado com sucesso.", msgRetorno);
	}

	@Test
	public void testAutenticacaoException() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		String email = "teste@mail.com";

		when(WebContextFactory.get()).thenReturn(mock(WebContext.class));
		when(WebContextFactory.get().getHttpServletRequest()).thenReturn(mock(HttpServletRequest.class));
		when(WebContextFactory.get().getHttpServletRequest().getSession()).thenReturn(null);
		when(SecurityUtil.getEmpresaByDWR(null)).thenReturn(empresa);

		AuthenticationFailedException authenticationFailedException = new AuthenticationFailedException();

		doThrow(authenticationFailedException).when(mail).testEnvio(empresa, "Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, true, false);

		String msgRetorno = utilDWR.enviaEmail(email, true, false);
		assertEquals("Erro ao tentar autenticar o usuário, verifique Usuário e Senha.", msgRetorno);
	}

	@Test
	public void testErroInesperadoAoEnviarEmail() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		String email = "teste@mail.com";

		when(WebContextFactory.get()).thenReturn(mock(WebContext.class));
		when(WebContextFactory.get().getHttpServletRequest()).thenReturn(mock(HttpServletRequest.class));
		when(WebContextFactory.get().getHttpServletRequest().getSession()).thenReturn(null);
		when(SecurityUtil.getEmpresaByDWR(null)).thenReturn(empresa);

		doThrow(mock(Exception.class)).when(mail).testEnvio(empresa, "Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, true, false);

		String msgRetorno = utilDWR.enviaEmail(email, true, false);
		assertEquals("Erro desconhecido, entre em contato com o suporte.", msgRetorno);
	}

	@Test(expected = Exception.class)
	public void testErroInesperadoAoEnviarEmailComMensagemDeExcecao() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		String email = "teste@mail.com";

		when(WebContextFactory.get()).thenReturn(mock(WebContext.class));
		when(WebContextFactory.get().getHttpServletRequest()).thenReturn(mock(HttpServletRequest.class));
		when(WebContextFactory.get().getHttpServletRequest().getSession()).thenReturn(null);
		when(SecurityUtil.getEmpresaByDWR(null)).thenReturn(empresa);

		doThrow(new Exception("teste")).when(mail).testEnvio(empresa, "Teste do envio de email do RH", "Este email foi enviado de forma automática, não responda.", email, true, false);

		utilDWR.enviaEmail(email, true, false);
	}

	public static String getSMTPAddressFailedExceptionMessageQuandoAlgumaRestricaoDoServidorSMTPForViolada() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("O servidor SMTP recusou o envio do seu e-mail.\n");
		stringBuilder.append("Isto pode ter ocorrido devido algumas regras impostas ao seu servidor de email. Segue abaixo algumas dessas regras:\n\n");
		stringBuilder.append("1- O remetente do email (configurado no cadastro de empresa) não pode ser diferente do usuário configurado no cadastro do SMTP.\n\n");
		stringBuilder.append(" Por exemplo: Email cadastrado no cadastro de empresa está 'exemplo@fortes.com.br' e na configuração do SMTP 'fortes@exemplo.com.br'. \n\n");
		stringBuilder.append("2- O servidor SMTP não permite que o envio de emails pela máquina onde o sistema RH está instalado.\n\n");
		stringBuilder.append("3- O servidor SMTP requer autenticação. \n\n");
		stringBuilder.append("Obs: Caso não consiga concluir o envio do email, entre em contato com o suporte.");
		return stringBuilder.toString();

	}

	public static String getMessageExceptionMessageQuandoAlgumCampoEstiverMarcadoDeFormaIndevida() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("O servidor SMTP possui configurações divergentes do sistema, os problemas mais comuns são:\n\n");
		stringBuilder.append("1- O envio do email  do remetente (configurado no cadastro de empresa) não pode ser diferente do usuário configurado no cadastro de SMTP. \n\n");
		stringBuilder.append("2- Os campos 'Servidor SMTP' e 'Porta SMTP' respectivamente estão com informações divergentes. \n\n");
		stringBuilder.append("3- Campo 'TLS' marcado quando o servidor não o utiliza.\n\n");
		stringBuilder.append("Obs: Caso não consiga concluir o envio do email, entre em contato com o suporte.");

		return stringBuilder.toString();

	}
}
