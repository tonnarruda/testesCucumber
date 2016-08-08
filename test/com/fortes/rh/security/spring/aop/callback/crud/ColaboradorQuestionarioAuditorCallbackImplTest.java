package com.fortes.rh.security.spring.aop.callback.crud;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.ColaboradorQuestionarioAuditorCallbackImpl;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

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

		Auditavel auditavel = callback.processa(this.mockaMetodoIntercepDeleteRespostaAvaliacaoDesempenho());

		assertEquals("ColaboradorQuestionario", auditavel.getModulo());
		assertEquals("Remoção de Resposta", auditavel.getOperacao());
		assertEquals("nome colaborador", auditavel.getChave());
		assertEquals("[DADOS ATUALIZADOS]\n{\n  \"Avaliado\": \"nome colaborador\",\n  \"Avaliação Desempenho\": null\n}", auditavel.getDados());
	}
	
	private MetodoInterceptado mockaMetodoIntercepDeleteRespostaAvaliacaoDesempenho() {
		return new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoDeleteRespostaAvaliacaoDesempenho());
	}
	
	private MethodInvocation mockaMethodInvocationParaMetodoDeleteRespostaAvaliacaoDesempenho() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(ColaboradorQuestionarioManager.class)
								.reflect().method("deleteRespostaAvaliacaoDesempenho")
								.withArgs(Long.class);
				return m;
			}
			public Object[] getArguments() {
				return new Object[]{1L};
			}
			public AccessibleObject getStaticPart() {
				return null;
			}
			public Object getThis() {
				return colaboradorQuestionarioManager;
			}
			public Object proceed() throws Throwable {
				return null;
			}
		};
	}
	
	@Test
	public void testRemoveByColaboradorAndQuestionario() throws Throwable {
		Auditavel auditavel = callback.processa(this.mockaMetodoIntercepRemoveByColaboradorAndQuestionario());
		
		assertEquals("Entrevista de Desligamento", auditavel.getModulo());
		assertEquals("Remoção de Resposta", auditavel.getOperacao());
		assertEquals("Entrevista de Desligamento - nome colaborador", auditavel.getChave());
		assertEquals("[DADOS ATUALIZADOS]\n{\n  \"Colaborador Desligado\": \"nome colaborador\",\n  \"Tipo Avaliação\": \"Entrevista de Desligamento\"\n}", auditavel.getDados());
	}
	
	private MetodoInterceptado mockaMetodoIntercepRemoveByColaboradorAndQuestionario() {
		return new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoRemoveByColaboradorAndQuestionario());
	}
	
	private MethodInvocation mockaMethodInvocationParaMetodoRemoveByColaboradorAndQuestionario() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(ColaboradorQuestionarioManager.class)
								.reflect().method("removeByColaboradorAndQuestionario")
								.withArgs(Colaborador.class, Questionario.class);
				return m;
			}
			public Object[] getArguments() {
				Questionario questionario = QuestionarioFactory.getEntity(1L);
				questionario.setTipo(TipoQuestionario.ENTREVISTA);
				return new Object[]{ColaboradorFactory.getEntity(1L), questionario};
			}
			public AccessibleObject getStaticPart() {
				return null;
			}
			public Object getThis() {
				return colaboradorQuestionarioManager;
			}
			public Object proceed() throws Throwable {
				return null;
			}
		};
	}
	
}
