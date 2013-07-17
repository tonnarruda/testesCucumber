package com.fortes.rh.test.web.action.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.web.action.avaliacao.PerguntaAvaliacaoEditAction;

public class PerguntaAvaliacaoEditActionTest extends MockObjectTestCase
{
	private PerguntaAvaliacaoEditAction action;
	private Mock perguntaManager;
	private Mock colaboradorQuestionarioManager;

	protected void setUp() throws Exception
	{
		action = new PerguntaAvaliacaoEditAction();

		perguntaManager = mock(PerguntaManager.class); 
		action.setPerguntaManager((PerguntaManager) perguntaManager.proxy());
		
		colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
		action.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());
		
		
		action.setAvaliacao(new Avaliacao());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	protected void tearDown() throws Exception
	{
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(ColaboradorQuestionarioFactory.getEntity(3L));
		action.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		
		perguntaManager.expects(once()).method("findByQuestionario").with(eq(1L)).will(returnValue(new ArrayList<Pergunta>()));
		colaboradorQuestionarioManager.expects(once()).method("findByQuestionario").with(eq(1L)).will(returnValue(colaboradorQuestionarios));
		
		assertEquals("success", action.list());
		assertEquals("Esta avaliação já possui perguntas respondidas. Só é possível visualizá-las.", action.getActionMessages().toArray()[0]);
	}

	public void testDelete() throws Exception
	{
		Pergunta pergunta = PerguntaFactory.getEntity(33L);
		action.setPergunta(pergunta);
		perguntaManager.expects(once()).method("removerPergunta").with(eq(pergunta)).isVoid();
		
		assertEquals(action.delete(), "success");
	}
	
	public void testInsert() throws Exception
	{

	}

	public void testUpdate() throws Exception
	{

	}

	public void testGetSet() throws Exception
	{
		assertNotNull(action.getAvaliacao());
		assertTrue(action.getAvaliacao() instanceof Avaliacao);
	}
}
