package com.fortes.rh.test.web.action.pesquisa;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.PerguntaEditAction;

public class PerguntaEditActionTest extends MockObjectTestCase
{
	private PerguntaEditAction perguntaEditAction;
	private Mock perguntaManager;
	private Mock respostaManager;
	private Mock aspectoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		perguntaEditAction = new PerguntaEditAction();

		perguntaManager = new Mock(PerguntaManager.class);
		perguntaEditAction.setPerguntaManager((PerguntaManager) perguntaManager.proxy());

		respostaManager = new Mock(RespostaManager.class);
		perguntaEditAction.setRespostaManager((RespostaManager) respostaManager.proxy());

		aspectoManager = new Mock(AspectoManager.class);
		perguntaEditAction.setAspectoManager((AspectoManager) aspectoManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		perguntaManager = null;
		perguntaEditAction = null;
        MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", perguntaEditAction.execute());
	}

	public void testPrepareUpdate() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		pergunta.setQuestionario(questionario);

		perguntaEditAction.setQuestionario(questionario);
		perguntaEditAction.setPergunta(pergunta);

		perguntaManager.expects(once()).method("findPergunta").with(eq(pergunta.getId())).will(returnValue(pergunta));
		aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
		respostaManager.expects(once()).method("findRespostasSugeridas").with(eq(questionario.getId())).will(returnValue(RespostaFactory.getCollection(1L)));

		assertEquals("success", perguntaEditAction.prepareUpdate());
		assertEquals(1, perguntaEditAction.getTemas().size());
	}

	public void testPrepareInsert() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		perguntaEditAction.setQuestionario(questionario);

		aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
		respostaManager.expects(once()).method("findRespostasSugeridas").with(eq(questionario.getId())).will(returnValue(null));

		assertEquals("success", perguntaEditAction.prepareInsert());
		assertEquals(1, perguntaEditAction.getTemas().size());
		assertNull(perguntaEditAction.getRespostasSugeridas());
	}

	public void testInsert() throws Exception
	{
		Pergunta pergunta = PerguntaFactory.getEntity(1L);

		perguntaEditAction.setPergunta(pergunta);

		perguntaManager.expects(once()).method("salvarPergunta").with(eq(pergunta), ANYTHING, ANYTHING, ANYTHING).will(returnValue(pergunta));

		assertEquals("success", perguntaEditAction.insert());
		assertEquals(pergunta, perguntaEditAction.getPergunta());
	}

	public void testInsertException() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta.setQuestionario(questionario);
		pergunta.setTipo(TipoPergunta.OBJETIVA);

		perguntaEditAction.setPergunta(pergunta);
		perguntaEditAction.setQuestionario(questionario);
		perguntaEditAction.setRespostaSugerida(1);

		perguntaEditAction.setPergunta(pergunta);

		perguntaManager.expects(once()).method("salvarPergunta").with(eq(pergunta), ANYTHING, ANYTHING, ANYTHING).will(throwException(new Exception("erro")));
		aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
		respostaManager.expects(once()).method("findRespostasSugeridas").with(eq(questionario.getId())).will(returnValue(null));

		assertEquals("input", perguntaEditAction.insert());
	}

	public void testUpdate() throws Exception
	{
		Pergunta pergunta = PerguntaFactory.getEntity(1L);

		perguntaEditAction.setPergunta(pergunta);

		perguntaManager.expects(once()).method("atualizarPergunta").with(eq(pergunta),ANYTHING, ANYTHING);

		assertEquals("success", perguntaEditAction.update());
		assertEquals(pergunta, perguntaEditAction.getPergunta());
	}

	public void testUpdateException() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		pergunta.setQuestionario(questionario);
		pergunta.setTipo(TipoPergunta.OBJETIVA);

		perguntaEditAction.setPergunta(pergunta);
		perguntaEditAction.setQuestionario(questionario);

		perguntaManager.expects(once()).method("atualizarPergunta").with(eq(pergunta),ANYTHING, ANYTHING).will(throwException(new Exception("erro")));;
		perguntaManager.expects(once()).method("findPergunta").with(eq(pergunta.getId())).will(returnValue(pergunta));
		aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
		respostaManager.expects(once()).method("findRespostasSugeridas").with(eq(questionario.getId())).will(returnValue(RespostaFactory.getCollection(1L)));

		assertEquals("input", perguntaEditAction.update());
	}


	public void testGetsSets()
	{
		perguntaEditAction.getModel();
		perguntaEditAction.getPesquisa();
		perguntaEditAction.setPesquisa(new Pesquisa());
		perguntaEditAction.getQuestionario();
		perguntaEditAction.getRespostas();
		perguntaEditAction.getTipoPerguntas();
		perguntaEditAction.getRespostaObjetiva();
		perguntaEditAction.setRespostas(RespostaFactory.getCollection(1L));
		perguntaEditAction.setRespostaObjetiva(new String[]{"aa"});
		perguntaEditAction.setRespostaObjetivaSugerida(new String[]{"aaa"});
		perguntaEditAction.getRespostaObjetivaSugerida();
		perguntaEditAction.setOrdemSugerida(1);
		perguntaEditAction.getOrdemSugerida();
		perguntaEditAction.setRespostaSugerida(1);
		perguntaEditAction.getRespostaSugerida();
	}

}