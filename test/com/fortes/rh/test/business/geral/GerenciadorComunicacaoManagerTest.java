package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManagerImpl;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
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
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.dao.hibernate.pesquisa.AvaliacaoTurmaFactory;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoLimiteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.geral.ProvidenciaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

public class GerenciadorComunicacaoManagerTest extends MockObjectTestCase
{
	private GerenciadorComunicacaoManagerImpl gerenciadorComunicacaoManager = new GerenciadorComunicacaoManagerImpl();
	private Mock gerenciadorComunicacaoDao;
	private Mock candidatoSolicitacaoManager;
	private Mock historicoColaboradorManager;
	private Mock parametrosDoSistemaManager;
	private Mock periodoExperienciaManager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorTurmaManager;
	private Mock usuarioMensagemManager;
	private Mock usuarioEmpresaManager;
	private Mock questionarioManager;
	private Mock colaboradorManager;
	private Mock solicitacaoManager;
	private Mock providenciaManager;
	private Mock mensagemManager;
	private Mock empresaManager;
	private Mock usuarioManager;
	private Mock exameManager;
	private Mock cargoManager;
	private Mock mail;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        gerenciadorComunicacaoDao = new Mock(GerenciadorComunicacaoDao.class);
        gerenciadorComunicacaoManager.setDao((GerenciadorComunicacaoDao) gerenciadorComunicacaoDao.proxy());
        
        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        gerenciadorComunicacaoManager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
        
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        gerenciadorComunicacaoManager.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        periodoExperienciaManager = new Mock(PeriodoExperienciaManager.class);
        gerenciadorComunicacaoManager.setPeriodoExperienciaManager((PeriodoExperienciaManager) periodoExperienciaManager.proxy());
        
        empresaManager = new Mock(EmpresaManager.class);
        gerenciadorComunicacaoManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        
        usuarioMensagemManager = new Mock(UsuarioMensagemManager.class);
        gerenciadorComunicacaoManager.setUsuarioMensagemManager((UsuarioMensagemManager) usuarioMensagemManager.proxy());

