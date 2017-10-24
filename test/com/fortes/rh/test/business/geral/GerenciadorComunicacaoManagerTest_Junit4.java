package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusAutorizacaoGestor;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.dao.hibernate.pesquisa.AvaliacaoTurmaFactory;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.CartaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoLimiteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.geral.ProvidenciaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringUtil.class,ArquivoUtil.class,RelatorioUtil.class})
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
	
	@SuppressWarnings("deprecation")
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

		PowerMockito.mockStatic(SpringUtil.class);
		PowerMockito.mockStatic(ArquivoUtil.class);
		PowerMockito.mockStatic(RelatorioUtil.class);
		
		BDDMockito.given(SpringUtil.getBean("colaboradorManager")).willReturn(colaboradorManager);
		BDDMockito.given(SpringUtil.getBean("historicoColaboradorManager")).willReturn(historicoColaboradorManager);
		BDDMockito.given(SpringUtil.getBeanOld("colaboradorTurmaManager")).willReturn(colaboradorTurmaManager);
		BDDMockito.given(SpringUtil.getBeanOld("colaboradorManager")).willReturn(colaboradorManager);
		BDDMockito.given(SpringUtil.getBeanOld("usuarioManager")).willReturn(usuarioManager);
		BDDMockito.given(SpringUtil.getBeanOld("questionarioManager")).willReturn(questionarioManager);
		BDDMockito.given(SpringUtil.getBeanOld("exameManager")).willReturn(exameManager);
    }
	
	@Test
	public void testeInsereGerenciadorComunicacaoDefault() 
	{
		// se for inserir mais  um default terá de alterar no importador o método insereGerenciadorComunicacaoDefault(empresa) em empresaJDBC.
		Empresa empresa = EmpresaFactory.getEmpresa();
		
		gerenciadorComunicacaoManager.insereGerenciadorComunicacaoDefault(empresa);
		
		verify(gerenciadorComunicacaoDao,times(25)).save(any(GerenciadorComunicacao.class));
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
	
	@Test
	public void testenviaAvisoAtualizacaoInfoPessoaisNaoConfigurado() throws Exception
	{
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ATUALIZAR_INFO_PESSOAIS.getId(), null)).thenReturn(new ArrayList<GerenciadorComunicacao>());
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Colaborador colaboradorOriginal = ColaboradorFactory.getEntity();
		Colaborador colaboradorAtualizado = ColaboradorFactory.getEntity();
		
		gerenciadorComunicacaoManager.enviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, empresa.getId());

		verify(usuarioMensagemManager, never()).saveMensagemAndUsuarioMensagem(anyString(), anyString(), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class) , any(Avaliacao.class), any(Long.class));
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
	}
	
	@Test
	public void testenviaAvisoAtualizacaoInfoPessoaisSemDadosAlterados() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);

		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ATUALIZAR_INFO_PESSOAIS.getId(), empresa.getId())).thenReturn(Arrays.asList(gerenciadorComunicacao));
		
		Colaborador colaboradorOriginal = ColaboradorFactory.getEntity();
		Colaborador colaboradorAtualizado = colaboradorOriginal;
		
		gerenciadorComunicacaoManager.enviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, empresa.getId());
		
		verify(usuarioMensagemManager, never()).saveMensagemAndUsuarioMensagem(anyString(), anyString(), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class) , any(Avaliacao.class), any(Long.class));
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
	}
	
	@Test
	public void testenviaAvisoAtualizacaoInfoPessoais() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(2L, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ATUALIZAR_INFO_PESSOAIS.getId(), empresa.getId())).thenReturn(Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2));
		
		Colaborador colaboradorOriginal = ColaboradorFactory.getEntity();
		Colaborador colaboradorAtualizado = ColaboradorFactory.getEntity(EstadoFactory.getEntity());
		
		gerenciadorComunicacaoManager.enviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, empresa.getId());
		
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagem(anyString(), anyString(), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class) , any(Avaliacao.class), any(Long.class));
		verify(mail, times(1)).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
	}
	
	@Test
	public void testenviaAvisoAtualizacaoInfoPessoaisDoisGerenciadoresException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(2L, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ATUALIZAR_INFO_PESSOAIS.getId(), empresa.getId())).thenReturn(Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2));
		doThrow(AddressException.class).when(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class) , any(Avaliacao.class), any(Long.class));
				
		Colaborador colaboradorOriginal = ColaboradorFactory.getEntity();
		Colaborador colaboradorAtualizado = ColaboradorFactory.getEntity(EstadoFactory.getEntity());
		
		gerenciadorComunicacaoManager.enviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, empresa.getId());
		
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagem(anyString(), anyString(), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class) , any(Avaliacao.class), any(Long.class));
		verify(mail, times(1)).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
	}
	
	@Test
	public void testenviaAvisoAtualizacaoInfoPessoaisUmGerenciadoresException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.ATUALIZAR_INFO_PESSOAIS.getId(), empresa.getId())).thenReturn(Arrays.asList(gerenciadorComunicacao1));
		doThrow(AddressException.class).when(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class) , any(Avaliacao.class), any(Long.class));
		
		Colaborador colaboradorOriginal = ColaboradorFactory.getEntity();
		Colaborador colaboradorAtualizado = ColaboradorFactory.getEntity(EstadoFactory.getEntity());
		
		gerenciadorComunicacaoManager.enviaAvisoAtualizacaoInfoPessoais(colaboradorOriginal, colaboradorAtualizado, empresa.getId());
		
		verify(usuarioMensagemManager, times(1)).saveMensagemAndUsuarioMensagem(anyString(), anyString(), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class) , any(Avaliacao.class), any(Long.class));
		verify(mail, never()).send(eq(empresa), anyString(), anyString(), any(File[].class), any(String[].class));
	}
	
	@Test
	public void testEnviaEmailBoasVindasColaboradorJob() throws AddressException, MessagingException 
	{
		Empresa empresa = criaEmpresa();

		Colaborador colab1 = ColaboradorFactory.getEntity(1L, empresa);
		colab1.setEmailColaborador("email@gmail.com");

		Colaborador colab2 = ColaboradorFactory.getEntity(2L, empresa);
		colab2.setNome("Colaborador");
		
		Collection<Colaborador> colaboradores = new ArrayList<>();
		colaboradores.add(colab1);
		colaboradores.add(colab2);

		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		
		Cartao cartao = CartaoFactory.getEntity(empresa);
		cartao.setMensagem("teste");
		
		when(colaboradorManager.findByAdmitidos(any(Date.class))).thenReturn(colaboradores);
		when(gerenciadorComunicacaoDao.findByOperacaoIdAndEmpresaId(Operacao.BOAS_VINDAS_COLABORADORES.getId(), empresa.getId())).thenReturn(gerenciadorComunicacao);
		when(cartaoManager.findByEmpresaIdAndTipo(empresa.getId(), TipoCartao.BOAS_VINDAS)).thenReturn(cartao);
		
		gerenciadorComunicacaoManager.enviaEmailBoasVindasColaboradorJob();

		verify(colaboradorManager, times(1)).findByAdmitidos(any(Date.class));
		verify(gerenciadorComunicacaoDao, times(2)).findByOperacaoIdAndEmpresaId(Operacao.BOAS_VINDAS_COLABORADORES.getId(), empresa.getId());
		verify(cartaoManager, times(2)).findByEmpresaIdAndTipo(empresa.getId(), TipoCartao.BOAS_VINDAS);
		verify(mail, times(1)).sendImg(empresa, "Seja bem vindo a empresa " + colab1.getEmpresaNome(), cartao.getMensagem().replace("#NOMECOLABORADOR#", colab1.getNome()), ArquivoUtil.getPathBackGroundCartao(cartao.getImgUrl()), colab1.getContato().getEmail());
		verify(mail, times(1)).sendImg(empresa, "Seja bem vindo a empresa " + colab2.getEmpresaNome(), cartao.getMensagem().replace("#NOMECOLABORADOR#", colab2.getNome()), ArquivoUtil.getPathBackGroundCartao(cartao.getImgUrl()), colab2.getContato().getEmail());
	}
	
	@Test
	public void testEnviarNotificacaoCursosAvencerColaboradorEmail() throws AddressException, MessagingException {
		
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();

		verify(mail).send(eq(empresa),any(ParametrosDoSistema.class), anyString(), anyString(), eq(Boolean.TRUE), any(String[].class));
	}
	
	@Test
	public void testEnviarNotificacaoCursosAvencerGestorAreaEmail() throws Exception {
		
		Empresa empresa = criaEmpresa();
		String[] emails = {"gestorarea@email.com.br"};
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		Colaborador colaborador  = ((ColaboradorTurma ) (colaboradoresTurmas.toArray()[0])).getColaborador();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		when(areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.RESPONSAVEL, null)).thenReturn(emails);

		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(mail).send(eq(empresa),any(ParametrosDoSistema.class), anyString(), anyString(), eq(Boolean.TRUE), eq(emails[0]));
	}

	@Test
	public void testEnviarNotificacaoCursosAvencerCoGestorAreaEmail() throws Exception {
		
		Empresa empresa = criaEmpresa();
		String[] emails = {"cogestorarea@email.com.br"};
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		Colaborador colaborador  = ((ColaboradorTurma ) (colaboradoresTurmas.toArray()[0])).getColaborador();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		when(areaOrganizacionalManager.getEmailsResponsaveis(colaborador.getAreaOrganizacional().getId(), gerenciadorComunicacao.getEmpresa().getId(), AreaOrganizacional.CORRESPONSAVEL, null)).thenReturn(emails);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(mail).send(eq(empresa),any(ParametrosDoSistema.class), anyString(), anyString(), eq(Boolean.TRUE), eq(emails[0]));
	}

	@Test
	public void testEnviarNotificacaoCursosAvencerResponsavelRH() throws Exception {
		
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(mail).send(eq(empresa),any(ParametrosDoSistema.class), anyString(), anyString(), eq(Boolean.TRUE),eq(empresa.getArrayEmailRespRH()[0]),eq(empresa.getArrayEmailRespRH()[1]));
	}
	
	@Test
	public void testEnviarNotificacaoCursosAvencerUsuarioEmail() throws Exception {
		
		Empresa empresa = criaEmpresa();
		String[] emails = {"usuario@email.com.br"};
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		when(usuarioManager.findEmailsByUsuario(any(Long[].class),anyString())).thenReturn(emails);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(mail).send(eq(empresa),any(ParametrosDoSistema.class), anyString(), anyString(), eq(Boolean.TRUE), eq(emails[0]));
	}
	
	@Test
	public void testEnviarNotificacaoCursosAvencerEmailSelecionarEnviarPara() throws Exception {
		
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.SELECIONAR_ENVIAR_PARA);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(mail, never()).send(eq(empresa), any(ParametrosDoSistema.class), anyString(), anyString(),any(Boolean.class), any(String[].class));
	}
	
	@Test
	public void testEnviarNotificacaoCursosAvencerGestorAreaCaixaMensagem() throws Exception {
		
		Empresa empresa = criaEmpresa();
		String[] emails = {"gestorArea@email.com.br"};
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		when(usuarioManager.findEmailsByUsuario(any(Long[].class),anyString())).thenReturn(emails);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagemRespAreaOrganizacional(anyString(), eq("RH"), anyString(),anyCollectionOf(Long.class), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testEnviarNotificacaoCursosAvencerCogestorAreaCaixaMensagem() throws Exception {
		
		Empresa empresa = criaEmpresa();
		String[] emails = {"cogestorArea@email.com.br"};
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		when(usuarioManager.findEmailsByUsuario(any(Long[].class),anyString())).thenReturn(emails);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional(anyString(), eq("RH"), anyString(),anyCollectionOf(Long.class), eq(TipoMensagem.TED), any(Avaliacao.class), anyLong());
	}

	@Test
	public void testEnviarNotificacaoCursosAvencerUsuarioCaixaMensagem() throws Exception {
		
		Empresa empresa = criaEmpresa();
		String[] emails = {"usuario@email.com.br"};
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		when(usuarioManager.findEmailsByUsuario(any(Long[].class),anyString())).thenReturn(emails);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), eq("RH"), anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), eq(TipoMensagem.TED) , any(Avaliacao.class), any(Long.class));
	}
	
	@Test
	public void testNaoEnviarNotificacaoCursosAvencerCaixaMensagem() throws Exception {
		
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.SELECIONAR_ENVIAR_PARA);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(usuarioMensagemManager, never()).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	}
	
	@Test
	public void testNaoEnviarNotificacaoCursosAvencer() throws Exception {
		
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.SELECIONAR_MEIO_COMUNICACAO, EnviarPara.SELECIONAR_ENVIAR_PARA);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		Collection<ColaboradorTurma> colaboradoresTurmas  = retornaColaboradorTurmaComCursoECertificacoes();
		
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(new ParametrosDoSistema());
		when(empresaManager.findTodasEmpresas()).thenReturn(Arrays.asList(empresa));
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CURSOS_A_VENCER.getId()),anyLong())).thenReturn(gerenciadorComunicacaos);
		when(colaboradorTurmaManager.findCursosCertificacoesAVencer(any(Date.class),eq(gerenciadorComunicacao.getEmpresa().getId()))).thenReturn(colaboradoresTurmas);
		
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
		
		verify(usuarioMensagemManager, never()).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
		verify(mail, never()).send(eq(empresa), any(ParametrosDoSistema.class), anyString(), anyString(),any(Boolean.class), any(String[].class));
	}

	@Test
	public void testExecuteEncerrarSolicitacao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setMailNaoAptos("Envio de email");
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.CANDIDATO_NAO_APTO);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(candidatoSolicitacaoManager.getEmailNaoAptos(anyLong(),eq(empresa))).thenReturn(new String[] {"teste@teste.com.br"});
		when(gerenciadorComunicacaoDao.findByOperacaoId(any(Integer.class),anyLong())).thenReturn(gerenciadorComunicacaos);
		
		gerenciadorComunicacaoManager.enviaEmailCandidatosNaoAptos(empresa, 1L);
		
		verify(mail).send(eq(empresa),anyString(),anyString(),any(File[].class),eq("teste@teste.com.br"));
	}
	
	@Test
	public void testEnviaEmailSolicitacao() throws AddressException, MessagingException
	{
		ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity(1L);
		parametros.setAppUrl("url");
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setNome("Empresa I");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("Estabelecimento I");
		
		Usuario usuarioSolicitante = UsuarioFactory.getEntity();
		usuarioSolicitante.setId(1L);
			
		Contato contato = new Contato();
		contato.setEmail("email@email.com");
		
		Colaborador solicitante = ColaboradorFactory.getEntity();
		solicitante.setContato(contato);
		solicitante.setUsuario(usuarioSolicitante);
		solicitante.setId(1L);
		solicitante.setNome("Joao");
		
		Usuario usuarioLiberador = UsuarioFactory.getEntity();
		usuarioLiberador.setId(1L);
		
		Colaborador liberador = ColaboradorFactory.getEntity();
		liberador.setUsuario(usuarioLiberador);
		liberador.setId(2L);
		liberador.setNome("Maria");
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao.setDescricao("Motivo I");
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setDescricao("Solicitação I");
		solicitacao.setLiberador(usuarioLiberador);
		solicitacao.setSolicitante(usuarioSolicitante);
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.ANALISE);
		solicitacao.setData(new Date());
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.SOLICITANTE_SOLICITACAO);

		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		when(parametrosDoSistemaManager.findById(parametros.getId())).thenReturn(parametros);
		when(colaboradorManager.findByUsuarioProjection(usuarioSolicitante.getId(),usuarioSolicitante.isAcessoSistema())).thenReturn(solicitante);
		when(colaboradorManager.findByUsuarioProjection(usuarioSolicitante.getId(),Boolean.FALSE)).thenReturn(liberador);
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.ALTERAR_STATUS_SOLICITACAO.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);

		gerenciadorComunicacaoManager.enviaEmailSolicitanteSolicitacao(solicitacao, empresa, usuarioLiberador);
		
		verify(mail).send(eq(empresa),eq(parametros),anyString(),anyString(),any(Boolean.class),anyString(),anyString());
	}
	
	@Test
	 public void testEnviarLembreteResponderAvaliacaoDesempenho() throws Exception
	 {
		 ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity(1L);
		 parametros.setAppUrl("url");

		 Empresa empresa = EmpresaFactory.getEmpresa(1L);

		 Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		 colaborador1.setEmpresa(empresa);
		 colaborador1.setEmailColaborador("teste1@fortesinformatica.com.br");

		 Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		 colaborador2.setEmpresa(empresa);
		 colaborador2.setEmailColaborador("teste2@fortesinformatica.com.br");

		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 when(colaboradorManager.findColaboradorDeAvaliacaoDesempenhoNaoRespondida()).thenReturn(Arrays.asList(colaborador1, colaborador2));
		 when(parametrosDoSistemaManager.findById(parametros.getId())).thenReturn(parametros); 
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);
		 
		 gerenciadorComunicacaoManager.enviarLembreteResponderAvaliacaoDesempenho();
		 
		 verify(mail,times(2)).send(eq(empresa),eq(parametros),anyString(),anyString(),eq(Boolean.TRUE),anyString());
		
	 }
	
	@Test
	 public void testEnviaEmailQuestionarioLiberado() throws Exception
	 {
		 ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity(1L);
		 parametros.setAppUrl("url");
		 
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 
		 Questionario questionario = QuestionarioFactory.getEntity(1L);
		 questionario.setEmpresa(empresa);
		 questionario.setDataInicio(new Date());
		 questionario.setDataFim(new Date());
		 
		 Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		 colaborador1.setEmailColaborador("teste1@fortesinformatica.com.br");
		 
		 ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
		 colaboradorQuestionario1.setColaborador(colaborador1);
		 
		 Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		 colaborador2.setEmailColaborador("teste2@fortesinformatica.com.br");
		 
		 ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity(2L);
		 colaboradorQuestionario2.setColaborador(colaborador2);
		 
		 Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		 colaboradorQuestionarios.add(colaboradorQuestionario1);
		 colaboradorQuestionarios.add(colaboradorQuestionario2);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 when(parametrosDoSistemaManager.findById(parametros.getId())).thenReturn(parametros);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.LIBERAR_PESQUISA.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);
		 
		 gerenciadorComunicacaoManager.enviaEmailQuestionarioLiberado(empresa, questionario, colaboradorQuestionarios);
		
		 verify(mail,times(2)).send(eq(empresa),eq(parametros),anyString(),anyString(),any(Boolean.class),anyString());
	 }
	
	@Test
	 public void testEnviaLembreteDeQuestionarioNaoLiberado() throws Exception
	 {
		 ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity(1L);
		 parametros.setAppUrl("url");
		 
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setEmailRespRH("email@email.com");
		 
		 Questionario questionario = QuestionarioFactory.getEntity(1L);
		 questionario.setEmpresa(empresa);
		 questionario.setDataInicio(new Date());
		 questionario.setDataFim(new Date());
		 questionario.setTipo(1);
		 questionario.setTitulo("Questionário I");
		 
		 Collection<Questionario> questionarios = Arrays.asList(questionario);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		 gerenciadorComunicacao.setQtdDiasLembrete("1");
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 when(questionarioManager.findQuestionarioNaoLiberados(any(Date.class))).thenReturn(questionarios);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.PESQUISA_NAO_LIBERADA.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 
		 gerenciadorComunicacaoManager.enviaLembreteDeQuestionarioNaoLiberado();
		
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	
	@Test
	 public void testEnviaAvisoDeCadastroCandidatoModuloExternoPorEmailParaResponsavelRh() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 when(empresaManager.findByIdProjection(any(Long.class))).thenReturn(empresa);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CADASTRAR_CANDIDATO_MODULO_EXTERNO.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);

		 gerenciadorComunicacaoManager.enviaAvisoDeCadastroCandidato("Chico", empresa.getId());
		 
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	
	@Test
	 public void testEnviaAvisoDeCadastroCandidatoModuloExternoPorEmailParaUsuarios() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 String[] emails = new String[] {"email@email.com"};
		 
		 when(empresaManager.findByIdProjection(any(Long.class))).thenReturn(empresa);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CADASTRAR_CANDIDATO_MODULO_EXTERNO.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 when(colaboradorManager.findEmailsByUsuarios(anyCollectionOf(Long.class))).thenReturn(emails);
		 
		 gerenciadorComunicacaoManager.enviaAvisoDeCadastroCandidato("Chico", empresa.getId());
		
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	
	@Test
	 public void testEnviaEmailQtdCurriculosCadastrados() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 Collection<Empresa> empresas = Arrays.asList(empresa);

		 Date DiaDoMesDeReferencia = DateUtil.retornaDataDiaAnterior(new Date());
		 Date inicioMes = DateUtil.getInicioMesData(DiaDoMesDeReferencia);
		 Date fimMes = DateUtil.getUltimoDiaMes(DiaDoMesDeReferencia);

		 Candidato pedro = new Candidato(empresa.getNome(), 'C', 10);
		 Collection<Candidato> candidatos = Arrays.asList(pedro);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.QTD_CURRICULOS_CADASTRADOS.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 
		 gerenciadorComunicacaoManager.enviaEmailQtdCurriculosCadastrados(empresas, inicioMes, fimMes, candidatos);
		 
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	
	 @Test
	 public void testEnviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo() throws Exception
	 {
		 ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity(1L);
		 parametros.setAppUrl("url");
		 
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Contato contato = new Contato();
		 contato.setEmail("email@email.com");
		 
		 FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		 faixaSalarial.setCargo(CargoFactory.getEntity());
		 
		 Colaborador teo = ColaboradorFactory.getEntity(1L);
		 teo.setNome("Teo");
		 teo.setEmpresa(empresa);
		 teo.setContato(contato);
		 teo.setFaixaSalarial(faixaSalarial);
		 
		 Colaborador leo = ColaboradorFactory.getEntity(2L);
		 leo.setNome("Leo");
		 leo.setEmpresa(empresa);
		 leo.setContato(contato);
		 leo.setFaixaSalarial(faixaSalarial);
		 
		 PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity();
		 periodoExperiencia.setDias(10);
		 
		 Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		 avaliacao.setTitulo("avaliacao");
		 
		 ColaboradorPeriodoExperienciaAvaliacao teoPeriodoExperiencia = new ColaboradorPeriodoExperienciaAvaliacao();
		 teoPeriodoExperiencia.setColaborador(teo);
		 teoPeriodoExperiencia.setPeriodoExperiencia(periodoExperiencia);
		 teoPeriodoExperiencia.setAvaliacao(avaliacao);

		 ColaboradorPeriodoExperienciaAvaliacao leoPeriodoExperiencia = new ColaboradorPeriodoExperienciaAvaliacao();
		 leoPeriodoExperiencia.setColaborador(leo);
		 leoPeriodoExperiencia.setPeriodoExperiencia(periodoExperiencia);
		 leoPeriodoExperiencia.setAvaliacao(avaliacao);
		 
		 Collection<ColaboradorPeriodoExperienciaAvaliacao > colaboradores = Arrays.asList(teoPeriodoExperiencia, leoPeriodoExperiencia);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR_AVALIADO);
		 gerenciadorComunicacao.setQtdDiasLembrete("9");
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 when(parametrosDoSistemaManager.findById(parametros.getId())).thenReturn(parametros);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 
		 gerenciadorComunicacaoManager.enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(colaboradores);
		 
		 verify(mail,times(2)).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	 
	 @Test
	 public void testEnviaLembreteExamesPrevistos() throws Exception
	 {
		 ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity(1L);
		 parametros.setAppUrl("url");
		 parametros.setAppVersao("1");
		 
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Collection<Empresa> empresas = Arrays.asList(empresa);
		 
		 Usuario usuario = UsuarioFactory.getEntity();
		 Collection<Usuario> usuarios = Arrays.asList(usuario);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 Collection<ExamesPrevistosRelatorio> examesPrevistosRelatorios = new ArrayList<ExamesPrevistosRelatorio>(); 
		 
		 ExamesPrevistosRelatorio exameRelatorio = new ExamesPrevistosRelatorio();
		 exameRelatorio.setExameId(1l);

		 examesPrevistosRelatorios.add(exameRelatorio);
		 
		 when(parametrosDoSistemaManager.findByIdProjection(parametros.getId())).thenReturn(parametros);
		 when(exameManager.findRelatorioExamesPrevistos(anyLong(),any(Date.class),any(Date.class),any(Long[].class),any(Long[].class),any(Long[].class),any(Long[].class),any(Character.class),any(Boolean.class),any(Boolean.class),any(Boolean.class))).thenReturn(examesPrevistosRelatorios);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.EXAMES_PREVISTOS.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));
		 when(colaboradorManager.findEmailsByUsuarios(anyCollectionOf(Long.class))).thenReturn(new String[]{"email@email.com"});
		 
		 gerenciadorComunicacaoManager.enviaLembreteExamesPrevistos(empresas);
		 
		 verify(mail, times(1)).send(eq(empresa), anyString(), any(DataSource[].class), anyString(), any(String[].class));
	 }
	
	 @Test
	 public void testEnviaMensagemLembretePeriodoExperiencia() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");

		 Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		 estabelecimento.setNome("Estabelecimento");
		 
		 PeriodoExperiencia periodoexperiencia = PeriodoExperienciaFactory.getEntity();
		 periodoexperiencia.setDias(30);
		 periodoexperiencia.setEmpresa(empresa);
		 Collection<PeriodoExperiencia> periodoExperiencias = Arrays.asList(periodoexperiencia);
		 
		 FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity(1L);
		 faixa1.setCargo(CargoFactory.getEntity());
		 faixa1.setDescricao("Faixa1");

		 AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		 area.setDescricao("Area");
		 
		 Colaborador teo = ColaboradorFactory.getEntity(1L);
		 teo.setNome("Teo");
		 teo.setNomeComercial("Teo");
		 teo.setAreaOrganizacional(area);
		 teo.setFaixaSalarial(faixa1);
		 teo.setEmpresa(empresa);
		 teo.setEstabelecimento(estabelecimento);
		 Collection<Colaborador> colaboradors = Arrays.asList(teo);
		 
		 Usuario usuario = UsuarioFactory.getEntity();
		 Collection<Usuario> usuarios = Arrays.asList(usuario);
		 
		 GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		 gerenciadorComunicacao1.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 gerenciadorComunicacao2.setUsuarios(usuarios);
		 gerenciadorComunicacao2.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		 gerenciadorComunicacao3.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		 gerenciadorComunicacao4.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao5 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		 gerenciadorComunicacao5.setQtdDiasLembrete("1");
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2, gerenciadorComunicacao3, gerenciadorComunicacao4, gerenciadorComunicacao5);
		 
		 String[] emails = new String[] {"email1@teste.com"};
		 
		 when(periodoExperienciaManager.findAllAtivos()).thenReturn(periodoExperiencias);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 when(colaboradorManager.findAdmitidosHaDias(any(Integer.class),eq(empresa),anyLong())).thenReturn(colaboradors);
		 when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));
		 when(areaOrganizacionalManager.getEmailsResponsaveis(eq(teo.getAreaOrganizacional().getId()),eq(teo.getEmpresa().getId()),eq(AreaOrganizacional.RESPONSAVEL),any(String.class))).thenReturn(emails);
		 when(areaOrganizacionalManager.getEmailsResponsaveis(eq(teo.getAreaOrganizacional().getId()),eq(teo.getEmpresa().getId()),eq(AreaOrganizacional.CORRESPONSAVEL),any(String.class))).thenReturn(emails);
		 
		 gerenciadorComunicacaoManager.enviaMensagemLembretePeriodoExperiencia();
		 
		 verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
		 verify(usuarioMensagemManager,times(2)).saveMensagemResponderAcompPeriodoExperiencia(anyString(), anyString(),anyString(), anyCollectionOf(Long.class), any(Character.class),any(Avaliacao.class), any(Colaborador.class),any(Integer.class));
		 verify(mail,times(2)).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	 
	 @Test
	 public void testNotificaBackup() throws AddressException, MessagingException
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa();
		 
		 ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		 parametroSistema.setAppUrl("url");
		 parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(1L, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_TECNICO);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 when(parametrosDoSistemaManager.findById(1l)).thenReturn(parametroSistema);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.GERAR_BACKUP.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		
		 gerenciadorComunicacaoManager.notificaBackup("dump.sql");
		 
		 verify(mail).send(anyString(),anyString(),anyString() ,anyString());
	 }
	 
	 @Test
	 public void testEnviarEmailContratacaoColaborador() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Colaborador teo = ColaboradorFactory.getEntity(1L);
		 teo.setNome("Teo");
		 teo.setEmpresa(empresa);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_SETOR_PESSOAL);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CONTRATAR_COLABORADOR_AC.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 
		 gerenciadorComunicacaoManager.enviarEmailContratacaoColaborador(teo.getNome(), empresa);
		 
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	 
	 @Test
	 public void testEnviaMensagemPeriodoExperienciaParaGestorAreaOrganizacionalPorCaixaDeMensagem() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");

		 FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity(1L);
		 faixa1.setCargo(CargoFactory.getEntity());
		 faixa1.setDescricao("Faixa1");

		 AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		 area.setDescricao("Area");
		 
		 Colaborador avaliador = ColaboradorFactory.getEntity(1L);
		 avaliador.setNome("Teo");
		 avaliador.setNomeComercial("Teo");
		 avaliador.setAreaOrganizacional(area);
		 avaliador.setFaixaSalarial(faixa1);
		 avaliador.setEmpresa(empresa);

		 Colaborador avaliado = ColaboradorFactory.getEntity(1L);
		 avaliado.setNome("Leo");
		 avaliado.setNomeComercial("Leo");
		 avaliado.setAreaOrganizacional(area);
		 avaliado.setFaixaSalarial(faixa1);
		 avaliado.setEmpresa(empresa);

		 Usuario usuario = UsuarioFactory.getEntity();
		 Collection<Usuario> usuarios = Arrays.asList(usuario);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 when(colaboradorManager.findByIdDadosBasicos(avaliado.getId(), null)).thenReturn(avaliado);
		 when(colaboradorManager.findByUsuarioProjection(anyLong(), any(Boolean.class))).thenReturn(avaliador);
		 when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 
		 gerenciadorComunicacaoManager.enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(avaliado.getId(), 1L, UsuarioFactory.getEntity(), empresa);
	
		 verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	 }
	 
	 @Test
	 public void testEnviaMensagemPeriodoExperienciaParaGestorAreaOrganizacionalPorEmail() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 
		 FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity(1L);
		 faixa1.setCargo(CargoFactory.getEntity());
		 faixa1.setDescricao("Faixa1");
		 
		 AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		 area.setDescricao("Area");
		 
		 Colaborador avaliador = ColaboradorFactory.getEntity(1L);
		 avaliador.setNome("Teo");
		 avaliador.setNomeComercial("Teo");
		 avaliador.setAreaOrganizacional(area);
		 avaliador.setFaixaSalarial(faixa1);
		 avaliador.setEmpresa(empresa);
		 
		 Colaborador avaliado = ColaboradorFactory.getEntity(1L);
		 avaliado.setNome("Leo");
		 avaliado.setNomeComercial("Leo");
		 avaliado.setAreaOrganizacional(area);
		 avaliado.setFaixaSalarial(faixa1);
		 avaliado.setEmpresa(empresa);
		 
		 Usuario usuario = UsuarioFactory.getEntity();
		 Collection<Usuario> usuarios = Arrays.asList(usuario);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 String[] emails = {};
		 ParametrosDoSistema parametroSistema = new ParametrosDoSistema();

				 
		 when(colaboradorManager.findByIdDadosBasicos(avaliado.getId(), null)).thenReturn(avaliado);
		 when(colaboradorManager.findByUsuarioProjection(anyLong(), any(Boolean.class))).thenReturn(avaliador);
		 when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 when(colaboradorManager.findEmailsByUsuarios(anyCollectionOf(Long.class))).thenReturn(emails);
		 when(parametrosDoSistemaManager.findById(1l)).thenReturn(parametroSistema);
		
		 gerenciadorComunicacaoManager.enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(avaliado.getId(), 1L, UsuarioFactory.getEntity(), empresa);
		 
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class));
	 }
	 
	 @Test
	 public void testEnviaMensagemAoExcluirRespostasAvaliacaoPeriodoDeExperiencia() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");

		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Elivaldo");
		 colaborador.setNomeComercial("Eli");
		 colaborador.setEmailColaborador("email@gmail.com");
		 colaborador.setEmpresa(empresa);
		 
		 Colaborador avaliado = ColaboradorFactory.getEntity(1L);
		 avaliado.setNome("Cristovão");
		 avaliado.setNomeComercial("Cris");
		 avaliado.setEmailColaborador("email@gmail.com");
		 avaliado.setEmpresa(empresa);

		 Usuario usuario = UsuarioFactory.getEntity();
		 usuario.setColaborador(colaborador);
		 Collection<Usuario> usuarios = Arrays.asList(usuario);

		 Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		 
		 ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		 colaboradorQuestionario.setColaborador(avaliado);
		 colaboradorQuestionario.setAvaliacao(avaliacao);
		 
		 GerenciadorComunicacao gerenciadorComunicacaoCaixaDeMensagem = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 GerenciadorComunicacao gerenciadorComunicacaoEmail = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacaoEmail.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacaoCaixaDeMensagem, gerenciadorComunicacaoEmail);

		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));
		 when(colaboradorManager.findEmailsByUsuarios(anyCollectionOf(Long.class))).thenReturn(new String[]{});

		 gerenciadorComunicacaoManager.enviarMensagemAoExluirRespostasAvaliacaoPeriodoDeExperiencia(colaboradorQuestionario, UsuarioFactory.getEntity(), empresa);
		
		 verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class));
	 }
	 
	 @Test
	 public void testEnviaMensagemCancelamentoSituacao() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setContato(new Contato());
		 colaborador.setEmpresa(empresa);

		 HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		 historicoColaborador.setColaborador(colaborador);
		 
		 String mensagem = "Teste";
		 
		 TSituacao situacao = new TSituacao();
		 situacao.setEmpresaCodigoAC("0010");
		 situacao.setGrupoAC("005");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 when(mensagemManager.formataMensagemCancelamentoHistoricoColaborador(anyString(),any(HistoricoColaborador.class))).thenReturn("teste");
		 when(usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC()),anyLong())).thenReturn(new ArrayList<UsuarioEmpresa>());
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CANCELAR_SITUACAO_AC.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 
	 	 gerenciadorComunicacaoManager.enviaMensagemCancelamentoSituacao(situacao, mensagem, historicoColaborador);
	 	 verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	 }
	 
	 @Test
	 public void testExisteConfiguracaoParaCandidatosModuloExterno()
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 Object[] valores = new Object[] {Operacao.CURRICULO_AGUARDANDO_APROVACAO_MODULO_EXTERNO.getId(), MeioComunicacao.CAIXA_MENSAGEM.getId(), EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getId(), empresa.getId()};
		 String [] propriedades = new String[]{"operacao", "meioComunicacao", "enviarPara", "empresa.id"};
		 
		 when(gerenciadorComunicacaoDao.verifyExists(eq(propriedades),eq(valores))).thenReturn(true);
		 
		 assertTrue(gerenciadorComunicacaoManager.existeConfiguracaoParaCandidatosModuloExterno(empresa.getId()));
	 }
	 
	 @Test
	 public void testEnviaAvisoDesligamentoColaboradorAC() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setEmpresa(empresa);
		 colaborador.setContato(new Contato());
		 colaborador.setAreaOrganizacional(areaOrganizacional);
		 colaborador.setUsuarioIdProjection(1L);
		 
		 TSituacao situacao = new TSituacao();
		 situacao.setEmpresaCodigoAC("0010");
		 situacao.setGrupoAC("005");
		 
		 String[] emails = new String[] {"email1@teste.com"};
		 
		 GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		 GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		 GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		 GerenciadorComunicacao gerenciadorComunicacao5 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacao5.setUsuarios(Arrays.asList(UsuarioFactory.getEntity(1L)));
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2, gerenciadorComunicacao3, gerenciadorComunicacao4, gerenciadorComunicacao5);
		 
		 Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		 colaboradores.add(colaborador);
		 
		 when(colaboradorManager.findColaboradoresByCodigoAC(eq(empresa.getId()),eq(true),anyString())).thenReturn(colaboradores);
		 when(usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC()),anyLong())).thenReturn(new ArrayList<UsuarioEmpresa>());
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.DESLIGAR_COLABORADOR_AC.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 when(areaOrganizacionalManager.getEmailsResponsaveis(eq(colaborador.getAreaOrganizacional().getId()),eq(colaborador.getEmpresa().getId()),eq(AreaOrganizacional.RESPONSAVEL),any(String.class))).thenReturn(emails);
		 when(areaOrganizacionalManager.getEmailsResponsaveis(eq(colaborador.getAreaOrganizacional().getId()),eq(colaborador.getEmpresa().getId()),eq(AreaOrganizacional.CORRESPONSAVEL),any(String.class))).thenReturn(emails);
		 when(comissaoMembroManager.colaboradoresComEstabilidade(any(Long[].class))).thenReturn(new HashMap<Long, Date>());
		 when(colaboradorManager.findEmailsByUsuarios(anyCollectionOf(Long.class))).thenReturn(emails);

		 gerenciadorComunicacaoManager.enviaAvisoDesligamentoColaboradorAC(situacao.getEmpresaCodigoAC(), situacao.getGrupoAC(), empresa, "001");
		 
		 verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
		 verify(mail,times(4)).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	 
	 @Test
	 public void testEnviaMensagemCancelamentoContratacao() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setCodigoAC("0001");
		 empresa.setGrupoAC("001");
		 
		 FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		 faixaSalarial.setCargo(CargoFactory.getEntity());
		 
		 HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		 historico.setEstabelecimento(EstabelecimentoFactory.getEntity());
		 historico.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity());
		 historico.setFaixaSalarial(faixaSalarial);
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setEmpresa(empresa);
		 colaborador.setHistoricoColaborador(historico);
		 
		 UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		 
		 ArrayList<UsuarioEmpresa> usuariosEmpresas = new ArrayList<UsuarioEmpresa>();
		 usuariosEmpresas.add(UsuarioEmpresaFactory.getEntity(1l));
		 
		 List<UsuarioEmpresa> usuariosEmpresa = usuariosEmpresas;
		 usuariosEmpresa.add(usuarioEmpresa);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
		 
		 when(usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(anyString(), anyString(),anyLong())).thenReturn(usuariosEmpresas);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CANCELAR_CONTRATACAO_AC.getId()),any(Long.class))).thenReturn(Arrays.asList(gerenciadorComunicacao));
		 
		 gerenciadorComunicacaoManager.enviaMensagemCancelamentoContratacao(colaborador, "mensagem do ac");
	
		 verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	 }
	 
	 @Test
	 public void testEnviaMensagemCancelamentoSolicitacaoDesligamentoAC() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setCodigoAC("0001");
		 empresa.setGrupoAC("001");
		 
		 FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		 faixaSalarial.setCargo(CargoFactory.getEntity());
		 
		 HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		 historico.setEstabelecimento(EstabelecimentoFactory.getEntity());
		 historico.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity());
		 historico.setFaixaSalarial(faixaSalarial);
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setEmpresa(empresa);
		 colaborador.setUsuario(new Usuario());
		 colaborador.setHistoricoColaborador(historico);
		 
		 UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		 
		 List<UsuarioEmpresa> usuariosEmpresa = new ArrayList<UsuarioEmpresa>();
		 usuariosEmpresa.add(usuarioEmpresa);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
		 
		 when(colaboradorManager.findByCodigoAC(eq(colaborador.getCodigoAC()),eq(empresa))).thenReturn(colaborador);
		 when(usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(anyString(), anyString(),anyLong())).thenReturn(usuariosEmpresa);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC.getId()),any(Long.class))).thenReturn(Arrays.asList(gerenciadorComunicacao));
		 
		 gerenciadorComunicacaoManager.enviaMensagemCancelamentoSolicitacaoDesligamentoAC(colaborador, "mensagem do ac", empresa.getCodigoAC(), empresa.getGrupoAC());
		 
		 verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	 }
	 
	 @Test
	 public void testEnviaAvisoContratacao() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setCodigoAC("0001");
		 
		 Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		 
		 FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		 faixaSalarial.setCargo(CargoFactory.getEntity());
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setEmpresa(empresa);
		 
		 HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity(colaborador, new Date(), faixaSalarial, estabelecimento, AreaOrganizacionalFactory.getEntity(), FuncaoFactory.getEntity(), AmbienteFactory.getEntity(), StatusRetornoAC.CONFIRMADO);

		 Usuario usuario = UsuarioFactory.getEntity();
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(Arrays.asList(usuario));
		 
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacao2.setUsuarios(Arrays.asList(usuario));
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacoes = Arrays.asList(gerenciadorComunicacao, gerenciadorComunicacao2); 
		 
		 when(historicoColaboradorManager.findByIdProjection(anyLong())).thenReturn(historico);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CONTRATAR_COLABORADOR.getId()),any(Long.class))).thenReturn(gerenciadorComunicacoes);
		 when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));
		 when(colaboradorManager.findEmailsByUsuarios(anyCollectionOf(Long.class))).thenReturn(new String[]{});
		 
		 gerenciadorComunicacaoManager.enviaAvisoContratacao(historico);
	
		 verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	 }
	 
	 @Test
	 public void testEnviaEmailConfiguracaoLimiteColaborador() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setEmailRespLimiteContrato("email@email.com");
		 
		 AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		 
		 ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = ConfiguracaoLimiteColaboradorFactory.getEntity();
		 configuracaoLimiteColaborador.setDescricao("Configuracao Limite Colaborador");
		 configuracaoLimiteColaborador.setAreaOrganizacional(areaOrganizacional);
		 
		 Cargo cargo = CargoFactory.getEntity(1L);
		 cargo.setNomeMercado("Cargo");
		 
		 QuantidadeLimiteColaboradoresPorCargo quantidadeLimiteColaboradoresPorCargo = new QuantidadeLimiteColaboradoresPorCargo();
		 quantidadeLimiteColaboradoresPorCargo.setCargo(cargo);
		 quantidadeLimiteColaboradoresPorCargo.setLimite(10);
		 
		 Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos = new ArrayList<QuantidadeLimiteColaboradoresPorCargo>();
		 quantidadeLimiteColaboradoresPorCargos.add(quantidadeLimiteColaboradoresPorCargo);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_LIMITE_CONTRATO);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 when(empresaManager.findByIdProjection(any(Long.class))).thenReturn(empresa);
		 when(areaOrganizacionalManager.findByIdProjection(configuracaoLimiteColaborador.getAreaOrganizacional().getId())).thenReturn(areaOrganizacional);
		 when(cargoManager.findByIdProjection(configuracaoLimiteColaborador.getAreaOrganizacional().getId())).thenReturn(cargo);
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CADASTRAR_LIMITE_COLABORADOR_CARGO.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 
		 gerenciadorComunicacaoManager.enviaEmailConfiguracaoLimiteColaborador(configuracaoLimiteColaborador, quantidadeLimiteColaboradoresPorCargos, empresa);
		
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	 }
	 
	 @Test
	 public void testEnviarAvisoEmail() throws AddressException, MessagingException 
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);

		 Curso curso = CursoFactory.getEntity();
		 curso.setNome("curso I");
		 
		 Turma turma = TurmaFactory.getEntity(2L);
		 turma.setCurso(curso);
		 turma.setDescricao("descricao");
		 turma.setDataPrevIni(new Date());
		 turma.setDataPrevFim(new Date());
		 turma.setHorario("comercial");
		 
		 Contato contato = new Contato();
		 contato.setEmail("email@email.com");
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setContato(contato);
		 
		 ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		 colaboradorTurma.setColaborador(colaborador);

		 when(empresaManager.findByIdProjection(any(Long.class))).thenReturn(empresa);
		 when(colaboradorTurmaManager.findColaboradoresComEmailByTurma(eq(turma.getId()),any(Boolean.class))).thenReturn(Arrays.asList(colaboradorTurma));
		 
		 gerenciadorComunicacaoManager.enviarAvisoEmail(turma, empresa.getId());
		
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
	}
	 
	 @Test
	 public void testEnviarAvisoEmailLiberacao() throws AddressException, MessagingException 
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 
		 Curso curso = CursoFactory.getEntity();
		 curso.setNome("curso I");
		 
		 Turma turma = TurmaFactory.getEntity(2L);
		 turma.setCurso(curso);
		 turma.setDescricao("descricao");
		 turma.setDataPrevIni(new Date());
		 turma.setDataPrevFim(new Date());
		 turma.setHorario("comercial");
		 
		 AvaliacaoTurma avaliacaoTurma = AvaliacaoTurmaFactory.getEntity(1L);
		 avaliacaoTurma.setProjectionQuestionarioTitulo("avaliacao final");
		 
		 Contato contato = new Contato();
		 contato.setEmail("email@email.com");
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setContato(contato);
		 
		 ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		 colaboradorTurma.setColaborador(colaborador);
		 
		 ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		 parametroSistema.setAppUrl("url");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.LIBERAR_AVALIACAO_TURMA.getId()),eq(empresa.getId()))).thenReturn(gerenciadorComunicacaos);
		 when(empresaManager.findByIdProjection(eq(empresa.getId()))).thenReturn(empresa);
		 when(colaboradorTurmaManager.findColaboradoresComEmailByTurma(eq(turma.getId()),any(Boolean.class))).thenReturn(Arrays.asList(colaboradorTurma));
		 when(parametrosDoSistemaManager.findById(1l)).thenReturn(parametroSistema);
		 
		 gerenciadorComunicacaoManager.enviarAvisoEmailLiberacao(turma, avaliacaoTurma, empresa.getId());
		 
		 verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString());
		 
	 }
	 
 	@Test
	public void testEnviaMensagemNotificacaoDeNaoAberturaSolicitacaoEpi()
	{
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setNomeComercial("Teo");
		 colaborador.setEmpresa(empresa);

		 Colaborador colaborador2 = ColaboradorFactory.getEntity(1L);
		 colaborador2.setNome("Leo");
		 colaborador2.setNomeComercial("Leo");
		 colaborador2.setEmpresa(empresa);

		 Usuario usuario = UsuarioFactory.getEntity();
		 Collection<Usuario> usuarios = Arrays.asList(usuario);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.NAO_ABERTURA_SOLICITACAO_EPI.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		 when(colaboradorManager.findAdmitidosHaDiasSemEpi(anyCollectionOf(Integer.class),eq(empresa.getId()))).thenReturn(Arrays.asList(colaborador, colaborador2));
		 when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(2L)));
		 
		 gerenciadorComunicacaoManager.enviaMensagemNotificacaoDeNaoAberturaSolicitacaoEpi();
		 
		 verify(usuarioMensagemManager,times(2)).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	}
 	
 	@Test
 	public void testEnviaAvisoOcorrenciaCadastrada()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa I");
		
		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity(1L);
		ocorrencia.setEmpresa(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Teo");
		colaborador.setNomeComercial("Teo");
		colaborador.setEmpresa(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		Collection<Usuario> usuarios = Arrays.asList(usuario);
		
		Providencia providencia = ProvidenciaFactory.getEntity(1L);
		
		ColaboradorOcorrencia colaboradorOocorrencia = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOocorrencia.setColaborador(colaborador);
		colaboradorOocorrencia.setOcorrencia(ocorrencia);
		colaboradorOocorrencia.setProvidencia(providencia);
		colaboradorOocorrencia.setDataIni(new Date());		
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setUsuarios(usuarios);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(colaboradorManager.findColaboradorByIdProjection(anyLong())).thenReturn(colaborador);
		when(providenciaManager.findById(anyLong())).thenReturn(providencia);
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.CADASTRAR_OCORRENCIA.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(1L)));
		when(parametrosDoSistemaManager.findById(1l)).thenReturn(parametroSistema);
	
		gerenciadorComunicacaoManager.enviaAvisoOcorrenciaCadastrada(colaboradorOocorrencia, empresa.getId());
		
		verify(usuarioMensagemManager).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	}
 	
 	@Test
 	public void testEnviaMensagemNotificacaoDeNaoEntregaSolicitacaoEpi()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa I");
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Teo");
		colaborador.setNomeComercial("Teo");
		colaborador.setEmpresa(empresa);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(1L);
		colaborador2.setNome("Leo");
		colaborador2.setNomeComercial("Leo");
		colaborador2.setEmpresa(empresa);
		
		Usuario usuario = UsuarioFactory.getEntity();
		Collection<Usuario> usuarios = Arrays.asList(usuario);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setUsuarios(usuarios);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.NAO_ENTREGA_SOLICITACAO_EPI.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		when(colaboradorManager.findAguardandoEntregaEpi(anyCollectionOf(Integer.class),eq(empresa.getId()))).thenReturn(Arrays.asList(colaborador, colaborador2));
		when(usuarioEmpresaManager.findUsuariosAtivo(anyCollectionOf(Long.class), anyLong())).thenReturn(Arrays.asList(UsuarioEmpresaFactory.getEntity(1L)));
		
		gerenciadorComunicacaoManager.enviaMensagemNotificacaoDeNaoEntregaSolicitacaoEpi();
		
		verify(usuarioMensagemManager,times(2)).saveMensagemAndUsuarioMensagem(anyString(), anyString(),anyString(), anyCollectionOf(UsuarioEmpresa.class), any(Colaborador.class), any(Character.class),any(Avaliacao.class), anyLong());
	}
 	
 	@Test
 	public void testEnviarEmailTerminoContratoTemporarioColaborador() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa I");
		empresa.setEmailRespRH("teste1@gmail.com;teste2@gmail.com;");
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Teo");
		colaborador.setNomeComercial("Teo");
		colaborador.setEmpresa(empresa);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(1L);
		colaborador2.setNome("Leo");
		colaborador2.setNomeComercial("Leo");
		colaborador2.setEmpresa(empresa);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		gerenciadorComunicacao.setQtdDiasLembrete("1");

		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		when(gerenciadorComunicacaoDao.findByOperacaoId(eq(Operacao.TERMINO_CONTRATO_COLABORADOR.getId()),any(Long.class))).thenReturn(gerenciadorComunicacaos);
		when(colaboradorManager.findParaLembreteTerminoContratoTemporario(anyCollectionOf(Integer.class),eq(empresa.getId()))).thenReturn(Arrays.asList(colaborador, colaborador2));

		gerenciadorComunicacaoManager.enviarEmailTerminoContratoTemporarioColaborador();

		verify(mail).send(eq(empresa),anyString(),anyString() ,any(File[].class),anyString(),anyString());
	}
 	
	private Empresa criaEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		empresa.setNome("Empresa I");
		empresa.setEmailRespRH("teste1@gmail.com;teste2@gmail.com;");
		empresa.setEmailRemetente("teste1@gmail.com");
		
		return empresa;
	}
	
	private Collection<ColaboradorTurma> retornaColaboradorTurmaComCursoECertificacoes(){
		Collection<ColaboradorTurma> colaboradoresTurmas = new ArrayList<ColaboradorTurma>();
		
		Certificacao certificacao1 =  CertificacaoFactory.getEntity();
		certificacao1.setNome("certificacaoNome1");
		
		Certificacao certificacao2 =  CertificacaoFactory.getEntity();
		certificacao2.setNome("certificacaoNome1");
		
		Curso curso = CursoFactory.getEntity();
		curso.setCertificacaos(Arrays.asList(certificacao1, certificacao2));
		
		ColaboradorTurma colaboradorTurma1 = criarColaboradorTurma();
		colaboradorTurma1.setCurso(curso);
		
		ColaboradorTurma colaboradorTurma2 = criarColaboradorTurma();
		colaboradorTurma2.setCurso(curso);
		
		colaboradoresTurmas.add(colaboradorTurma1);
		colaboradoresTurmas.add(colaboradorTurma2);
		
		return colaboradoresTurmas;
		
	}
	
	private ColaboradorTurma criarColaboradorTurma(){
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		Cargo cargo = CargoFactory.getEntity();
		faixaSalarial.setCargo(cargo);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmailColaborador("email@email.com");
		Curso curso = CursoFactory.getEntity();
		
		colaborador.setAreaOrganizacional(areaOrganizacional);
		colaborador.setFaixaSalarial(faixaSalarial);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setNome("Java Básico");
		
		Turma turma = TurmaFactory.getEntity();
		turma.setVencimento(DateUtil.incrementa(new Date(),1, 2));
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setCertificacaoNome(certificacao.getNome());
		
		return colaboradorTurma;
	}
}
