package com.fortes.rh.security.spring.aop.callback.crud;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManagerImpl;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.ColaboradorRespostaAuditorCallbackImpl;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

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
	public void testRemoveFicha() throws Throwable {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setTipo(TipoQuestionario.ENTREVISTA);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		
		when(colaboradorRespostaManager.getColaboradorQuestionarioManager()).thenReturn(colaboradorQuestionarioManager);
		when(colaboradorQuestionarioManager.findByIdProjection(1L)).thenReturn(colaboradorQuestionario);

		Auditavel auditavel = callback.processa(this.mockaMetodoIntercepRemoveFicha());

		assertEquals("Entrevista de Desligamento", auditavel.getModulo());
		assertEquals("Remoção de Resposta", auditavel.getOperacao());
		assertEquals("Entrevista de Desligamento - nome colaborador", auditavel.getChave());
		assertEquals("[DADOS ATUALIZADOS]\n{\n  \"Colaborador Desligado\": \"nome colaborador\",\n  \"Tipo Avaliação\": \"Entrevista de Desligamento\"\n}", auditavel.getDados());
	}
	
	private MetodoInterceptado mockaMetodoIntercepRemoveFicha() {
		return new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoRemoveFicha());
	}
	
	private MethodInvocation mockaMethodInvocationParaMetodoRemoveFicha() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(ColaboradorRespostaManager.class)
								.reflect().method("removeFicha")
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
				return colaboradorRespostaManager;
			}
			public Object proceed() throws Throwable {
				return null;
			}
		};
	}
	
}
