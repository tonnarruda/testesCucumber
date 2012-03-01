package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManagerImpl;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
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
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
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
	private Mock parametrosDoSistemaManager;
	private Mock periodoExperienciaManager;
	private Mock usuarioMensagemManager;
	private Mock usuarioEmpresaManager;
	private Mock questionarioManager;
	private Mock colaboradorManager;
	private Mock solicitacaoManager;
	private Mock empresaManager;
	private Mock exameManager;
	private Mock mensagemManager;
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

        mail = mock(Mail.class);
        gerenciadorComunicacaoManager.setMail((Mail) mail.proxy());

        solicitacaoManager = new Mock(SolicitacaoManager.class);
		MockSpringUtil.mocks.put("solicitacaoManager", solicitacaoManager);

		colaboradorManager = new Mock(ColaboradorManager.class);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		
		questionarioManager = new Mock(QuestionarioManager.class);
		MockSpringUtil.mocks.put("questionarioManager", questionarioManager);
		
		exameManager = new Mock(ExameManager.class);
		MockSpringUtil.mocks.put("exameManager", exameManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
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
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.LIBERAR_QUESTIONARIO.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
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
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 parametrosDoSistemaManager.expects(once()).method("getDiasLembretePesquisa").will(returnValue(Arrays.asList(1,2)));
		 questionarioManager.expects(atLeastOnce()).method("findQuestionarioNaoLiberados").with(ANYTHING).will(returnValue(questionarios));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
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
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaEmailResponsavelRh("Chico", empresa.getId());
		 } catch (Exception e) {
			 exception = e;
		 }
		 
		 assertNull(exception);
	 }
	 public void testEnviaEmailQuestionarioNaoRespondido() throws Exception
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
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.LEMBRETE_QUESTIONARIO_NAO_RESPONDIDO.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviaEmailQuestionarioNaoRespondido(empresa, questionario, colaboradorQuestionarios);
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
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.RESPONSAVEL_RH.getId());
		 
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
		 
		 GerenciadorComunicacao gerenciadorComunicacao = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao.setEmpresa(empresa);
		 gerenciadorComunicacao.setMeioComunicacao(MeioComunicacao.EMAIL.getId());
		 gerenciadorComunicacao.setEnviarPara(EnviarPara.PERFIL_AUTORIZADO_EXAMES_PREVISTOS.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao);
		 
		 Collection<Exame> examesPrevistos = Arrays.asList(ExameFactory.getEntity()); 
		 
		 colaboradorManager.expects(once()).method("findEmailsByPapel").with(eq(empresa.getId()),eq("ROLE_RECEBE_EXAMES_PREVISTOS")).will(returnValue(emails));
		 parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametros));
		 exameManager.expects(once()).method("findRelatorioExamesPrevistos").with(new Constraint[] {eq(empresa.getId()), ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(examesPrevistos));
		 gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.EXAMES_PREVISTOS.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
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
		 Collection<Colaborador> colaboradors = Arrays.asList(teo);
		 
		 UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity();
		 Collection<UsuarioEmpresa> usuarioEmpresasPeriodoExperienciaGerencial = Arrays.asList(usuarioEmpresa);
		 
		 GerenciadorComunicacao gerenciadorComunicacao1 = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao1.setEmpresa(empresa);
		 gerenciadorComunicacao1.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao1.setEnviarPara(EnviarPara.GERENCIADOR_DE_MENSAGEM_PERIODO_EXPERIENCIA.getId());
		 
		 GerenciadorComunicacao gerenciadorComunicacao2 = GerenciadorComunicacaoFactory.getEntity();
		 gerenciadorComunicacao2.setEmpresa(empresa);
		 gerenciadorComunicacao2.setMeioComunicacao(MeioComunicacao.CAIXA_MENSAGEM.getId());
		 gerenciadorComunicacao2.setEnviarPara(EnviarPara.RECEBE_MENSAGEM_PERIODO_EXPERIENCIA.getId());
		 
		 Collection<GerenciadorComunicacao> gerenciadorComunicacaos = Arrays.asList(gerenciadorComunicacao1, gerenciadorComunicacao2);
		 
		 periodoExperienciaManager.expects(once()).method("findAll").will(returnValue(periodoExperiencias));
		 parametrosDoSistemaManager.expects(once()).method("getDiasLembretePeriodoExperiencia").will(returnValue(Arrays.asList(1)));
		 colaboradorManager.expects(once()).method("findAdmitidosHaDias").with(ANYTHING, ANYTHING).will(returnValue(colaboradors));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleAvaliacaoExperiencia").with(eq(empresa.getId()),eq("GERENCIA_MSG_PERIODOEXPERIENCIA")).will(returnValue(usuarioEmpresasPeriodoExperienciaGerencial));
		 usuarioEmpresaManager.expects(once()).method("findUsuariosByEmpresaRoleAvaliacaoExperiencia").with(eq(empresa.getId()),eq("RECEBE_MSG_PERIODOEXPERIENCIA")).will(returnValue(new ArrayList<UsuarioEmpresa>()));
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()),eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagem").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		 usuarioMensagemManager.expects(once()).method("saveMensagemAndUsuarioMensagemRespAreaOrganizacional").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).isVoid();
		 
		 
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
		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.BACKUP_AUTOMATICO.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));

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

		 gerenciadorComunicacaoDao.expects(once()).method("findByOperacaoId").with(eq(Operacao.CONTRATAR_COLABORADOR.getId()),ANYTHING).will(returnValue(gerenciadorComunicacaos));
		 mail.expects(atLeastOnce()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
		 
		 Exception exception = null;
		 try {
			 gerenciadorComunicacaoManager.enviarEmailContratacaoColaborador(teo.getNome(), empresa);
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
		 Object[] valores = new Object[] {Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.getId(), MeioComunicacao.CAIXA_MENSAGEM.getId(), EnviarPara.PERFIL_AUTORIZADO_VISUALIZAR_SOLICITACAO_PESSOAL.getId(), empresa.getId()};
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
}
