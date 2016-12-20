package com.fortes.rh.security.spring.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import junit.framework.TestCase;
import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;

import com.fortes.rh.model.sesmt.Evento;
import com.fortes.security.auditoria.MetodoInterceptado;

public class MetodoInterceptadoImplTest extends TestCase {

	MetodoInterceptado metodoInterceptado;
	
	MethodInvocation methodInvocationMock;
	
	boolean metodoFoiInvocado;
	
	protected void setUp() throws Exception {
		metodoFoiInvocado = false;
		methodInvocationMock = this.mockaMethodInvocationParaMetodoUpdate();
		metodoInterceptado = new MetodoInterceptadoImpl(methodInvocationMock);
	}
	
	public void testDeveriaObterNomeDaInterfaceQuandoAnotacaoDeModuloNaoDeclarada() {
		seMetodoInterceptadoNaoPossuiAnotacaoDeModuloDeclarada();
		assertEquals("modulo", "ComponenteSemAnotacaoDeModulo", metodoInterceptado.getModulo());
	}
	
	private void seMetodoInterceptadoNaoPossuiAnotacaoDeModuloDeclarada() {
		methodInvocationMock = this.mockaMethodInvocationParaMetodoSemAnotacaoDeModulo();
		metodoInterceptado = new MetodoInterceptadoImpl(methodInvocationMock);
	}

	private MethodInvocation mockaMethodInvocationParaMetodoSemAnotacaoDeModulo() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(ComponenteSemAnotacaoDeModuloManager.class)
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
				return new ComponenteSemAnotacaoDeModuloManagerImpl();
			}
			public Object proceed() throws Throwable {
				metodoFoiInvocado = true;
				return null;
			}
		};
	}

	public void testDeveriaLancarExcecaoQuandoTentaremObterRetornoDoMetodoAntesDeProcessar() {
		try {
			metodoInterceptado.getRetornoDoMetodo();
			fail("Deveria ter lançado uma IllegalStateException.");
		} catch (Exception e) {
			assertEquals(IllegalStateException.class, e.getClass());
		}
	}
	
	public void testDeveriaSerUmaInstanciaValida() throws Throwable {
		
		Object retorno = metodoInterceptado.processa();
		
		quandoProcessadoSemErro();
		quandoTemRetornoValido(retorno);
		quandoReflectionsForamObtidasCorretamente();
		quandoAnotacoesForamAvaliadasCorretamente();
	}

	private void quandoAnotacoesForamAvaliadasCorretamente() {
		// testa anotacoes
		assertEquals("modulo", "Cadastro de Bugigangas", metodoInterceptado.getModulo());
		assertEquals("operacao", "Atualização Customizada", metodoInterceptado.getOperacao());
	}

	private void quandoReflectionsForamObtidasCorretamente() {
		// testa reflection
		assertEquals(SomeManagerImpl.class, metodoInterceptado.getComponente().getClass());
		assertEquals("update", metodoInterceptado.getMetodo().getName());
		assertEquals(1, metodoInterceptado.getParametros().length);
		assertEquals(Evento.class, metodoInterceptado.getParametros()[0].getClass());
	}

	private void quandoTemRetornoValido(Object retorno) {
		// testa retorno
		assertNull(retorno);
		assertNull(metodoInterceptado.getRetornoDoMetodo());
	}

	private void quandoProcessadoSemErro() {
		// testa execucao
		assertTrue(metodoFoiInvocado);
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
	
}
