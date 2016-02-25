package com.fortes.rh.security.spring.aop.callback.crud;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import junit.framework.TestCase;
import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;

import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.SomeManager;
import com.fortes.rh.security.spring.aop.SomeManagerImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class UpdateAuditorCallbackImplTest extends TestCase {

	AuditorCallback callback;
	private MetodoInterceptado metodoMockado;
	
	private static final String DADOS_ESPERADOS = "[DADOS ANTERIORES]\n"
													+ "{\n"
//													+ "  \"dependenciasDesconsideradasNaRemocao\": [],\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Minha Festa\"\n"
													+ "}"
													+ "\n\n"
													+ "[DADOS ATUALIZADOS]\n"
													+ "{\n"
//													+ "  \"dependenciasDesconsideradasNaRemocao\": [],\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Minha Festa 2010\"\n"
													+ "}";
	
	protected void setUp() {
		metodoMockado = this.mockaMetodoInterceptado();
		callback = new UpdateAuditorCallbackImpl();
	}
	
	public void testDeveriaProcessar() throws Throwable {
		
		Auditavel auditavel = callback.processa(metodoMockado);
		
		assertEquals("Cadastro de Bugigangas", auditavel.getModulo());
		assertEquals("Atualização Customizada", auditavel.getOperacao());
		assertEquals("Minha Festa 2010", auditavel.getChave());
		assertEquals(DADOS_ESPERADOS, auditavel.getDados());
	}
	
	private MetodoInterceptado mockaMetodoInterceptado() {
		return new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoUpdate());
	}
	
	/**
	 * Mocka <code>MethodInvocation</code> para simular o teste na chamada
	 * do metodo update(entity).
	 */
	private MethodInvocation mockaMethodInvocationParaMetodoUpdate() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(SomeManager.class)
								.reflect().method("update")
								.withArgs(Evento.class);
				return m;
			}
			public Object[] getArguments() {
				Evento evento = new Evento();
				evento.setId(2L);
				evento.setNome("Minha Festa 2010");
				evento.getDependenciasDesconsideradasNaRemocao();
				return new Object[]{evento};
			}
			public AccessibleObject getStaticPart() {
				return null;
			}
			public Object getThis() {
				return new SomeManagerImpl();
			}
			public Object proceed() throws Throwable {
				return null;
			}
		};
	}
	
}
