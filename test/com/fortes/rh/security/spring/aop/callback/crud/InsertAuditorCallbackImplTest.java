package com.fortes.rh.security.spring.aop.callback.crud;

import junit.framework.TestCase;

import org.aopalliance.intercept.MethodInvocation;

import com.fortes.business.GenericManager;
import com.fortes.rh.business.sesmt.EventoManagerImpl;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class InsertAuditorCallbackImplTest extends TestCase {

	AuditorCallback callback;
	
	private static final String DADOS_ESPERADOS = "[DADOS ATUALIZADOS]\n"
													+ "{\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Natal\"\n"
													+ "}";
	
	protected void setUp() {
		callback = new InsertAuditorCallbackImpl();
	}
	
	@SuppressWarnings("rawtypes")
	public void testDeveriaProcessar() throws Throwable {
		Evento eventoParam = new Evento();
		eventoParam.setId(null);
		eventoParam.setNome("Natal");
		
		Evento eventoRetorno = new Evento();
		eventoRetorno.setId(2L);
		eventoRetorno.setNome("Natal");
		
		MethodInvocation metodoMockado = new MethodInvocationDefault<GenericManager>("save", GenericManager.class, new Object[]{eventoParam}, new EventoManagerImpl(), eventoRetorno);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(metodoMockado));
		
		assertEquals("Cadastro de Eventos", auditavel.getModulo());
		assertEquals("Inserção", auditavel.getOperacao());
		assertEquals("Natal", auditavel.getChave());
		assertEquals(DADOS_ESPERADOS, auditavel.getDados());
	}
	
}
