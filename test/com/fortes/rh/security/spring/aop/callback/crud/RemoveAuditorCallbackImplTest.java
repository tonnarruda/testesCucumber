package com.fortes.rh.security.spring.aop.callback.crud;

import junit.framework.TestCase;

import org.aopalliance.intercept.MethodInvocation;

import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.SomeManager;
import com.fortes.rh.security.spring.aop.SomeManagerImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class RemoveAuditorCallbackImplTest extends TestCase {

	AuditorCallback callback;
	
	private static final String DADOS_ESPERADOS = "[DADOS ANTERIORES]\n"
													+ "{\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Minha Festa\"\n"
													+ "}";
	
	protected void setUp() {
		callback = new RemoveAuditorCallbackImpl();
	}
	
	public void testDeveriaProcessar() throws Throwable {
		
		MethodInvocation metodoUpdate = new MethodInvocationDefault<SomeManager>("remove", SomeManager.class, new Object[]{new Long(2L)}, new SomeManagerImpl(), null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(metodoUpdate));
		
		assertEquals("Cadastro de Bugigangas", auditavel.getModulo());
		assertEquals("Remoçao Customizada", auditavel.getOperacao());
		assertEquals("Minha Festa", auditavel.getChave());
		assertEquals(DADOS_ESPERADOS, auditavel.getDados());
	}
	
}
