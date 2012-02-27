package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManagerImpl;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

public class GerenciadorComunicacaoManagerTest extends MockObjectTestCase
{
	private GerenciadorComunicacaoManagerImpl gerenciadorComunicacaoManager = new GerenciadorComunicacaoManagerImpl();
	private Mock gerenciadorComunicacaoDao;
	private Mock candidatoSolicitacaoManager;
	private Mock parametrosDoSistemaManager;
	private Mock colaboradorManager;
	private Mock questionarioManager;
	private Mock empresaManager;
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
        
		colaboradorManager = new Mock(ColaboradorManager.class);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		
		questionarioManager = new Mock(QuestionarioManager.class);
		MockSpringUtil.mocks.put("questionarioManager", questionarioManager);
		
		empresaManager = new Mock(EmpresaManager.class);
		gerenciadorComunicacaoManager.setEmpresaManager((EmpresaManager) empresaManager.proxy());

        mail = mock(Mail.class);
        gerenciadorComunicacaoManager.setMail((Mail) mail.proxy());
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
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
		gerenciadorComunicacaoDao.expects(atLeastOnce()).method("findByOperacaoId").with(eq(Operacao.ALTEREAR_STATUS_SOLICITACAO.getId()), eq(empresa.getId())).will(returnValue(gerenciadorComunicacaos));
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
}
