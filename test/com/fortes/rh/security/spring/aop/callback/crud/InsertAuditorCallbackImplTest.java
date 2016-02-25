package com.fortes.rh.security.spring.aop.callback.crud;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import junit.framework.TestCase;
import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;

import com.fortes.business.GenericManager;
import com.fortes.rh.business.sesmt.EventoManagerImpl;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class InsertAuditorCallbackImplTest extends TestCase {

	AuditorCallback callback;
	private MetodoInterceptado metodoMockado;
	
	private static final String DADOS_ESPERADOS = "[DADOS ATUALIZADOS]\n"
													+ "{\n"
//													+ "  \"dependenciasDesconsideradasNaRemocao\": [],\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Natal\"\n"
													+ "}";
	
	protected void setUp() {
		metodoMockado = this.mockaMetodoInterceptado();
		callback = new InsertAuditorCallbackImpl();
	}
	
	public void testDeveriaProcessar() throws Throwable {
		
		Auditavel auditavel = callback.processa(metodoMockado);
		
		assertEquals("Cadastro de Eventos", auditavel.getModulo());
		assertEquals("Inserção", auditavel.getOperacao());
		assertEquals("Natal", auditavel.getChave());
		assertEquals(DADOS_ESPERADOS, auditavel.getDados());
	}
	
	private MetodoInterceptado mockaMetodoInterceptado() {
		return new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoSave());
	}
	
	/**
	 * Mocka <code>MethodInvocation</code> para simular o teste na chamada
	 * do metodo save(entity).
	 */
	private MethodInvocation mockaMethodInvocationParaMetodoSave() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(GenericManager.class)
								.reflect().method("save")
								.withArgs(Evento.class);
				return m;
			}
			public Object[] getArguments() {
				Evento evento = new Evento();
				evento.setId(null);
				evento.setNome("Natal");
				evento.getDependenciasDesconsideradasNaRemocao();
				return new Object[]{evento};
			}
			public AccessibleObject getStaticPart() {
				return null;
			}
			public Object getThis() {
				return new EventoManagerImpl();
			}
			public Object proceed() throws Throwable {
				Evento evento = new Evento();
				evento.setId(2L);
				evento.setNome("Natal");
				evento.getDependenciasDesconsideradasNaRemocao();
				return evento;
			}
		};
	}
	
}
