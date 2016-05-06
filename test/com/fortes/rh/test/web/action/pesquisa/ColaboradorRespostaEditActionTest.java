package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.ColaboradorRespostaEditAction;

public class ColaboradorRespostaEditActionTest extends MockObjectTestCase
{
	private ColaboradorRespostaEditAction colaboradorRespostaEditAction;
	private Mock questionarioManager;
	private Mock colaboradorRespostaManager;
	private Mock pesquisaManager;
	private Mock colaboradorManager;
	private Mock colaboradorQuestionarioManager;

	protected void setUp() throws Exception
	{
		colaboradorRespostaManager = new Mock(ColaboradorRespostaManager.class);

		colaboradorRespostaEditAction = new ColaboradorRespostaEditAction();
		colaboradorRespostaEditAction.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());

		pesquisaManager = new Mock(PesquisaManager.class);
        colaboradorRespostaEditAction.setPesquisaManager((PesquisaManager) pesquisaManager.proxy());
        
        questionarioManager = new Mock(QuestionarioManager.class);
        colaboradorRespostaEditAction.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());
        
        colaboradorManager = new Mock(ColaboradorManager.class);
        colaboradorRespostaEditAction.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        colaboradorRespostaEditAction.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();

		colaboradorRespostaManager = null;
		colaboradorRespostaEditAction = null;

        MockSecurityUtil.verifyRole = false;
		
		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", colaboradorRespostaEditAction.execute());
	}

	public void testGetsSets()
	{
		colaboradorRespostaEditAction.getColaborador();
		colaboradorRespostaEditAction.getColaboradorQuestionario();
		colaboradorRespostaEditAction.getColaboradorResposta();
		colaboradorRespostaEditAction.getColaboradorRespostaManager();
		colaboradorRespostaEditAction.getEmpresaSistema();
		colaboradorRespostaEditAction.getModel();
		colaboradorRespostaEditAction.getTela();
		colaboradorRespostaEditAction.getTipoPergunta();
		colaboradorRespostaEditAction.getPesquisa();
		colaboradorRespostaEditAction.getPesquisaManager();
		colaboradorRespostaEditAction.getRespostas();
		colaboradorRespostaEditAction.getRetorno();
		colaboradorRespostaEditAction.getVoltarPara();
		colaboradorRespostaEditAction.setColaboradorResposta(ColaboradorRespostaFactory.getEntity(1L));
		colaboradorRespostaEditAction.setVoltarPara("ewew");
		colaboradorRespostaEditAction.setColaboradorQuestionario(ColaboradorQuestionarioFactory.getEntity(1L));
		colaboradorRespostaEditAction.setRetorno("ewew");
	}
	
	public void testPrepareResponderQuestionarioColaboradorLogadoIgualAoColaboradorIdDaURL() throws Exception{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setTipo(TipoQuestionario.getAVALIACAOTURMA());
		Collection<Pergunta> perguntas = Arrays.asList(PerguntaFactory.getEntity(1L));
		questionario.setPerguntas(perguntas);
		Long turmaId = 1234L;
			  
		Colaborador colaboradorLogado = ColaboradorFactory.getEntity(121L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
			  
		colaboradorRespostaEditAction.setUsuarioLogado(usuario);
		colaboradorRespostaEditAction.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		colaboradorRespostaEditAction.setColaborador(colaboradorLogado);
		colaboradorRespostaEditAction.setTurmaId(turmaId);
			  
		colaboradorManager.expects(once()).method("findByUsuario").with(eq(usuario), eq(colaboradorRespostaEditAction.getEmpresaSistema().getId())).will(returnValue(colaboradorLogado));
		questionarioManager.expects(once()).method("findResponderQuestionario").with(ANYTHING).will(returnValue(questionario));
		colaboradorManager.expects(once()).method("findColaboradorByIdProjection").with(eq(colaboradorLogado.getId())).will(returnValue(colaboradorLogado));
		colaboradorRespostaManager.expects(once()).method("findByQuestionarioColaborador").with(eq(questionario.getId()), eq(colaboradorLogado.getId()), eq(turmaId), eq(null)).will(returnValue(new ArrayList<ColaboradorResposta>()));
		colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId()), eq(colaboradorLogado.getId()), eq(turmaId));
		
		assertEquals("success",colaboradorRespostaEditAction.prepareResponderQuestionario());
    }
	
	public void testPrepareResponderQuestionarioColaboradorLogadoDiferenteAoColaboradorIdDaURL() throws Exception{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setTipo(TipoQuestionario.getAVALIACAOTURMA());
		Collection<Pergunta> perguntas = Arrays.asList(PerguntaFactory.getEntity(1L));
		questionario.setPerguntas(perguntas);
		Long turmaId = 1234L;
			  
		Colaborador colaboradorLogado = ColaboradorFactory.getEntity(121L);
		Colaborador colaborador = ColaboradorFactory.getEntity(122L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
			  
		colaboradorRespostaEditAction.setUsuarioLogado(usuario);
		colaboradorRespostaEditAction.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		colaboradorRespostaEditAction.setColaborador(colaborador);
		colaboradorRespostaEditAction.setTurmaId(turmaId);
			  
		colaboradorManager.expects(once()).method("findByUsuario").with(eq(usuario), eq(colaboradorRespostaEditAction.getEmpresaSistema().getId())).will(returnValue(colaboradorLogado));
		questionarioManager.expects(once()).method("findResponderQuestionario").with(ANYTHING).will(returnValue(questionario));
		assertEquals("error",colaboradorRespostaEditAction.prepareResponderQuestionario());
    }
	
	public void testPrepareResponderQuestionarioPorOutroUsuario() throws Exception{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setTipo(TipoQuestionario.getAVALIACAOTURMA());
		Collection<Pergunta> perguntas = Arrays.asList(PerguntaFactory.getEntity(1L));
		questionario.setPerguntas(perguntas);
		Long turmaId = 1234L;
		
			  
		Colaborador colaboradorLogado = ColaboradorFactory.getEntity(121L);
		Colaborador colaborador = ColaboradorFactory.getEntity(122L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
			  
		colaboradorRespostaEditAction.setUsuarioLogado(usuario);
		colaboradorRespostaEditAction.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		colaboradorRespostaEditAction.setColaborador(colaborador);
		colaboradorRespostaEditAction.setTurmaId(turmaId);
			  
		colaboradorManager.expects(once()).method("findByUsuario").with(eq(usuario), eq(colaboradorRespostaEditAction.getEmpresaSistema().getId())).will(returnValue(colaboradorLogado));
		questionarioManager.expects(once()).method("findResponderQuestionario").with(ANYTHING).will(returnValue(questionario));
		colaboradorManager.expects(once()).method("findColaboradorByIdProjection").with(eq(colaborador.getId())).will(returnValue(colaborador));
		colaboradorRespostaManager.expects(once()).method("findByQuestionarioColaborador").with(eq(questionario.getId()), eq(colaborador.getId()), eq(turmaId), eq(null)).will(returnValue(new ArrayList<ColaboradorResposta>()));
		colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId()), eq(colaborador.getId()), eq(turmaId));
		
		assertEquals("success",colaboradorRespostaEditAction.prepareResponderQuestionarioPorOutroUsuario());
    }
}
