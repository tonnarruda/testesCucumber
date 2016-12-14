package com.fortes.rh.security.spring.aop.callback.crud;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.ColaboradorRespostaAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class ColaboradorRespostaAuditorCallbackImplTest {

	AuditorCallback callback;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	
	@Before
	public void setUp() {
		colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
		colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
		callback = new ColaboradorRespostaAuditorCallbackImpl();
	}
	
	@Test
	public void testRemoveFichaEntrevistaDeDesligamento() throws Throwable {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		Questionario questionario = QuestionarioFactory.getEntity(1L, "Entrevista de Desligamento");
		questionario.setTipo(TipoQuestionario.ENTREVISTA);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		
		when(colaboradorRespostaManager.getColaboradorQuestionarioManager()).thenReturn(colaboradorQuestionarioManager);
		when(colaboradorQuestionarioManager.findByIdProjection(1L)).thenReturn(colaboradorQuestionario);

		MethodInvocation removeFicha = new MethodInvocationDefault<ColaboradorRespostaManager>("removeFicha", ColaboradorRespostaManager.class, new Object[]{1L}, colaboradorRespostaManager, null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(removeFicha));

		assertEquals("Entrevista de Desligamento", auditavel.getModulo());
		assertEquals("Remoção de Resposta", auditavel.getOperacao());
		assertEquals("Entrevista de Desligamento - nome colaborador", auditavel.getChave());
		assertEquals("[DADOS ATUALIZADOS]\n{\n  \"Colaborador Desligado\": \"nome colaborador\",\n  \"Modelo de Entrevista\": \"Entrevista de Desligamento\"\n}", auditavel.getDados());
	}
	
	@Test
	public void testRemoveFichaFichaMedica() throws Throwable {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		Questionario questionario = QuestionarioFactory.getEntity(1L, "Admissional");
		questionario.setTipo(TipoQuestionario.FICHAMEDICA);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		
		when(colaboradorRespostaManager.getColaboradorQuestionarioManager()).thenReturn(colaboradorQuestionarioManager);
		when(colaboradorQuestionarioManager.findByIdProjection(1L)).thenReturn(colaboradorQuestionario);

		MethodInvocation removeFicha = new MethodInvocationDefault<ColaboradorRespostaManager>("removeFicha", ColaboradorRespostaManager.class, new Object[]{1L}, colaboradorRespostaManager, null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(removeFicha));

		assertEquals("Ficha Médica", auditavel.getModulo());
		assertEquals("Remoção de Resposta", auditavel.getOperacao());
		assertEquals("Ficha Médica - nome colaborador", auditavel.getChave());
		assertEquals("[DADOS ATUALIZADOS]\n{\n  \"Colaborador\": \"nome colaborador\",\n  \"Ficha\": \"Admissional\"\n}", auditavel.getDados());
	}
}
