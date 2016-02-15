package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mockit.Mockit;

import org.apache.commons.lang.StringUtils;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

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
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
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
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
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
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
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
	private Mock motivoSolicitacaoManager;
	private Mock colaboradorTurmaManager;
	private Mock usuarioMensagemManager;
	private Mock estabelecimentoManager;
	private Mock usuarioEmpresaManager;
	private Mock comissaoMembroManager;
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
        
		comissaoMembroManager = new Mock(ComissaoMembroManager.class);
		gerenciadorComunicacaoManager.setComissaoMembroManager((ComissaoMembroManager) comissaoMembroManager.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        gerenciadorComunicacaoManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        motivoSolicitacaoManager = new Mock(MotivoSolicitacaoManager.class);
        gerenciadorComunicacaoManager.setMotivoSolicitacaoManager((MotivoSolicitacaoManager) motivoSolicitacaoManager.proxy());
        
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
		
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		MockSpringUtil.mocks.put("estabelecimentoManager", estabelecimentoManager);

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
		empresa.setMailNaoAptos("Envio de email");
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.CANDIDATO_NAO_APTO);
		
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
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.SOLICITANTE_SOLICITACAO);

		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametros));
		colaboradorManager.expects(once()).method("findByUsuarioProjection").with(eq(solicitacao.getSolicitante().getId()), eq(true)).will(returnValue(solicitante));
		colaboradorManager.expects(once()).method("findByUsuarioProjection").with(eq(usuarioLiberador.getId()), eq(null)).will(returnValue(liberador));
		gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.ALTERAR_STATUS_SOLICITACAO.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		mail.expects(atLeastOnce()).method("send").withAnyArguments();

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

		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.AVALIADOR_AVALIACAO_DESEMPENHO);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 colaboradorManager.expects(once()).method("findColaboradorDeAvaliacaoDesempenhoNaoRespondida").will(returnValue(Arrays.asList(colaborador1, colaborador2)));
		 parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametros));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").withAnyArguments();

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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametros));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.LIBERAR_PESQUISA.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").withAnyArguments();
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR_AVALIADO);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametros));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 
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
		 
		 Usuario usuario = UsuarioFactory.getEntity();
		 Collection<Usuario> usuarios = Arrays.asList(usuario);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 Collection<Exame> examesPrevistos = Arrays.asList(ExameFactory.getEntity()); 
		 
		 parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametros));
		 exameManager.expects(once()).method("findRelatorioExamesPrevistos").with(new Constraint[] {eq(empresa.getId()), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(examesPrevistos));
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		 gerenciadorComunicacao1.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 gerenciadorComunicacao2.setUsuarios(usuarios);
		 gerenciadorComunicacao2.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		 gerenciadorComunicacao3.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		 gerenciadorComunicacao4.setQtdDiasLembrete("1");
		 
		 GerenciadorComunicacao gerenciadorComunicacao5 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		 gerenciadorComunicacao5.setQtdDiasLembrete("1");
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2, gerenciadorComunicacao3, gerenciadorComunicacao4, gerenciadorComunicacao5);
		 
		 String[] emails = new String[] {"email1@teste.com"};
		 
		 periodoExperienciaManager.expects(once()).method("findToList").will(returnValue(periodoExperiencias));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		 colaboradorManager.expects(atLeastOnce()).method("findAdmitidosHaDias").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradors));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagemRespAreaOrganizacional").withAnyArguments().isVoid();
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional").withAnyArguments().isVoid();
		 areaOrganizacionalManager.expects(once()).method("getEmailsResponsaveis").with(eq(teo.getAreaOrganizacional().getId()), eq(teo.getEmpresa().getId()), eq(AreaOrganizacional.RESPONSAVEL)).will(returnValue(emails));
		 areaOrganizacionalManager.expects(once()).method("getEmailsResponsaveis").with(eq(teo.getAreaOrganizacional().getId()), eq(teo.getEmpresa().getId()), eq(AreaOrganizacional.CORRESPONSAVEL)).will(returnValue(emails));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(null, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_TECNICO);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.GERAR_BACKUP.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));

		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING});

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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_SETOR_PESSOAL);
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(ANYTHING, ANYTHING).will(returnValue(avaliado));
		 colaboradorManager.expects(once()).method("findByUsuarioProjection").with(ANYTHING, ANYTHING).will(returnValue(avaliador));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(avaliado.getId(), 1L, UsuarioFactory.getEntity(), empresa);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 String[] emails = {};
		 ParametrosDoSistema parametroSistema = new ParametrosDoSistema();

		 
		 colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(ANYTHING, ANYTHING).will(returnValue(avaliado));
		 colaboradorManager.expects(once()).method("findByUsuarioProjection").with(ANYTHING, ANYTHING).will(returnValue(avaliador));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 colaboradorManager.expects(once()).method("findEmailsByUsuarios").with(ANYTHING).will(returnValue(emails));
		 parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaMensagemPeriodoExperienciaParaGestorAreaOrganizacional(avaliado.getId(), 1L, UsuarioFactory.getEntity(), empresa);
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }

	 public void testEnviaMensagemAoExluirRespostasAvaliacaoPeriodoDeExperiencia() throws Exception
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
		 
		 GerenciadorComunicacao gerenciadorComunicacaoCaixaDeMensagem = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 GerenciadorComunicacao gerenciadorComunicacaoEmail = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacaoEmail.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacaoCaixaDeMensagem, gerenciadorComunicacaoEmail);

		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		 colaboradorManager.expects(once()).method("findEmailsByUsuarios").with(ANYTHING);
		 mail.expects(atLeastOnce()).method("send").withAnyArguments();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviarMensagemAoExluirRespostasAvaliacaoPeriodoDeExperiencia(colaboradorQuestionario, UsuarioFactory.getEntity(), empresa);
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 mensagemManager.expects(once()).method("formataMensagemCancelamentoHistoricoColaborador").with(eq(mensagem), eq(historicoColaborador)).will(returnValue("Teste"));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").with(eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CANCELAR_SITUACAO_AC.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments();
		 
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

	 public void testEnviaAvisoDesligamentoColaboradorAC() throws Exception
	 {
		 Empresa empresa = EmpresaFactory.getEmpresa(1L);
		 empresa.setNome("Empresa I");
		 empresa.setEmailRespRH("email@email.com");
		 
		 AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		 
		 Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		 colaborador.setNome("Teo");
		 colaborador.setEmpresa(empresa);
		 colaborador.setAreaOrganizacional(areaOrganizacional);
		 
		 TSituacao situacao = new TSituacao();
		 situacao.setEmpresaCodigoAC("0010");
		 situacao.setGrupoAC("005");
		 
		 String[] emails = new String[] {"email1@teste.com"};
		 
		 GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		 GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		 GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2, gerenciadorComunicacao3, gerenciadorComunicacao4);
		 
		 Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		 colaboradores.add(colaborador);
		 
		 colaboradorManager.expects(once()).method("findColaboradoresByCodigoAC").with(eq(empresa.getId()), eq(true), ANYTHING).will(returnValue(colaboradores));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").with(eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.DESLIGAR_COLABORADOR_AC.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments();
		 areaOrganizacionalManager.expects(once()).method("getEmailsResponsaveis").with(eq(colaborador.getAreaOrganizacional().getId()), eq(colaborador.getEmpresa().getId()), eq(AreaOrganizacional.RESPONSAVEL)).will(returnValue(emails));
		 areaOrganizacionalManager.expects(once()).method("getEmailsResponsaveis").with(eq(colaborador.getAreaOrganizacional().getId()), eq(colaborador.getEmpresa().getId()), eq(AreaOrganizacional.CORRESPONSAVEL)).will(returnValue(emails));
		 comissaoMembroManager.expects(once()).method("colaboradoresComEstabilidade").withAnyArguments().will(returnValue(new HashMap<Long, Date>()));
		 mail.expects(atLeastOnce()).method("send").withAnyArguments();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaAvisoDesligamentoColaboradorAC(situacao.getEmpresaCodigoAC(), situacao.getGrupoAC(), empresa, "001");
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
		 
		 UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		 
		 List<UsuarioEmpresa> usuariosEmpresa = new ArrayList<UsuarioEmpresa>();
		 usuariosEmpresa.add(usuarioEmpresa);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);

		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").withAnyArguments().will(returnValue(usuariosEmpresa));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CANCELAR_CONTRATACAO_AC.getId()),ANYTHING).will(returnValue(Arrays.asList(gerenciadorComunicacao)));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments();
		 
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
		 
		 UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		 
		 List<UsuarioEmpresa> usuariosEmpresa = new ArrayList<UsuarioEmpresa>();
		 usuariosEmpresa.add(usuarioEmpresa);
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.RECEBE_MENSAGEM_AC_PESSOAL);
		 
		 colaboradorManager.expects(once()).method("findByCodigoAC").with(eq(colaborador.getCodigoAC()), eq(empresa)).will(returnValue(colaborador));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleSetorPessoal").withAnyArguments().will(returnValue(usuariosEmpresa));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC.getId()),ANYTHING).will(returnValue(Arrays.asList(gerenciadorComunicacao)));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments();
		 
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
		 
		 HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity(colaborador, new Date(), faixaSalarial, estabelecimento, AreaOrganizacionalFactory.getEntity(), FuncaoFactory.getEntity(), AmbienteFactory.getEntity(), StatusRetornoAC.CONFIRMADO);

		 Usuario usuario = UsuarioFactory.getEntity();
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(Arrays.asList(usuario));
		 
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		 gerenciadorComunicacao2.setUsuarios(Arrays.asList(usuario));
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacoes = Arrays.asList(gerenciadorComunicacao, gerenciadorComunicacao2); 
		 
		 historicoColaboradorManager.expects(once()).method("findByIdProjection").withAnyArguments().will(returnValue(historico));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.CONTRATAR_COLABORADOR.getId()),ANYTHING).will(returnValue(gerenciadorComunicacoes));
		 usuarioEmpresaManager.expects(atLeastOnce()).method("findUsuariosAtivo").withAnyArguments().will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 usuarioManager.expects(atLeastOnce()).method("findEmailsByUsuario").withAnyArguments().will(returnValue(new String[]{}));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments();
		 mail.expects(once()).method("send").withAnyArguments();
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_LIMITE_CONTRATO);
		 
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
		 colaboradorTurmaManager.expects(once()).method("findColaboradoresComEmailByTurma").with(eq(turma.getId()), ANYTHING).will(returnValue(Arrays.asList(colaboradorTurma)));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviarAvisoEmail(turma, empresa.getId());
		 } catch (Exception e) {
			 e.printStackTrace();
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
		 
		 ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		 parametroSistema.setAppUrl("url");
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.LIBERAR_AVALIACAO_TURMA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));
		 colaboradorTurmaManager.expects(once()).method("findColaboradoresComEmailByTurma").with(eq(turma.getId()), ANYTHING).will(returnValue(Arrays.asList(colaboradorTurma)));
		 parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		 gerenciadorComunicacao.setUsuarios(usuarios);
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);

		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.NAO_ABERTURA_SOLICITACAO_EPI.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		 colaboradorManager.expects(once()).method("findAdmitidosHaDiasSemEpi").with(ANYTHING, ANYTHING).will(returnValue(Arrays.asList(colaborador, colaborador2)));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		 usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		 
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
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setUsuarios(usuarios);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		colaboradorManager.expects(once()).method("findColaboradorByIdProjection").with(ANYTHING).will(returnValue(colaborador));
		providenciaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(providencia));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_OCORRENCIA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));;
		
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
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao.setUsuarios(usuarios);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.NAO_ENTREGA_SOLICITACAO_EPI.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		colaboradorManager.expects(once()).method("findAguardandoEntregaEpi").with(ANYTHING, ANYTHING).will(returnValue(Arrays.asList(colaborador, colaborador2)));
		usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").withAnyArguments();
		usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		
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
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
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
	
	public void testEnviaMensagemCadastroSituacaoAC()
	{
		Empresa empresa = criaEmpresa();
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "Colaborador", null, null, null, null, null);
		
		TSituacao situacao = new TSituacao();
		situacao.setEmpresaCodigoAC("001");
		situacao.setGrupoAC("002");
		situacao.setLotacaoCodigoAC("003");
		situacao.setData("05/02/2013");
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		Usuario usuario = UsuarioFactory.getEntity();
		Collection<Usuario> usuarios = Arrays.asList(usuario);

		GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao3.setUsuarios(usuarios);
		
		GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		
		GerenciadorComunicacao gerenciadorComunicacao5 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao5.setUsuarios(usuarios);
		
		GerenciadorComunicacao gerenciadorComunicacao6 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		
		GerenciadorComunicacao gerenciadorComunicacao7 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.CADASTRAR_SITUACAO_AC, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		
		String[] emails = new String[] {"email1@teste.com"};
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = new ArrayList<GerenciadorComunicacao>();
		gerenciadorComunicacaos.add(gerenciadorComunicacao1);
		gerenciadorComunicacaos.add(gerenciadorComunicacao2);
		gerenciadorComunicacaos.add(gerenciadorComunicacao3);
		gerenciadorComunicacaos.add(gerenciadorComunicacao4);
		gerenciadorComunicacaos.add(gerenciadorComunicacao5);
		gerenciadorComunicacaos.add(gerenciadorComunicacao6);
		gerenciadorComunicacaos.add(gerenciadorComunicacao7);

		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SITUACAO_AC.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		
		empresaManager.expects(once()).method("findByCodigoAC").with(eq(situacao.getEmpresaCodigoAC()),eq(situacao.getGrupoAC())).will(returnValue(empresa));
		
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(situacao.getLotacaoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(areaOrganizacional));
		areaOrganizacionalManager.expects(once()).method("getEmailsResponsaveis").with(eq(areaOrganizacional.getId()), eq(empresa.getId()), eq(AreaOrganizacional.RESPONSAVEL)).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
		Long[] usuariosIds = collUtil.convertCollectionToArrayIds(gerenciadorComunicacao3.getUsuarios());
		usuarioManager.expects(once()).method("findEmailsByUsuario").with(eq(usuariosIds)).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(situacao.getLotacaoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(areaOrganizacional));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagemRespAreaOrganizacional").withAnyArguments().isVoid();		
		
		usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").with(eq(LongUtil.collectionToCollectionLong(gerenciadorComunicacao5.getUsuarios())), eq(gerenciadorComunicacao5.getEmpresa().getId()));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		
		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(situacao.getLotacaoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(areaOrganizacional));
		areaOrganizacionalManager.expects(once()).method("getEmailsResponsaveis").with(eq(areaOrganizacional.getId()), eq(empresa.getId()), eq(AreaOrganizacional.CORRESPONSAVEL)).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});

		areaOrganizacionalManager.expects(once()).method("findAreaOrganizacionalByCodigoAc").with(eq(situacao.getLotacaoCodigoAC()), eq(situacao.getEmpresaCodigoAC()), eq(situacao.getGrupoAC())).will(returnValue(areaOrganizacional));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional").withAnyArguments().isVoid();		
		
		gerenciadorComunicacaoManager.enviaMensagemCadastroSituacaoAC(colaborador.getNome(), situacao);
	}

	public void testEnviaMensagemHabilitacaoAVencer()
	{
		Empresa empresa = criaEmpresa();
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(2L);
		AreaOrganizacional areaOrganizacional3 = AreaOrganizacionalFactory.getEntity(3L);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacional1, areaOrganizacional2, areaOrganizacional3);
		
		Habilitacao habilitacaoComVencimento1DiaDepois = new Habilitacao();
		habilitacaoComVencimento1DiaDepois.setVencimento(DateUtil.incrementaDias(new Date(), 1));
		
		Habilitacao habilitacaoComVencimento2DiasDepois = new Habilitacao();
		habilitacaoComVencimento2DiasDepois.setVencimento(DateUtil.incrementaDias(new Date(), 2));
		
		Habilitacao habilitacaoComVencimento3DiasDepois = new Habilitacao();
		habilitacaoComVencimento3DiasDepois.setVencimento(DateUtil.incrementaDias(new Date(), 3));
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L, "Colaborador 1", "Nome Comercial 1", null, null, null, null);
		colaborador1.setHabilitacao(habilitacaoComVencimento1DiaDepois);
		colaborador1.setAreaOrganizacional(areaOrganizacional1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L, "Colaborador 2", "Nome Comercial 2", null, null, null, null);
		colaborador2.setHabilitacao(habilitacaoComVencimento1DiaDepois);
		colaborador2.setAreaOrganizacional(areaOrganizacional1);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity(3L, "Colaborador 3", "Nome Comercial 3", null, null, null, null);
		colaborador3.setHabilitacao(habilitacaoComVencimento2DiasDepois);
		colaborador3.setAreaOrganizacional(areaOrganizacional2);
		
		Colaborador colaborador4 = ColaboradorFactory.getEntity(4L, "Colaborador 4", "Nome Comercial 4", null, null, null, null);
		colaborador4.setHabilitacao(habilitacaoComVencimento3DiasDepois);
		colaborador4.setAreaOrganizacional(areaOrganizacional3);
		
		GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.EMAIL, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacao1.setQtdDiasLembrete("1&2");
		
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.EMAIL, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacao2.setQtdDiasLembrete("1&2");
		
		GerenciadorComunicacao gerenciadorComunicacao3 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		gerenciadorComunicacao3.setQtdDiasLembrete("1&2");
		
		Collection<Integer> diasLembrete = Arrays.asList(1, 2);
		String[] emails = new String[] {"email1@teste.com", "email2@teste.com"};
		
		Usuario usuario = UsuarioFactory.getEntity();
		Collection<Usuario> usuarios = Arrays.asList(usuario);
		
		GerenciadorComunicacao gerenciadorComunicacao4 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.EMAIL, EnviarPara.USUARIOS);
		gerenciadorComunicacao4.setUsuarios(usuarios);
		gerenciadorComunicacao4.setQtdDiasLembrete("1&2");
		
		CollectionUtil<Usuario> collUtil = new CollectionUtil<Usuario>();
		Long[] usuariosIds = collUtil.convertCollectionToArrayIds(gerenciadorComunicacao4.getUsuarios());
		
		GerenciadorComunicacao gerenciadorComunicacao5 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.GESTOR_AREA);
		gerenciadorComunicacao5.setQtdDiasLembrete("1&2");
		
		GerenciadorComunicacao gerenciadorComunicacao6 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.COGESTOR_AREA);
		gerenciadorComunicacao6.setQtdDiasLembrete("1&2");
		
		GerenciadorComunicacao gerenciadorComunicacao7 = GerenciadorComunicacaoFactory.getEntity(empresa, Operacao.HABILITACAO_A_VENCER, MeioComunicacao.CAIXA_MENSAGEM, EnviarPara.USUARIOS);
		gerenciadorComunicacao7.setUsuarios(usuarios);
		gerenciadorComunicacao7.setQtdDiasLembrete("1&2");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = new ArrayList<GerenciadorComunicacao>();
		gerenciadorComunicacaos.add(gerenciadorComunicacao1);
		gerenciadorComunicacaos.add(gerenciadorComunicacao2);
		gerenciadorComunicacaos.add(gerenciadorComunicacao3);
		gerenciadorComunicacaos.add(gerenciadorComunicacao4);
		gerenciadorComunicacaos.add(gerenciadorComunicacao5);
		gerenciadorComunicacaos.add(gerenciadorComunicacao6);
		gerenciadorComunicacaos.add(gerenciadorComunicacao7);
		
		gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.HABILITACAO_A_VENCER.getId()),eq(null)).will(returnValue(gerenciadorComunicacaos));
		areaOrganizacionalManager.expects(atLeastOnce()).method("findAllListAndInativas").with(eq(true), eq(null), eq(new Long[]{gerenciadorComunicacao1.getEmpresa().getId()})).will(returnValue(areas));
		colaboradorManager.expects(atLeastOnce()).method("findHabilitacaAVencer").with(eq(diasLembrete), eq(gerenciadorComunicacao1.getEmpresa().getId())).will(returnValue(Arrays.asList(colaborador1, colaborador2, colaborador3)));
		
		// Email para gestor
		areaOrganizacionalManager.expects(atLeastOnce()).method("getEmailsResponsaveis").with((ANYTHING), eq(areas), eq(AreaOrganizacional.RESPONSAVEL)).will(returnValue(emails));
		mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		// Email para cogestor
		areaOrganizacionalManager.expects(atLeastOnce()).method("getEmailsResponsaveis").with((ANYTHING), eq(areas), eq(AreaOrganizacional.CORRESPONSAVEL)).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		// Email para responsavel do rh
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		// Email para usuarios
		usuarioManager.expects(once()).method("findEmailsByUsuario").with(eq(usuariosIds)).will(returnValue(emails));
		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		
		// Caixa de mensagem para gestor
		areaOrganizacionalManager.expects(atLeastOnce()).method("getMatriarca").withAnyArguments().will(returnValue(areaOrganizacional1));
		usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagemRespAreaOrganizacional").withAnyArguments().isVoid();
				
		// Caixa de mensagem para cogestor
		areaOrganizacionalManager.expects(once()).method("getMatriarca").withAnyArguments().will(returnValue(areaOrganizacional1));
		usuarioMensagemManager.expects(atLeastOnce()).method("saveMensagemAndUsuarioMensagemCoRespAreaOrganizacional").withAnyArguments().isVoid();
		
		// Caixa de mensagem para usuarios
		usuarioEmpresaManager.expects(once()).method("findUsuariosAtivo").with(eq(LongUtil.collectionToCollectionLong(gerenciadorComunicacao7.getUsuarios())), eq(gerenciadorComunicacao7.getEmpresa().getId()));
		usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").withAnyArguments().isVoid();
		
		gerenciadorComunicacaoManager.enviaMensagemHabilitacaoAVencer();
	}
	
	public void testEnviarEmailParaResponsaveis() throws Exception
	{
		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");
		
		Empresa empresa = criaEmpresa();
		
		Usuario solicitante = UsuarioFactory.getEntity(1L);
		
		Colaborador colabSolicitante = ColaboradorFactory.getEntity(1L);
		colabSolicitante.setNome("nome");
		colabSolicitante.setNomeComercial("nomeComercial");
		colabSolicitante.setUsuario(solicitante);
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao.setId(1L);
		motivoSolicitacao.setDescricao("Motivo");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setNome("Estabelecimento");
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setDescricao("descricao");
		solicitacao.setSolicitante(solicitante);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao.setData(DateUtil.criarDataMesAno(20, 8, 2013));
		solicitacao.setObservacaoLiberador("observacaoLiberador");
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.ANALISE);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao, gerenciadorComunicacao2);
		
		String[] emailsByUsuario = new String[]{empresa.getEmailRespRH()};
		String[] emailsmMrcados = new String[]{"marcado@gmail.com"};

		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		colaboradorManager.expects(atLeastOnce()).method("findByUsuarioProjection").with(eq(solicitacao.getSolicitante().getId()), eq(true)).will(returnValue(colabSolicitante));
		motivoSolicitacaoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getMotivoSolicitacao().getId())).will(returnValue(motivoSolicitacao));
		estabelecimentoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getEstabelecimento().getId())).will(returnValue(estabelecimento));
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa),eq(parametroSistema),ANYTHING,ANYTHING,eq(true),ANYTHING});
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SOLICITACAO.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa),eq(parametroSistema),ANYTHING,ANYTHING,eq(true),ANYTHING});
		usuarioManager.expects(once()).method("findEmailsByPerfil").with(eq("ROLE_LIBERA_SOLICITACAO"),eq(empresa.getId())).will(returnValue(emailsByUsuario));
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa),eq(parametroSistema),ANYTHING,ANYTHING,eq(true),ANYTHING});
			
		gerenciadorComunicacaoManager.enviarEmailParaResponsaveisSolicitacaoPessoal(solicitacao, empresa, emailsmMrcados);
	}
	
	public void testEnviarEmailParaUsuarioComPermiAprovarOrReprovarAndGestor () throws Exception
	{
		ParametrosDoSistema parametroSistema = new ParametrosDoSistema();
		parametroSistema.setAppUrl("url");
		parametroSistema.setEmailDoSuporteTecnico("t@t.com.br");
		
		Empresa empresa = criaEmpresa();
		
		Usuario solicitante = UsuarioFactory.getEntity(1L);
		
		Colaborador colabSolicitante = ColaboradorFactory.getEntity(1L);
		colabSolicitante.setNome("nome");
		colabSolicitante.setNomeComercial("nomeComercial");
		colabSolicitante.setUsuario(solicitante);
		
		Colaborador gestor = ColaboradorFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacionalMae.setNome("Area Mãe");
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacional.setResponsavel(gestor);
		areaOrganizacional.setAreaMae(areaOrganizacionalMae);
		
		Collection<AreaOrganizacional> areas = Arrays.asList(areaOrganizacionalMae, areaOrganizacional);
		Collection<Long> areasId = Arrays.asList(areaOrganizacionalMae.getId(), areaOrganizacional.getId());
		
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
		motivoSolicitacao.setId(1L);
		motivoSolicitacao.setDescricao("Motivo");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setNome("Estabelecimento");
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setDescricao("descricao");
		solicitacao.setSolicitante(solicitante);
		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
		solicitacao.setData(DateUtil.criarDataMesAno(20, 8, 2013));
		solicitacao.setObservacaoLiberador("observacaoLiberador");
		solicitacao.setEstabelecimento(estabelecimento);
		solicitacao.setAreaOrganizacional(areaOrganizacional);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.ANALISE);
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.APROVAR_REPROVAR_SOLICITACAO_PESSOAL_AND_GESTOR);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		String[] emailsByUsuario = new String[]{empresa.getEmailRespRH()};
		String[] emailsmMrcados = new String[]{"marcado@gmail.com"};
		
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SOLICITACAO.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametroSistema));
		colaboradorManager.expects(atLeastOnce()).method("findByUsuarioProjection").with(eq(solicitacao.getSolicitante().getId()), eq(true)).will(returnValue(colabSolicitante));
		motivoSolicitacaoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getMotivoSolicitacao().getId())).will(returnValue(motivoSolicitacao));
		estabelecimentoManager.expects(atLeastOnce()).method("findById").with(eq(solicitacao.getEstabelecimento().getId())).will(returnValue(estabelecimento));
		usuarioManager.expects(once()).method("findEmailByPerfilAndGestor").with(eq("ROLE_LIBERA_SOLICITACAO"), eq(empresa.getId()), eq(solicitacao.getAreaOrganizacional().getId()), eq(false)).will(returnValue(emailsByUsuario));
		mail.expects(atLeastOnce()).method("send").withAnyArguments();
			
		gerenciadorComunicacaoManager.enviarEmailParaResponsaveisSolicitacaoPessoal(solicitacao, empresa, emailsmMrcados);
	}
	
	public void testEnviaComunicadoAoCadastrarSolicitacaoRealinhamentoColaborador() throws Exception
	{
		Empresa empresa = criaEmpresa();
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setNome("Colaborador");
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.RESPONSAVEL_RH);
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		String subject = "[RH] - Cadastro de solicitação de realinhamento para colaborador";
		String body = "Foi realizado um cadastro de solicitação de realinhamento para o colaborador " + colaborador.getNome() + ".";
		String[] emails = StringUtils.split(empresa.getEmailRespRH(), ";");
		
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CADASTRAR_SOLICITACAO_REALINHAMENTO_COLABORADOR.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));
		mail.expects(once()).method("send").with(new Constraint[]{eq(empresa), eq(subject), eq(body), eq(null), eq(emails)});
		
		gerenciadorComunicacaoManager.enviaAvisoAoCadastrarSolicitacaoRealinhamentoColaborador(empresa.getId(), colaborador, null);
	}
	
	public void testEnviarEmailAoCriarAcessoSistemaSemEmail()
	{
		Empresa empresa = criaEmpresa();

		try{
			gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", null, empresa);
			gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", "", empresa);
			gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", " ", empresa);
		} catch (Exception e) {
			assertFalse(true);
		}
		assertTrue(true);
	}
	
	public void testEenviarEmailAoCriarAcessoSistemaComEmail()
	{
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(new ParametrosDoSistema()));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CRIAR_ACESSO_SISTEMA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		mail.expects(once()).method("send").withAnyArguments().isVoid();
		
		gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", "email@email.com", empresa);
	}
	
	public void testEenviarEmailAoCriarAcessoSistemaSemGerenciadorConfigurado()
	{
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.AVULSO);
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(new ParametrosDoSistema()));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CRIAR_ACESSO_SISTEMA.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		
		gerenciadorComunicacaoManager.enviarEmailAoCriarAcessoSistema("login", "senha", "email@email.com", empresa);
	}
	
	public void testEnviarNotificacaoCursosAVencer() {
		
		Empresa empresa = criaEmpresa();
		
		GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity(empresa, MeioComunicacao.EMAIL, EnviarPara.COLABORADOR);
		gerenciadorComunicacao.setQtdDiasLembrete("1");
		
		Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		
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
		
		Collection<ColaboradorTurma> colaboradoresTurmas  = Arrays.asList(colaboradorTurma1, colaboradorTurma2);
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(new ParametrosDoSistema()));
		empresaManager.expects(once()).method("findTodasEmpresas").will(returnValue(Arrays.asList(empresa)));
		gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CURSOS_A_VENCER.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		colaboradorTurmaManager.expects(once()).method("findCursosCertificacoesAVencer").with(ANYTHING, eq(gerenciadorComunicacao.getEmpresa().getId())).will(returnValue(colaboradoresTurmas));
		mail.expects(once()).method("send").withAnyArguments().isVoid();
		gerenciadorComunicacaoManager.enviarNotificacaoCursosOuCertificacoesAVencer();
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

	private Empresa criaEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("Empresa I");
		empresa.setEmailRespRH("teste1@gmail.com;teste2@gmail.com;");
		empresa.setEmailRemetente("teste1@gmail.com");
		
		return empresa;
	}
}
