package com.fortes.rh.security.spring.aop.callback.crud;

import junit.framework.TestCase;

import org.aopalliance.intercept.MethodInvocation;

import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.SomeManager;
import com.fortes.rh.security.spring.aop.SomeManagerImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class UpdateAuditorCallbackImplTest extends TestCase {

	AuditorCallback callback;
	
	private static final String DADOS_ESPERADOS = "[DADOS ANTERIORES]\n"
													+ "{\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Minha Festa\"\n"
													+ "}"
													+ "\n\n"
													+ "[DADOS ATUALIZADOS]\n"
													+ "{\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Minha Festa 2010\"\n"
													+ "}";
	
	protected void setUp() {
		callback = new UpdateAuditorCallbackImpl();
	}
	
	public void testDeveriaProcessar() throws Throwable {
		Evento evento = new Evento();
		evento.setId(2L);
		evento.setNome("Minha Festa 2010");
		
		MethodInvocation metodoUpdate = new MethodInvocationDefault<SomeManager>("update", SomeManager.class, new Object[]{evento}, new SomeManagerImpl(), null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(metodoUpdate));
		
		assertEquals("Cadastro de Bugigangas", auditavel.getModulo());
		assertEquals("Atualização Customizada", auditavel.getOperacao());
		assertEquals("Minha Festa 2010", auditavel.getChave());
		assertEquals(DADOS_ESPERADOS, auditavel.getDados());
	}
}