        usuarioEmpresaManager = new Mock(UsuarioEmpresaManager.class);
        gerenciadorComunicacaoManager.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());

        mensagemManager = new Mock(MensagemManager.class);
        gerenciadorComunicacaoManager.setMensagemManager((MensagemManager) mensagemManager.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        gerenciadorComunicacaoManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        MockSpringUtil.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);
        
        cargoManager = new Mock(CargoManager.class);
        gerenciadorComunicacaoManager.setCargoManager((CargoManager) cargoManager.proxy());

        mail = mock(Mail.class);
        gerenciadorComunicacaoManager.setMail((Mail) mail.proxy());

        solicitacaoManager = new Mock(SolicitacaoManager.class);
		MockSpringUtil.mocks.put("solicitacaoManager", solicitacaoManager);

		providenciaManager = new Mock(ProvidenciaManager.class);
		gerenciadorComunicacaoManager.setProvidenciaManager((ProvidenciaManager) providenciaManager.proxy());
		
		colaboradorManager = new Mock(ColaboradorManager.class);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		MockSpringUtil.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
		
		usuarioManager = new Mock(UsuarioManager.class);
		MockSpringUtil.mocks.put("usuarioManager", usuarioManager);
		
		questionarioManager = new Mock(QuestionarioManager.class);
		MockSpringUtil.mocks.put("questionarioManager", questionarioManager);
		
		exameManager = new Mock(ExameManager.class);
		MockSpringUtil.mocks.put("exameManager", exameManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
    }
	
	public void testeInsereGerenciadorComunicacaoDefault() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();
		gerenciadorComunicacaoDao.expects(once()).method("save").withAnyArguments().isVoid();

		// se for inserir mais  um defalt terá de aterar no importador o método insereGerenciadorComunicacaoDefault(empresa) em empresaJDBC.
		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.insereGerenciadorComunicacaoDefault(empresa);
		} catch (Exception e) {
			exception = e;
		}

		assertNull(exception);
	}

	public void testExecuteEncerrarSolicitacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setEmailCandidatoNaoApto(Boolean.TRUE);
		empresa.setMailNaoAptos("Envio de email");
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		gerenciadorComunicacao.setEnviarPara(EnviarPara.CANDIDATO_NAO_APTO.getId());
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		candidatoSolicitacaoManager.expects(once()).method("getEmailNaoAptos").will(returnValue(new String[] {"teste@teste.com.br"}));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").will(returnValue(gerenciadorComunicacaos));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});

		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.enviaEmailCandidatosNaoAptos(empresa, 1L);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testEnviaEmailSolicitanteSolicitacao()
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
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		gerenciadorComunicacao.setEmpresa(empresa);
		gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		gerenciadorComunicacao.setEnviarPara(EnviarPara.SOLICITANTE_SOLICITACAO.getId());

		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametros));
		colaboradorManager.expects(once()).method("findByUsuarioProjection").with(eq(solicitacao.getSolicitante().getId())).will(returnValue(solicitante));
		colaboradorManager.expects(once()).method("findByUsuarioProjection").with(eq(usuarioLiberador.getId())).will(returnValue(liberador));
		solicitacaoManager.expects(atLeastOnce()).method("montaCorpoEmailSolicitacao").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.ALTERAR_STATUS_SOLICITACAO.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});

		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.enviaEmailSolicitanteSolicitacao(solicitacao, empresa, usuarioLiberador);
		} catch (Exception e) {
			exception = e;
		}

		assertNull(exception);

	}
	
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

		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 colaboradorManager.expects(once()).method("findColaboradorDeAvaliacaoDesempenhoNaoRespondida").will(returnValue(Arrays.asList(colaborador1, colaborador2)));
		 parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametros));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});

		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviarLembreteResponderAvaliacaoDesempenho();
		 } catch (Exception e) {
			 exception = e;
		 }

		 assertNull(exception);
	 }

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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.COLABORADOR.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametros));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.LIBERAR_PESQUISA.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaEmailQuestionarioLiberado(empresa, questionario, colaboradorQuestionarios);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RESPONSAVEL_RH.getId());
		 gerenciadorComunicacao.setQtdDiasLembrete("1");
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 questionarioManager.expects(atLeastOnce()).method("findQuestionarioNaoLiberados").with(ANYTHING).will(returnValue(questionarios));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.PESQUISA_NAO_LIBERADA.getId()), eq(null)).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaLembreteDeQuestionarioNaoLiberado();
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
	 public void testEnviaEmailResponsavelRh() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RESPONSAVEL_RH.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 empresaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(empresa));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_CANDIDATO_MODULO_EXTERNO.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaEmailResponsavelRh("Chico", empresa.getId());
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RESPONSAVEL_RH.getId());
		 
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.QTD_CURRICULOS_CADASTRADOS.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaEmailQtdCurriculosCadastrados(empresas, inicioMes, fimMes, candidatos);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
	 public void testEnviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo() throws Exception
	 {
		 ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity(1L);
		 parametros.setAppUrl("url");
		 
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Contato contato = new Contato();
		 contato.setEmail("email@email.com");
		 
		 Colaborador teo = ColaboradorFactory.getEntity(1L);
		 teo.setNome("Teo");
		 teo.setEmpresa(empresa);
		 teo.setContato(contato);
		 
		 Colaborador leo = ColaboradorFactory.getEntity(2L);
		 leo.setNome("Leo");
		 leo.setEmpresa(empresa);
		 leo.setContato(contato);
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.COLABORADOR_AVALIADO.getId()); 
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametros));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo(colaboradores);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }

	 public void testEnviaLembreteExamesPrevistos() throws Exception
	 {
		 ParametrosDoSistema parametros = ParametrosDoSistemaFactory.getEntity(1L);
		 parametros.setAppUrl("url");
		 parametros.setAppVersao("1");
		 
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Collection<Empresa> empresas = Arrays.asList(empresa);
		 
		 Collection<String> emails = Arrays.asList("email@email.com");
		 
		 Usuario usuario = UsuarioFactory.getEntity();
		 Collection<Usuario> usuarios = Arrays.asList(usuario);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.USUARIOS.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 Collection<Exame> examesPrevistos = Arrays.asList(ExameFactory.getEntity()); 
		 
		 parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametros));
		 exameManager.expects(once()).method("findRelatorioExamesPrevistos").with(new Constraint[] {eq(empresa.getId()), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(examesPrevistos));
		 
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.EXAMES_PREVISTOS.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 colaboradorManager.expects(once()).method("findEmailsByUsuarios").withAnyArguments();
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaLembreteExamesPrevistos(empresas);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao1.setEmpresa(empresa);
		 gerenciadorComunicacao1.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao1.setEnviarPara(EnviarPara.GESTOR_AREA.getId());
		 gerenciadorComunicacao1.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao2.setEmpresa(empresa);
		 gerenciadorComunicacao2.setUsuarios(usuarios);
		 gerenciadorComunicacao2.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao2.setEnviarPara(EnviarPara.USUARIOS.getId());
		 gerenciadorComunicacao2.setQtdDiasLembrete("1");
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2);
		 
		 periodoExperienciaManager.expects(once()).method("findAll").will(returnValue(periodoExperiencias));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		 colaboradorManager.expects(once()).method("findAdmitidosHaDias").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradors));
		 colaboradorManager.expects(once()).method("findAdmitidosHaDias").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradors));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagemRespAreaOrganizacional").withAnyArguments().isVoid();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemLembretePeriodoExperiencia();
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
	 public void testNotificaBackup()
	 {
		 ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		 parametroSistema.setAppUrl("url");
		 parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RESPONSAVEL_TECNICO.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.GERAR_BACKUP.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));

		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});

		 Exception exception = null;
		 try {

			 gerenciadorComunicacaoManager.notificaBackup("dump.sql");
		 } catch (Exception e) {
			 exception = e;
		 }

		 assertNull(exception);
	 }
	
	 public void testEnviarEmailContratacaoColaborador() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Colaborador teo = ColaboradorFactory.getEntity(1L);
		 teo.setNome("Teo");
		 teo.setEmpresa(empresa);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RESPONSAVEL_SETOR_PESSOAL.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CONTRATAR_COLABORADOR_AC.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviarEmailContratacaoColaborador(teo.getNome(), empresa);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
	 public void testEnviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional() throws Exception
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.USUARIOS.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(ANYTHING, ANYTHING).will(returnValue(avaliado));
		 colaboradorManager.expects(once()).method("findByUsuarioProjection").with(ANYTHING).will(returnValue(avaliador));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(avaliado.getId(), 1L, UsuarioFactory.getEntity(), empresa);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }

	 public void testEnviaMensagemCancelamentoSituacao() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setEmpresa(empresa);

		 HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		 historicoColaborador.setColaborador(colaborador);
		 
		 String mensagem = "Teste";
		 
		 TSituacao situacao = new TSituacao();
		 situacao.setEmpresaCodigoAC("0010");
		 situacao.setGrupoAC("005");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 mensagemManager.expects(once()).method("formataMensagemCancelamentoHistoricoColaborador").with(eq(mensagem), eq(historicoColaborador)).will(returnValue("Teste"));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").with(eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CANCELAR_SITUACAO_AC.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemCancelamentoSituacao(situacao, mensagem, historicoColaborador);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 
	 }
	 public void testExisteConfiguracaoParaCandidatosModuloExterno()
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 Object[] valores = new Object[] {Operacao.CURRICULO_AGUARDANDO_APROVACAO_MODULO_EXTERNO.getId(), MeioComunicacao.CAIXA_MENSAGEM.getId(), EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getId(), empresa.getId()};
		 gerenciadorComunicacaoDao.expects(once())
		 									.method("verifyExists")
		 									.with(eq(new String[]{"operacao", "meioComunicacao", "enviarPara", "empresa.id"}),eq(valores))
		 									.will(returnValue(true));
		 assertTrue(gerenciadorComunicacaoManager.existeConfiguracaoParaCandidatosModuloExterno(empresa.getId()));
	 }

	 public void testEnviaMensagemDesligamentoColaboradorAC() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setEmpresa(empresa);
		 
		 TSituacao situacao = new TSituacao();
		 situacao.setEmpresaCodigoAC("0010");
		 situacao.setGrupoAC("005");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 colaboradorManager.expects(once()).method("findByCodigoAC").with(ANYTHING, eq(empresa)).will(returnValue(colaborador));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").with(eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.DESLIGAR_COLABORADOR_AC.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemDesligamentoColaboradorAC("001", situacao.getEmpresaCodigoAC(), situacao.getGrupoAC(), empresa);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId());

		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").withAnyArguments().will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CANCELAR_CONTRATACAO_AC.getId()),ANYTHING).will(returnValue(Arrays.asList(gerenciadorComunicacao)));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemCancelamentoContratacao(colaborador, "mensagem do ac");
		 } catch (Exception e) {
			 exception = e;
		 }
		 assertNull(exception);
	 }

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
		 colaborador.setHistoricoColaborador(historico);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL.getId());
		 
		 colaboradorManager.expects(once()).method("findByCodigoAC").with(eq(colaborador.getCodigoAC()), eq(empresa)).will(returnValue(colaborador));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").withAnyArguments().will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC.getId()),ANYTHING).will(returnValue(Arrays.asList(gerenciadorComunicacao)));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemCancelamentoSolicitacaoDesligamentoAC(colaborador, "mensagem do ac", empresa.getCodigoAC(), empresa.getGrupoAC());
		 } catch (Exception e) {
			 exception = e;
		 }
		 assertNull(exception);
	 }
	 
	 
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
		 
		 HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		 historico.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity());
		 historico.setFaixaSalarial(faixaSalarial);
		 historico.setEstabelecimento(estabelecimento);
		 historico.setColaborador(colaborador);
		 historico.setFuncao(FuncaoFactory.getEntity());
		 historico.setAmbiente(AmbienteFactory.getEntity());

		 Usuario usuario = UsuarioFactory.getEntity();
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.USUARIOS.getId());
		 gerenciadorComunicacao.setUsuarios(Arrays.asList(usuario));
		 
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao2.setEmpresa(empresa);
		 gerenciadorComunicacao2.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao2.setEnviarPara(EnviarPara.USUARIOS.getId());
		 gerenciadorComunicacao2.setUsuarios(Arrays.asList(usuario));
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacoes = Arrays.asList(gerenciadorComunicacao, gerenciadorComunicacao2); 
		 
		 historicoColaboradorManager.expects(once()).method("findByIdProjection").withAnyArguments().will(returnValue(historico));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.CONTRATAR_COLABORADOR.getId()),ANYTHING).will(returnValue(gerenciadorComunicacoes));
		 usuarioEmpresaManager.expects(atLeastOnce()).method("findUsuariosAtivo").withAnyArguments().will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 usuarioManager.expects(atLeastOnce()).method("findEmailsByUsuario").withAnyArguments().will(returnValue(new String[]{}));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaAvisoContratacao(historico);
		 } catch (Exception e) {
			 exception = e;
		 }
		 assertNull(exception);
	 }
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RESPONSAVEL_LIMITE_CONTRATO.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));
		 areaOrganizacionalManager.expects(once()).method("findByIdProjection").with(eq(configuracaoLimiteColaborador.getAreaOrganizacional().getId())).will(returnValue(areaOrganizacional));
		 cargoManager.expects(once()).method("findByIdProjection").with(eq(configuracaoLimiteColaborador.getAreaOrganizacional().getId())).will(returnValue(cargo));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_LIMITE_COLABORADOR_CARGO.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaEmailConfiguracaoLimiteColaborador(configuracaoLimiteColaborador, quantidadeLimiteColaboradoresPorCargos, empresa);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
	 public void testEnviarAvisoEmail() 
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

		 empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));
		 colaboradorTurmaManager.expects(once()).method("findColaboradoresComEmailByTurma").with(eq(turma.getId())).will(returnValue(Arrays.asList(colaboradorTurma)));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviarAvisoEmail(turma, empresa.getId());
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
		
	}
	 public void testEnviarAvisoEmailLiberacao() 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.COLABORADOR.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.LIBERAR_AVALIACAO_TURMA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));
		 colaboradorTurmaManager.expects(once()).method("findColaboradoresComEmailByTurma").with(eq(turma.getId())).will(returnValue(Arrays.asList(colaboradorTurma)));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviarAvisoEmailLiberacao(turma, avaliacaoTurma, empresa.getId());
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
		 
	 }
	 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.USUARIOS.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.NAO_ABERTURA_SOLICITACAO_EPI.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		 colaboradorManager.expects(once()).method("findAdmitidosHaDiasSemEpi").with(ANYTHING, ANYTHING).will(returnValue(Arrays.asList(colaborador, colaborador2)));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemNotificacaoDeNaoAberturaSolicitacaoEpi();
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	}

	public void testEnviaAvisoOcorrenciaCadastrada()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa I");
		
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
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		gerenciadorComunicacao.setEmpresa(empresa);
		gerenciadorComunicacao.setUsuarios(usuarios);
		gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		gerenciadorComunicacao.setEnviarPara(EnviarPara.USUARIOS.getId());
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		colaboradorManager.expects(once()).method("findColaboradorByIdProjection").with(ANYTHING).will(returnValue(colaborador));
		providenciaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(providencia));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_OCORRENCIA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		
		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.enviaAvisoOcorrenciaCadastrada(colaboradorOocorrencia, empresa.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
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
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		gerenciadorComunicacao.setEmpresa(empresa);
		gerenciadorComunicacao.setUsuarios(usuarios);
		gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		gerenciadorComunicacao.setEnviarPara(EnviarPara.USUARIOS.getId());
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.NAO_ENTREGA_SOLICITACAO_EPI.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		colaboradorManager.expects(once()).method("findAguardandoEntregaEpi").with(ANYTHING, ANYTHING).will(returnValue(Arrays.asList(colaborador, colaborador2)));
		usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		
		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.enviaMensagemNotificacaoDeNaoEntregaSolicitacaoEpi();
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testEnviarEmailTerminoContratoTemporarioColaborador()
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
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		gerenciadorComunicacao.setEmpresa(empresa);
		gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		gerenciadorComunicacao.setEnviarPara(EnviarPara.RESPONSAVEL_RH.getId());
		gerenciadorComunicacao.setQtdDiasLembrete("1");

		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.TERMINO_CONTRATO_COLABORADOR.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		colaboradorManager.expects(atLeastOnce()).method("findParaLembreteTerminoContratoTemporario").with(ANYTHING, ANYTHING).will(returnValue(Arrays.asList(colaborador, colaborador2)));
		mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		Exception exception = null;
		try {
			gerenciadorComunicacaoManager.enviarEmailTerminoContratoTemporarioColaborador();
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
}
