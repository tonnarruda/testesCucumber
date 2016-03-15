package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.PerguntaListAction;

public class PerguntaListActionTest extends MockObjectTestCase
{
	private PerguntaListAction perguntaListAction;
	private Mock perguntaManager;
	private Mock aspectoManager;
	private Mock questionarioManager;

    protected void setUp() throws Exception
    {
        super.setUp();

        perguntaListAction = new PerguntaListAction();

        perguntaManager = new Mock(PerguntaManager.class);
        perguntaListAction.setPerguntaManager((PerguntaManager) perguntaManager.proxy());

        aspectoManager = new Mock(AspectoManager.class);
        perguntaListAction.setAspectoManager((AspectoManager) aspectoManager.proxy());

        questionarioManager = new Mock(QuestionarioManager.class);
        perguntaListAction.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	perguntaListAction = null;
        perguntaManager = null;
        MockSecurityUtil.verifyRole = false;
        
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", perguntaListAction.execute());
    }

    public void testList() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Aspecto aspecto1 = AspectoFactory.getEntity(1L);
    	Aspecto aspecto2 = AspectoFactory.getEntity(2L);

    	Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
    	aspectos.add(aspecto1);
    	aspectos.add(aspecto2);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	Pergunta pergunta3 = PerguntaFactory.getEntity(3L);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);
    	perguntas.add(pergunta3);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(perguntas));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta3.getOrdem()));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));

    	assertEquals("success", perguntaListAction.list());
    	assertEquals(perguntas, perguntaListAction.getPerguntas());
    	assertEquals(aspectos, perguntaListAction.getAspectos());
    	assertNotNull(perguntaListAction.getTipoPergunta());
    	assertEquals("voltar", perguntaListAction.getUrlVoltar());
    }

    public void testListBusca() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Aspecto aspecto1 = AspectoFactory.getEntity(1L);
    	Aspecto aspecto2 = AspectoFactory.getEntity(2L);

    	Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
    	aspectos.add(aspecto1);
    	aspectos.add(aspecto2);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setAspecto(aspecto1);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setAspecto(aspecto1);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	perguntaListAction.setAspecto(aspecto1);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	aspectoManager.expects(once()).method("findByIdProjection").with(eq(aspecto1.getId())).will(returnValue(aspecto1));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta2.getOrdem()));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionarioAspecto").with(eq(pesquisa.getId()),ANYTHING).will(returnValue(perguntas));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));

    	assertEquals("success", perguntaListAction.list());
    	assertEquals(perguntas, perguntaListAction.getPerguntas());
    	assertEquals(aspectos, perguntaListAction.getAspectos());
    	assertEquals(aspecto1.getId(), perguntaListAction.getAspecto().getId());
    }

    public void testDelete() throws Exception
    {
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Aspecto aspecto1 = AspectoFactory.getEntity(1L);
    	Aspecto aspecto2 = AspectoFactory.getEntity(2L);

    	Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
    	aspectos.add(aspecto1);
    	aspectos.add(aspecto2);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	Pergunta pergunta3 = PerguntaFactory.getEntity(3L);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);
    	perguntas.add(pergunta3);

    	perguntaListAction.setPergunta(pergunta);
    	perguntaListAction.setMsg("sucesso");

    	perguntaManager.expects(once()).method("removerPergunta").with(eq(pergunta));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(perguntas));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta3.getOrdem()));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));

    	assertEquals("success", perguntaListAction.delete());
    	assertNull(perguntaListAction.getActionErr());
    	assertEquals(pergunta, perguntaListAction.getPergunta());
    }

    public void testDeleteException() throws Exception
    {
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Aspecto aspecto1 = AspectoFactory.getEntity(1L);
    	Aspecto aspecto2 = AspectoFactory.getEntity(2L);

    	Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
    	aspectos.add(aspecto1);
    	aspectos.add(aspecto2);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	Pergunta pergunta3 = PerguntaFactory.getEntity(3L);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);
    	perguntas.add(pergunta3);

    	perguntaListAction.setPergunta(pergunta);

    	perguntaManager.expects(once()).method("removerPergunta").with(eq(pergunta)).will(throwException(new Exception("erro")));
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(perguntas));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta3.getOrdem()));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));
    	
    	assertEquals("success", perguntaListAction.delete());
    	assertNotNull(perguntaListAction.getActionErr());
    }

    public void testReordenar() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setQuestionario(questionario);

    	perguntaListAction.setPergunta(pergunta);
    	perguntaListAction.setSinal('+');
    	
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	perguntaManager.expects(once()).method("reordenarPergunta").with(eq(pergunta),eq('+'));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(PerguntaFactory.getCollection(1L)));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta.getOrdem()));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));
    	
    	assertEquals("success", perguntaListAction.reordenar());
    }

    public void testReordenarException() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setQuestionario(questionario);

    	perguntaListAction.setPergunta(pergunta);
    	perguntaListAction.setSinal('+');

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	perguntaManager.expects(once()).method("reordenarPergunta").with(eq(pergunta),eq('+')).will(throwException(new Exception("error")));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(PerguntaFactory.getCollection(1L)));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta.getOrdem()));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));
    	
    	assertEquals("success", perguntaListAction.reordenar());
    	assertNotNull(perguntaListAction.getActionErr());
    	assertEquals('+', perguntaListAction.getSinal());
    	assertTrue(pergunta.getOrdem().equals(perguntaListAction.getUltimaOrdenacao()));
    }

    public void testAlterarPosicao() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setQuestionario(questionario);

    	int posicaoSugerida = 5;

    	perguntaListAction.setPergunta(pergunta);
    	perguntaListAction.setPosicaoSugerida(posicaoSugerida);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
		perguntaManager.expects(once()).method("alterarPosicao").with(eq(pergunta),eq(posicaoSugerida));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(PerguntaFactory.getCollection(1L)));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta.getOrdem()));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));
    	
    	assertEquals("success", perguntaListAction.alterarPosicao());
    	assertNull(perguntaListAction.getActionErr());
    }

    public void testAlterarPosicaoException() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setQuestionario(questionario);

    	int posicaoSugerida = 5;

    	perguntaListAction.setPergunta(pergunta);
    	perguntaListAction.setPosicaoSugerida(posicaoSugerida);

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	perguntaManager.expects(once()).method("alterarPosicao").with(eq(pergunta),eq(posicaoSugerida)).will(throwException(new Exception("erro")));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(PerguntaFactory.getCollection(1L)));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta.getOrdem()));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));
    	
    	assertEquals("success", perguntaListAction.alterarPosicao());
    	assertNotNull(perguntaListAction.getActionErr());
    }

    public void testEmbaralharPerguntas() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	perguntaManager.expects(once()).method("embaralharPerguntas").with(eq(pesquisa.getId()));
    	
    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(PerguntaFactory.getCollection(1L)));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(2));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));
    	
		assertEquals("success", perguntaListAction.embaralharPerguntas());
		assertNull(perguntaListAction.getActionErr());
    }

    public void testEmbaralharPerguntasException() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	perguntaListAction.setQuestionario(questionario);

    	Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
    	pesquisa.setQuestionario(questionario);

    	perguntaManager.expects(once()).method("embaralharPerguntas").with(eq(pesquisa.getId())).will(throwException(new Exception("erro")));

    	questionarioManager.expects(once()).method("findByIdProjection").with(eq(questionario.getId())).will(returnValue(questionario));
    	aspectoManager.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(AspectoFactory.getCollection(1L)));
    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionario").with(eq(pesquisa.getId())).will(returnValue(PerguntaFactory.getCollection(1L)));
    	perguntaManager.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(2));
    	questionarioManager.expects(once()).method("montaUrlVoltar").with(eq(questionario.getId())).will(returnValue("voltar"));
    	
   		assertEquals("success", perguntaListAction.embaralharPerguntas());
    }

	public void setQuestionarioManager(Mock questionarioManager)
	{
		this.questionarioManager = questionarioManager;
	}

}
