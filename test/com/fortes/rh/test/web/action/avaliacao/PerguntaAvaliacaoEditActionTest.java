package com.fortes.rh.test.web.action.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.web.action.avaliacao.PerguntaAvaliacaoEditAction;

public class PerguntaAvaliacaoEditActionTest
{
	private PerguntaAvaliacaoEditAction action;
	private PerguntaManager perguntaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;

	@Before
	public void setUp() throws Exception
	{
		action = new PerguntaAvaliacaoEditAction();

		perguntaManager = mock(PerguntaManager.class); 
		action.setPerguntaManager(perguntaManager);
		
		colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
		action.setColaboradorRespostaManager(colaboradorRespostaManager);
		
		action.setAvaliacao(new Avaliacao());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	@Test
	public void testList() throws Exception
	{
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(ColaboradorQuestionarioFactory.getEntity(3L));
		action.setAvaliacao(AvaliacaoFactory.getEntity(1L));
		
		when(perguntaManager.findByQuestionario(1L)).thenReturn(new ArrayList<Pergunta>());
		when(colaboradorRespostaManager.countColaboradorAvaliacaoRespondida(1L)).thenReturn(1);
		
		assertEquals("success", action.list());
		assertEquals("Esta avaliação já possui perguntas respondidas. Só é possível visualizá-las.", action.getActionMessages().toArray()[0]);
	}

	@Test
	public void testDelete() throws Exception
	{
		Pergunta pergunta = PerguntaFactory.getEntity(33L);
		action.setPergunta(pergunta);
		
		assertEquals(action.delete(), "success");
	}

	@Test
	public void testGetSet() throws Exception
	{
		assertNotNull(action.getAvaliacao());
		assertTrue(action.getAvaliacao() instanceof Avaliacao);
	}
}
