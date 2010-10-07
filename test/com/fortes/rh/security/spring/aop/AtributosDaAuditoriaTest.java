package com.fortes.rh.security.spring.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.security.spring.aop.callback.MetodoInterceptadoImpl;

public class AtributosDaAuditoriaTest extends MockObjectTestCase {

	AtributosDaAuditoria atributos;
	MethodInvocation methodInvocation;
	
	boolean metodoFoiInvocado = false;
	
	protected void setUp() throws Exception {
		metodoFoiInvocado = false;
	}
	
	public void testDeveriaCarregarAtributosDaAuditoria() throws Throwable {
		
		this.methodInvocation = this.mockaMethodInvocationParaMetodoRemove();
		this.atributos = criaNovoAtributosDaAuditoria();
		atributos = atributos.carrega();
		
		assertEquals("Minha Festa", atributos.getChave());
		assertEquals("[DADOS ANTERIORES]\n29", atributos.getDados());
		assertEquals("Cadastro de Bugigangas", atributos.getModulo());
		assertEquals("Remoçao Customizada", atributos.getOperacao());
		assertNull(atributos.getResultado());
		assertTrue(metodoFoiInvocado);
	}
	
	public void testDeveriaSerFetchMode() {
		this.methodInvocation = this.mockaMethodInvocationParaMetodoRemove();
		this.atributos = criaNovoAtributosDaAuditoria();
		boolean fetch = this.atributos.isFetchMode();
		assertTrue(fetch);
	}

	private AtributosDaAuditoria criaNovoAtributosDaAuditoria() {
		return new AtributosDaAuditoria(new MetodoInterceptadoImpl(methodInvocation));
	}
	
	public void testDeveriaNaoSerFetchMode() {
		this.methodInvocation = this.mockaMethodInvocationParaMetodoUpdate();
		this.atributos = criaNovoAtributosDaAuditoria();
		boolean fetch = this.atributos.isFetchMode();
		assertFalse(fetch);
	}
	
	/**
	 * Mocka <code>MethodInvocation</code> para simular o teste na chamada
	 * do metodo update(evento).
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
				return new Object[]{new Evento()};
			}
			public AccessibleObject getStaticPart() {
				return null;
			}
			public Object getThis() {
				// se redefinir a classe aqui ele dá erro no teste (bizarro!)
				return new SomeManagerImpl();
			}
			public Object proceed() throws Throwable {
				metodoFoiInvocado = true;
				return null;
			}
		};
	}
	/**
	 * Mocka <code>MethodInvocation</code> para simular o teste na chamada
	 * do metodo remove(long).
	 */
	private MethodInvocation mockaMethodInvocationParaMetodoRemove() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(SomeManager.class)
								.reflect().method("remove")
								.withArgs(Long.class);
				return m;
			}
			public Object[] getArguments() {
				return new Object[]{new Long(29)};
			}
			public AccessibleObject getStaticPart() {
				return null;
			}
			public Object getThis() {
				// se redefinir a classe aqui ele dá erro no teste (bizarro!)
				return new SomeManagerImpl();
			}
			public Object proceed() throws Throwable {
				metodoFoiInvocado = true;
				return null;
			}
		};
	}
	
}
