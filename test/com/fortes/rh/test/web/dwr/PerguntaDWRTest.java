package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.web.dwr.PerguntaDWR;

public class PerguntaDWRTest extends MockObjectTestCase
{
	private PerguntaDWR perguntaDWR;
	private Mock perguntaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		perguntaDWR = new PerguntaDWR();

		perguntaManager = new Mock(PerguntaManager.class);
		perguntaDWR.setPerguntaManager((PerguntaManager) perguntaManager.proxy());
	}

	public void testGetPerguntas()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setQuestionario(questionario);

		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setQuestionario(questionario);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);

		perguntaManager.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(perguntas));

		Map<Object, Object> retorno = perguntaDWR.getPerguntas(questionario.getId());

		assertEquals(2, retorno.size());

	}

	public void testGetPerguntasSemQuestionario()
	{
		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);

		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);

		Map<Object, Object> retorno = perguntaDWR.getPerguntas(null);

		assertEquals(0, retorno.size());

	}
}
