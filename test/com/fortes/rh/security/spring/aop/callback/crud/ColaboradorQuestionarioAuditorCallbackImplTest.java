package com.fortes.rh.security.spring.aop.callback.crud;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.ColaboradorQuestionarioAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class ColaboradorQuestionarioAuditorCallbackImplTest {

	AuditorCallback callback;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	
	@Before
	public void setUp() {
		colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
		callback = new ColaboradorQuestionarioAuditorCallbackImpl();
	}
	
	@Test
	public void testDeleteRespostaAvaliacaoDesempenho() throws Throwable {
		ColaboradorQuestionario colabQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colabQuestionario.setAvaliacaoDesempenho(AvaliacaoDesempenhoFactory.getEntity(2L));
		colabQuestionario.setColaborador(ColaboradorFactory.getEntity(2L));
		
		when(colaboradorQuestionarioManager.findByIdProjection(1L)).thenReturn(colabQuestionario);

		MethodInvocation deleteRespostaAvaliacaoDesempenho = new MethodInvocationDefault<ColaboradorQuestionarioManager>("deleteRespostaAvaliacaoDesempenho", ColaboradorQuestionarioManager.class, new Object[]{1L}, colaboradorQuestionarioManager);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(deleteRespostaAvaliacaoDesempenho));

		assertEquals("ColaboradorQuestionario", auditavel.getModulo());
		assertEquals("Remoção de Resposta", auditavel.getOperacao());
		assertEquals("nome colaborador", auditavel.getChave());
		assertEquals("[DADOS ATUALIZADOS]\n{\n  \"Avaliado\": \"nome colaborador\",\n  \"Avaliação Desempenho\": null\n}", auditavel.getDados());
	}
}
