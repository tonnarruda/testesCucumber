package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

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
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
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
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAutorizacaoGestor;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

public class GerenciadorComunicacaoManagerTest_Junit4
{
	private GerenciadorComunicacaoManagerImpl gerenciadorComunicacaoManager = new GerenciadorComunicacaoManagerImpl();
	private GerenciadorComunicacaoDao gerenciadorComunicacaoDao;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
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
	private ExameManager exameManager;
	private CargoManager cargoManager;
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

        mail = mock(Mail.class);
        gerenciadorComunicacaoManager.setMail(mail);

        colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
        MockSpringUtilJUnit4.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);

        solicitacaoManager = mock(SolicitacaoManager.class);
		MockSpringUtilJUnit4.mocks.put("solicitacaoManager", solicitacaoManager);

		providenciaManager = mock(ProvidenciaManager.class);
		gerenciadorComunicacaoManager.setProvidenciaManager(providenciaManager);
		
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

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
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
		
		mocksEnviarAvisoAoAlterarStatusColaboradorSolPessoal(empresa, areaOrganizacional, areasOrganizacionais, colaborador, null, solicitacao, null, parametrosDoSistema, emails, usuariosEmpresa, gerenciadorComunicacaos);
		
		Exception ex = null;
		try {
			gerenciadorComunicacaoManager.enviarAvisoAoInserirColaboradorSolPessoal(empresa, usuarioLogado, colaborador.getId(), solicitacao.getId());
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
	
	private void mocksEnviarAvisoAoAlterarStatusColaboradorSolPessoal(Empresa empresa, AreaOrganizacional areaOrganizacional, Collection<AreaOrganizacional> areasOrganizacionais, Colaborador colaborador, Usuario usuarioSolicitante, Solicitacao solicitacao, CandidatoSolicitacao candidatoSolicitacaoAnterior, ParametrosDoSistema parametrosDoSistema, String[] emails, Collection<UsuarioEmpresa> usuariosEmpresa, Collection<GerenciadorComunicacao> gerenciadorComunicacaos) throws Exception {
		if(candidatoSolicitacaoAnterior != null){
			when(solicitacaoManager.findById(candidatoSolicitacaoAnterior.getSolicitacao().getId())).thenReturn(solicitacao);
			when(colaboradorManager.findByCandidato(candidatoSolicitacaoAnterior.getCandidato().getId(), empresa.getId())).thenReturn(colaborador);
			when(usuarioManager.findById(candidatoSolicitacaoAnterior.getUsuarioSolicitanteAutorizacaoGestor().getId())).thenReturn(usuarioSolicitante);
		}
		
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(colaboradorManager.findByIdDadosBasicos(colaborador.getId(), null)).thenReturn(colaborador);
		when(areaOrganizacionalManager.findAllListAndInativas(true, null, empresa.getId())).thenReturn(areasOrganizacionais);
		when(gerenciadorComunicacaoDao.findByOperacaoId(Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId(), empresa.getId())).thenReturn(gerenciadorComunicacaos);
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
}
