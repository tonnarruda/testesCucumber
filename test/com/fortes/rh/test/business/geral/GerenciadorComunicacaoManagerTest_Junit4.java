package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.LntManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CartaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManagerImpl;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAutorizacaoGestor;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.CartaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;

public class GerenciadorComunicacaoManagerTest_Junit4 
{
	private GerenciadorComunicacaoManagerImpl gerenciadorComunicacaoManager = new GerenciadorComunicacaoManagerImpl();
	private GerenciadorComunicacaoDao gerenciadorComunicacaoDao;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private ParticipanteCursoLntManager participanteCursoLntManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private MotivoSolicitacaoManager motivoSolicitacaoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private UsuarioMensagemManager usuarioMensagemManager;
	private EstabelecimentoManager estabelecimentoManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private ComissaoMembroManager comissaoMembroManager;
	private QuestionarioManager questionarioManager;
	private ColaboradorManager colaboradorManager;
	private SolicitacaoManager solicitacaoManager;
	private ProvidenciaManager providenciaManager;
	private MensagemManager mensagemManager;
	private EmpresaManager empresaManager;
	private UsuarioManager usuarioManager;
	private CartaoManager cartaoManager;
	private ExameManager exameManager;
	private CargoManager cargoManager;
	private LntManager lntManager;
	private Mail mail;
	
	@Before
	public void setUp() throws Exception
    {
        gerenciadorComunicacaoDao = mock(GerenciadorComunicacaoDao.class);
        gerenciadorComunicacaoManager.setDao(gerenciadorComunicacaoDao);
        
        candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
        gerenciadorComunicacaoManager.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
        
        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        gerenciadorComunicacaoManager.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        
        periodoExperienciaManager = mock(PeriodoExperienciaManager.class);
        gerenciadorComunicacaoManager.setPeriodoExperienciaManager(periodoExperienciaManager);
        
        empresaManager = mock(EmpresaManager.class);
        gerenciadorComunicacaoManager.setEmpresaManager(empresaManager);
        
        usuarioMensagemManager = mock(UsuarioMensagemManager.class);
        gerenciadorComunicacaoManager.setUsuarioMensagemManager(usuarioMensagemManager);

        usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);
        gerenciadorComunicacaoManager.setUsuarioEmpresaManager(usuarioEmpresaManager);

        mensagemManager = mock(MensagemManager.class);
        gerenciadorComunicacaoManager.setMensagemManager(mensagemManager);
        
		comissaoMembroManager = mock(ComissaoMembroManager.class);
		gerenciadorComunicacaoManager.setComissaoMembroManager(comissaoMembroManager);
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        gerenciadorComunicacaoManager.setAreaOrganizacionalManager(areaOrganizacionalManager);

        motivoSolicitacaoManager = mock(MotivoSolicitacaoManager.class);
        gerenciadorComunicacaoManager.setMotivoSolicitacaoManager(motivoSolicitacaoManager);
        
        cargoManager = mock(CargoManager.class);
        gerenciadorComunicacaoManager.setCargoManager(cargoManager);
        
        lntManager = mock(LntManager.class);
        gerenciadorComunicacaoManager.setLntManager(lntManager);

        mail = mock(Mail.class);
        gerenciadorComunicacaoManager.setMail(mail);
        
        cartaoManager = mock(CartaoManager.class);
        gerenciadorComunicacaoManager.setCartaoManager(cartaoManager);
        
        colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
        MockSpringUtilJUnit4.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);

        solicitacaoManager = mock(SolicitacaoManager.class);
		MockSpringUtilJUnit4.mocks.put("solicitacaoManager", solicitacaoManager);

		providenciaManager = mock(ProvidenciaManager.class);
		gerenciadorComunicacaoManager.setProvidenciaManager(providenciaManager);
		
		participanteCursoLntManager = mock(ParticipanteCursoLntManager.class);
		gerenciadorComunicacaoManager.setParticipanteCursoLntManager(participanteCursoLntManager);
		
		colaboradorManager = mock(ColaboradorManager.class);
		MockSpringUtilJUnit4.mocks.put("colaboradorManager", colaboradorManager);
		gerenciadorComunicacaoManager.setColaboradorManager(colaboradorManager);
		
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
		MockSpringUtilJUnit4.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
		
		usuarioManager = mock(UsuarioManager.class);
		MockSpringUtilJUnit4.mocks.put("usuarioManager", usuarioManager);
		
		questionarioManager = mock(QuestionarioManager.class);
		MockSpringUtilJUnit4.mocks.put("questionarioManager", questionarioManager);
		
		exameManager = mock(ExameManager.class);
		MockSpringUtilJUnit4.mocks.put("exameManager", exameManager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		MockSpringUtilJUnit4.mocks.put("estabelecimentoManager", estabelecimentoManager);

		gerenciadorComunicacaoDao = mock(GerenciadorComunicacaoDao.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		usuarioMensagemManager = mock(UsuarioMensagemManager.class);
		usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);
		colaboradorManager = mock(ColaboradorManager.class);
		mail = mock(Mail.class);
		
		gerenciadorComunicacaoManager.setDao(gerenciadorComunicacaoDao);;
		gerenciadorComunicacaoManager.setAreaOrganizacionalManager(areaOrganizacionalManager);
		gerenciadorComunicacaoManager.setUsuarioMensagemManager(usuarioMensagemManager);
		gerenciadorComunicacaoManager.setUsuarioEmpresaManager(usuarioEmpresaManager);
		gerenciadorComunicacaoManager.setColaboradorManager(colaboradorManager);
		gerenciadorComunicacaoManager.setMail(mail);
		
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
    }
	
	@Test
	public void testEnviarAvisoAoAlterarStatusColaboradorSolPessoal() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areasOrganizacionais = Arrays.asList(areaOrganizacional);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "colaborador@email.com", areaOrganizacional);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L, "colaborador2@email.com", areaOrganizacional);

		Usuario usuarioLogado = UsuarioFactory.getEntity(1L);
		usuarioLogado.setColaborador(colaborador);
		
		Usuario usuarioSolicitante = UsuarioFactory.getEntity(1L);
		usuarioSolicitante.setColaborador(colaborador2);
		Collection<Usuario> usuarios = Arrays.asList(usuarioSolicitante);
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		
		CandidatoSolicitacao candidatoSolicitacaoAnterior = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, StatusAutorizacaoGestor.ANALISE);
		candidatoSolicitacaoAnterior.setUsuarioSolicitanteAutorizacaoGestor(usuarioLogado);
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, StatusAutorizacaoGestor.AUTORIZADO);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		
		String[] emails = new String[]{"email@email.com"};
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(1L);
		Collection<UsuarioEmpresa> usuariosEmpresa = Arrays.asList(usuarioEmpresa);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = GerenciadoresEnviarAvisoAoAlterarStatusColaboradorSolPessoal(empresa, usuarios, usuariosEmpresa);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId(), empresa.getId())).thenReturn(gerenciadorComunicacaos);
		mocksEnviarAvisoAoAlterarStatusColaboradorSolPessoal(empresa, areaOrganizacional, areasOrganizacionais, colaborador, usuarioSolicitante, solicitacao, candidatoSolicitacaoAnterior, parametrosDoSistema, emails, usuariosEmpresa, gerenciadorComunicacaos);
		
		Exception ex = null;
		try {
			gerenciadorComunicacaoManager.enviarAvisoAoAlterarStatusColaboradorSolPessoal(usuarioLogado, candidatoSolicitacaoAnterior, candidatoSolicitacao, empresa);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}

	@Test
	public void testEnviarAvisoAoInserirColaboradorSolPessoal() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areasOrganizacionais = Arrays.asList(areaOrganizacional);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "colaborador@email.com", areaOrganizacional);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);

		Usuario usuarioLogado = UsuarioFactory.getEntity(1L);
		usuarioLogado.setColaborador(colaborador);
		Collection<Usuario> usuarios = Arrays.asList(usuarioLogado);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setAutorizacaoGestorNaSolicitacaoPessoal(true);
		
		String[] emails = new String[]{"email@email.com"};
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(1L);
		Collection<UsuarioEmpresa> usuariosEmpresa = Arrays.asList(usuarioEmpresa);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = GerenciadoresEnviarAvisoAoAlterarStatusColaboradorSolPessoal(empresa, usuarios, usuariosEmpresa);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB.getId(), empresa.getId())).thenReturn(gerenciadorComunicacaos);
		mocksEnviarAvisoAoAlterarStatusColaboradorSolPessoal(empresa, areaOrganizacional, areasOrganizacionais, colaborador, null, solicitacao, null, parametrosDoSistema, emails, usuariosEmpresa, gerenciadorComunicacaos);
		
		Exception ex = null;
		try {
			gerenciadorComunicacaoManager.enviarAvisoAoInserirColaboradorSolicitacaoDePessoal(empresa, usuarioLogado, colaborador.getId(), solicitacao.getId());
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
	
	private void mocksEnviarAvisoAoAlterarStatusColaboradorSolPessoal(Empresa empresa, AreaOrganizacional areaOrganizacional, Collection<AreaOrganizacional> areasOrganizacionais, Colaborador colaborador, Usuario usuarioSolicitante, Solicitacao solicitacao, CandidatoSolicitacao candidatoSolicitacaoAnterior, ParametrosDoSistema parametrosDoSistema, String[] emails, Collection<UsuarioEmpresa> usuariosEmpresa, Collection<GerenciadorComunicacao> gerenciadorComunicacaos) throws Exception {
		if(candidatoSolicitacaoAnterior != null){
			when(colaboradorManager.findByCandidato(candidatoSolicitacaoAnterior.getCandidato().getId(), empresa.getId())).thenReturn(colaborador);
			when(usuarioManager.findById(candidatoSolicitacaoAnterior.getUsuarioSolicitanteAutorizacaoGestor().getId())).thenReturn(usuarioSolicitante);
		}
		
		when(solicitacaoManager.findById(solicitacao.getId())).thenReturn(solicitacao);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(colaboradorManager.findByIdDadosBasicos(colaborador.getId(), null)).thenReturn(colaborador);
		when(areaOrganizacionalManager.findAllListAndInativas(true, null, empresa.getId())).thenReturn(areasOrganizacionais);
		when(areaOrganizacionalManager.getEmailsResponsaveis(areaOrganizacional.getId(), areasOrganizacionais, AreaOrganizacional.RESPONSAVEL)).thenReturn(emails);
		when(areaOrganizacionalManager.getEmailsResponsaveis(areaOrganizacional.getId(), areasOrganizacionais, AreaOrganizacional.CORRESPONSAVEL)).thenReturn(emails);
		when(colaboradorManager.findEmailsByUsuarios(LongUtil.collectionToCollectionLong(usuariosEmpresa))).thenReturn(emails);
		when(areaOrganizacionalManager.getAncestrais(areasOrganizacionais, colaborador.getAreaOrganizacional().getId())).thenReturn(areasOrganizacionais);
		when(areaOrganizacionalManager.getAncestrais(areasOrganizacionais, colaborador.getAreaOrganizacional().getId())).thenReturn(areasOrganizacionais);
	}

	private Collection<GerenciadorComunicacao> GerenciadoresEnviarAvisoAoAlterarStatusColaboradorSolPessoal(Empresa empresa, Collection<Usuario> usuarios, Collection<UsuarioEmpresa> usuariosEmpresa) {
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(2L, empresa, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(3L, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao3.setUsuarios(usuarios);
		gerenciadorComunicacao3.setEmpresa(empresa);
		when(usuarioEmpresaManager.findUsuariosAtivo(LongUtil.collectionToCollectionLong(gerenciadorComunicacao3.getUsuarios()), gerenciadorComunicacao3.getEmpresa().getId())).thenReturn(usuariosEmpresa);
		GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(4L, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		GerenciadorComunicacao gerenciadorComunicacao5 = GerenciadorComunicacaoFactory.getEntity(5L, empresa, MeioComunicacao.EMAIL, EnviarPara.SOLICITANTE_SOLICITACAO);
		
		GerenciadorComunicacao gerenciadorComunicacao6 = GerenciadorComunicacaoFactory.getEntity(6L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		GerenciadorComunicacao gerenciadorComunicacao7 = GerenciadorComunicacaoFactory.getEntity(7L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		GerenciadorComunicacao gerenciadorComunicacao8 = GerenciadorComunicacaoFactory.getEntity(8L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao8.setUsuarios(usuarios);
		gerenciadorComunicacao8.setEmpresa(empresa);
		when(usuarioEmpresaManager.findUsuariosAtivo(LongUtil.collectionToCollectionLong(gerenciadorComunicacao8.getUsuarios()), gerenciadorComunicacao8.getEmpresa().getId())).thenReturn(usuariosEmpresa);
		GerenciadorComunicacao gerenciadorComunicacao9 = GerenciadorComunicacaoFactory.getEntity(9L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RESPONSAVEL_RH);
		GerenciadorComunicacao gerenciadorComunicacao10 = GerenciadorComunicacaoFactory.getEntity(10L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.SOLICITANTE_SOLICITACAO);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao1,gerenciadorComunicacao2,gerenciadorComunicacao3,gerenciadorComunicacao4,gerenciadorComunicacao5,gerenciadorComunicacao6,gerenciadorComunicacao7,gerenciadorComunicacao8,gerenciadorComunicacao9,gerenciadorComunicacao10);
		return gerenciadorComunicacaos;
	}
	
	@Test
	public void testeRemoveByOperacao(){
		Integer[] operacoes = {Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId(), Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB.getId()};
		gerenciadorComunicacaoManager.removeByOperacao(operacoes);
		verify(gerenciadorComunicacaoDao, times(1)).removeByOperacao(operacoes);
	}
	
	@Test
	public void testEnviaEmailParaResponsavelDoRHQuandoColaboradorCompletaAnoDeEmpresa() throws AddressException, MessagingException {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setQtdAnosDeEmpresa(1.0);
		Collection<Colaborador> colaboradores = Arrays.asList(colaborador); 
		
		Empresa empresa = criaEmpresa();
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);
		when(colaboradorManager.findComAnoDeEmpresa(eq(empresa.getId()), any(Date.class))).thenReturn(colaboradores);
		
		Exception ex = null;
		try {
			gerenciadorComunicacaoManager.enviaEmailQuandoColaboradorCompletaAnoDeEmpresa();
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
	}
	
	@Test
	public void testEnviaEmailParaColaboradorQuandoCompletarAnoDeEmpresaComCartaoConfigurado() throws AddressException, MessagingException {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setQtdAnosDeEmpresa(1.0);
		colaborador.setEmailColaborador("email@email.com.br");
		Collection<Colaborador> colaboradores = Arrays.asList(colaborador); 
		
		String subject = "Parabéns " + colaborador.getNome() + " por mais um ano de empresa";
		String body = "Parabéns por mais um ano de sucesso na empresa.<br><br>Cartão em anexo.";
		Empresa empresa = criaEmpresa();
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		Cartao cartao = CartaoFactory.getEntity(empresa, TipoCartao.ANIVERSARIO);
		DataSource[] files = new DataSource[]{};
		
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);

		when(colaboradorManager.findComAnoDeEmpresa(eq(empresa.getId()), any(Date.class))).thenReturn(colaboradores);
		when(cartaoManager.findByEmpresaIdAndTipo(eq(empresa.getId()), eq(TipoCartao.ANO_DE_EMPRESA))).thenReturn(cartao);
		when(cartaoManager.geraCartao(cartao, colaborador)).thenReturn(files);
		gerenciadorComunicacaoManager.enviaEmailQuandoColaboradorCompletaAnoDeEmpresa();
		verify(mail).send(empresa, subject, files, body, colaborador.getContato().getEmail());		
	}
	
	@Test
	public void testEnviaEmailParaColaboradorQuandoCompletarAnoDeEmpresaSemCartaoConfigurado() throws AddressException, MessagingException {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setQtdAnosDeEmpresa(1.0);
		colaborador.setEmailColaborador("email@email.com.br");
		Collection<Colaborador> colaboradores = Arrays.asList(colaborador); 
		
		String subject = "Parabéns " + colaborador.getNome() + " por mais um ano de empresa";
		String body = "Parabéns por mais um ano de sucesso na empresa.";
		
		Empresa empresa = criaEmpresa();
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		DataSource[] files = null;
		
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);

		when(colaboradorManager.findComAnoDeEmpresa(eq(empresa.getId()), any(Date.class))).thenReturn(colaboradores);
		when(cartaoManager.findByEmpresaIdAndTipo(eq(empresa.getId()), eq(TipoCartao.ANO_DE_EMPRESA))).thenReturn(null);
		gerenciadorComunicacaoManager.enviaEmailQuandoColaboradorCompletaAnoDeEmpresa();
		verify(mail).send(empresa, subject, files, body, colaborador.getContato().getEmail());		
	}
	
	@Test
	public void testEnviaEmailParaColaboradorQuandoCompletarAnoDeEmpresaSemCartaoConfiguradoException() throws AddressException, MessagingException {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setQtdAnosDeEmpresa(1.0);
		colaborador.setEmailColaborador("email@email.com.br");
		Collection<Colaborador> colaboradores = Arrays.asList(colaborador); 
		
		String subject = "Parabéns " + colaborador.getNome() + " por mais um ano de empresa";
		String body = "Parabéns por mais um ano de sucesso na empresa.";
		
		Empresa empresa = criaEmpresa();
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		DataSource[] files = null;
		
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);
		
		when(colaboradorManager.findComAnoDeEmpresa(eq(empresa.getId()), any(Date.class))).thenReturn(colaboradores);
		when(cartaoManager.findByEmpresaIdAndTipo(eq(empresa.getId()), eq(TipoCartao.ANO_DE_EMPRESA))).thenReturn(null);
		doThrow(AddressException.class).when(mail).send(empresa, subject, files, body, colaborador.getContato().getEmail());
		
		gerenciadorComunicacaoManager.enviaEmailQuandoColaboradorCompletaAnoDeEmpresa();
		verify(mail).send(empresa, subject, files, body, colaborador.getContato().getEmail());		
	}
	
	@Test
	public void testEnviaEmailParaResponsavelDoRHQuandoColaboradorCompletaAnoDeEmpresaExceptionGerenciadorNulo() throws AddressException, MessagingException 
	{
		Empresa empresa = criaEmpresa();
		
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId()),eq(empresa.getId()))).thenReturn(null);
	
		gerenciadorComunicacaoManager.enviaEmailQuandoColaboradorCompletaAnoDeEmpresa();
		
		verify(colaboradorManager, never()).findComAnoDeEmpresa(eq(empresa.getId()), any(Date.class));		
	}
	
	@Test
	public void testEnviaEmailParaResponsavelDoRHQuandoColaboradorCompletaAnoDeEmpresaAddressException() throws AddressException, MessagingException 
	{
		Collection<Colaborador> colaboradores = null; 
		
		Empresa empresa = criaEmpresa();
		String[] emails = empresa.getEmailRespRH().split(";");
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);
		when(colaboradorManager.findComAnoDeEmpresa(eq(empresa.getId()), any(Date.class))).thenReturn(colaboradores);
		
		gerenciadorComunicacaoManager.enviaEmailQuandoColaboradorCompletaAnoDeEmpresa();
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), eq(emails));
	}	
	private Empresa criaEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa I");
		empresa.setEmailRespRH("teste1@gmail.com;teste2@gmail.com;");
		empresa.setEmailRemetente("teste1@gmail.com");
		
		return empresa;
	}

	@Test
	public void testEnviarNotificaInicioLntPorEmailParaResponsavelRH() throws AddressException, MessagingException
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setEmailRespRH("responsavelrh@email.com.br");
		
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.INICIAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}	
	
	@Test
	public void testEnviarNotificaInicioLntPorEmailParaUsuarios() throws AddressException, MessagingException
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.INICIAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviarNotificaInicioLntPorEmailParaGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		
		String[] emails = new String[]{"gestor@email.com.br"};
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.INICIAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		when(areaOrganizacionalManager.findByLntId(eq(lnt.getId()), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getEmailsResponsaveis(eq(areas), eq(gerenciadorComunicacao.getEmpresa().getId()), eq(AreaOrganizacional.RESPONSAVEL))).thenReturn(emails);
		gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviarNotificaInicioLntPorEmailParaCoGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		
		String[] emails = new String[]{"cogestor@email.com.br"};
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.INICIAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		when(areaOrganizacionalManager.findByLntId(eq(lnt.getId()), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getEmailsResponsaveis(eq(areas), eq(gerenciadorComunicacao.getEmpresa().getId()), eq(AreaOrganizacional.CORRESPONSAVEL))).thenReturn(emails);
		gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviarNotificaInicioLntPorMensagemParaUsuarios() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.INICIAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagem(anyString(), eq("RH"), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testEnviarNotificaInicioLntPorMensagemParaGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		Collection<Long> areasIds = LongUtil.collectionToCollectionLong(areas);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.INICIAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		when(areaOrganizacionalManager.findByLntId(lnt.getId(), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getAncestraisIds(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas))).thenReturn(areasIds);
		
		gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagemRespAreaOrganizacional(anyString(), anyString(), anyString(), eq(areasIds), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testEnviarNotificaInicioLntPorMensagemParaCoGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		Collection<Long> areasIds = LongUtil.collectionToCollectionLong(areas);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.INICIAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		when(areaOrganizacionalManager.findByLntId(eq(lnt.getId()), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getAncestraisIds(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas))).thenReturn(areasIds);
		
		gerenciadorComunicacaoManager.enviaAvisoInicioLnt(Arrays.asList(lnt));
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(anyString(), anyString(), anyString(), eq(areasIds), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testEnviaAvisoLntFinalizadaPorEmailParaResponsavelRH() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setEmailRespRH("responsavel_rh@grupofortes.com.br");
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.FINALIZAR_LNT, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		setUpEnviaAvisoLntFinalizada(gerenciadorComunicacao, empresa);
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviaAvisoLntFinalizadaPorEmailParaUsuarios() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.FINALIZAR_LNT, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setUsuarios(Arrays.asList(UsuarioFactory.getEntity(1L)));
		
		setUpEnviaAvisoLntFinalizada(gerenciadorComunicacao, empresa);
		
		when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviaAvisoLntFinalizadaPorEmailParaGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.FINALIZAR_LNT, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		
		Map<Long, String> mapResponsaveisIdsEmails = new HashMap<Long, String>();
		mapResponsaveisIdsEmails.put(1L, "teste@teste.com");
		when(areaOrganizacionalManager.findMapResponsaveisIdsEmails(empresa.getId())).thenReturn(mapResponsaveisIdsEmails);

		setUpEnviaAvisoLntFinalizada(gerenciadorComunicacao, empresa);
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviaAvisoLntFinalizadaPorEmailParaCoGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.FINALIZAR_LNT, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		
		Map<Long, String> mapResponsaveisIdsEmails = new HashMap<Long, String>();
		mapResponsaveisIdsEmails.put(1L, "teste@teste.com");
		when(areaOrganizacionalManager.findMapCoResponsaveisIdsEmails(empresa.getId())).thenReturn(mapResponsaveisIdsEmails);

		setUpEnviaAvisoLntFinalizada(gerenciadorComunicacao, empresa);
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviaAvisoLntFinalizadaPorCaixaMensagemParaUsuarios() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.FINALIZAR_LNT, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setUsuarios(Arrays.asList(UsuarioFactory.getEntity(1L)));
		
		when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));

		setUpEnviaAvisoLntFinalizada(gerenciadorComunicacao, empresa);

		verify(mail, never()).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagem(anyString(), anyString(), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testEnviaAvisoLntFinalizadaPorCaixaMensagemParaGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.FINALIZAR_LNT, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		
		setUpEnviaAvisoLntFinalizada(gerenciadorComunicacao, empresa);

		verify(mail, never()).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagemRespAreaOrganizacional(anyString(), anyString(), anyString(), anyCollectionOf(Long.class), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testEnviaAvisoLntFinalizadaPorCaixaMensagemParaCoGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.FINALIZAR_LNT, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		
		setUpEnviaAvisoLntFinalizada(gerenciadorComunicacao, empresa);
		
		verify(mail, never()).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(anyString(), anyString(), anyString(), anyCollectionOf(Long.class), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}

	private Lnt setUpEnviaAvisoLntFinalizada(GerenciadorComunicacao gerenciadorComunicacao, Empresa empresa) throws Exception
	{
		Lnt lnt = LntFactory.getEntity(null, "LNT", DateUtil.incrementaDias(new Date(), -2), DateUtil.incrementaDias(new Date(), -1), null);
		CursoLnt cursoLnt = CursoLntFactory.getEntity(null, lnt); 
		
		ParticipanteCursoLnt participanteCursoLnt1 = ParticipanteCursoLntFactory.getEntity(null, null, cursoLnt);
		participanteCursoLnt1.setColaborador(ColaboradorFactory.getEntity(1L));
		participanteCursoLnt1.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		
		Map<Long, Collection<ParticipanteCursoLnt>> mapPerticipantesLNTPorResponsaveis = new HashMap<Long, Collection<ParticipanteCursoLnt>>();
		mapPerticipantesLNTPorResponsaveis.put(1L, Arrays.asList(participanteCursoLnt1));
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.FINALIZAR_LNT.getId(), empresa.getId())).thenReturn(Arrays.asList(gerenciadorComunicacao));

		gerenciadorComunicacaoManager.enviaAvisoLntFinalizada("subject", new StringBuilder("body"), "link", empresa.getId(), null, null, mapPerticipantesLNTPorResponsaveis, null);
		
		return lnt;
	}
	
	@Test
	public void testEnviaAvisoFimLntPorEmailParaCoGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacao.setQtdDiasLembrete("0");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		
		String[] emails = new String[]{"cogestor@email.com.br"};
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		when(areaOrganizacionalManager.findByLntId(eq(lnt.getId()), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getEmailsResponsaveis(eq(areas), eq(gerenciadorComunicacao.getEmpresa().getId()), eq(AreaOrganizacional.CORRESPONSAVEL))).thenReturn(emails);
		gerenciadorComunicacaoManager.enviaAvisoFimLnt(Arrays.asList(lnt));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviarNotificaFimLntPorEmailParaResponsavelRH() throws AddressException, MessagingException
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setEmailRespRH("responsavelrh@email.com.br");
		
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		gerenciadorComunicacao.setQtdDiasLembrete("0");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		gerenciadorComunicacaoManager.enviaAvisoFimLnt(Arrays.asList(lnt));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}	
	
	@Test
	public void testEnviarNotificaFimLntPorEmailParaUsuarios() throws AddressException, MessagingException
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setQtdDiasLembrete("0");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		gerenciadorComunicacaoManager.enviaAvisoFimLnt(Arrays.asList(lnt));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviarNotificaFimLntPorEmailParaGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacao.setQtdDiasLembrete("0");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		
		String[] emails = new String[]{"gestor@email.com.br"};
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		when(areaOrganizacionalManager.findByLntId(eq(lnt.getId()), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getEmailsResponsaveis(eq(areas), eq(gerenciadorComunicacao.getEmpresa().getId()), eq(AreaOrganizacional.RESPONSAVEL))).thenReturn(emails);
		gerenciadorComunicacaoManager.enviaAvisoFimLnt(Arrays.asList(lnt));
		
		verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	}
	
	@Test
	public void testEnviarNotificaFimLntPorMensagemParaUsuarios() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setQtdDiasLembrete("0");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		gerenciadorComunicacaoManager.enviaAvisoFimLnt(Arrays.asList(lnt));
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagem(anyString(), eq("RH"), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testEnviarNotificaFimLntPorMensagemParaGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacao.setQtdDiasLembrete("0");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		Collection<Long> areasIds = LongUtil.collectionToCollectionLong(areas);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		when(areaOrganizacionalManager.findByLntId(lnt.getId(), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getAncestraisIds(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas))).thenReturn(areasIds);
		
		gerenciadorComunicacaoManager.enviaAvisoFimLnt(Arrays.asList(lnt));
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagemRespAreaOrganizacional(anyString(), anyString(), anyString(), eq(areasIds), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testEnviarAvisoLntAutomaticoPorMensagemParaCoGestor() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Lnt lnt = LntFactory.getEntity(null, "LNT", new Date(), new Date(), null);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacao.setQtdDiasLembrete("0");
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		Collection<Long> areasIds = LongUtil.collectionToCollectionLong(areas);
		
		when(lntManager.findLntsNaoFinalizadas(null)).thenReturn(Arrays.asList(lnt));
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ENCERRAR_PERIODO_LNT.getId(), null)).thenReturn(gerenciadorComunicacaos);
		when(areaOrganizacionalManager.findByLntId(eq(lnt.getId()), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getAncestraisIds(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas))).thenReturn(areasIds);
		
		gerenciadorComunicacaoManager.enviaAvisoLntAutomatico();
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(anyString(), anyString(), anyString(), eq(areasIds), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
}
