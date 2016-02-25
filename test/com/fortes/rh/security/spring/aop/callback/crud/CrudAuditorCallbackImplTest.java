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
import com.fortes.rh.security.spring.aop.SomeManager;
import com.fortes.rh.security.spring.aop.SomeManagerImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class CrudAuditorCallbackImplTest extends TestCase {

	AuditorCallback callback;
	private MetodoInterceptado metodoMockado;
	
	private static final String DADOS_ESPERADOS_SAVE = "[DADOS ATUALIZADOS]\n"
													+ "{\n"
//													+ "  \"dependenciasDesconsideradasNaRemocao\": [],\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Natal\"\n"
													+ "}";
	
	private static final String DADOS_ESPERADOS_UPDATE = "[DADOS ANTERIORES]\n"
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
	
	private static final String DADOS_ESPERADOS_REMOVE = "[DADOS ANTERIORES]\n"
													+ "{\n"
//													+ "  \"dependenciasDesconsideradasNaRemocao\": [],\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Minha Festa\"\n"
													+ "}";
	
	protected void setUp() {
		callback = new CrudAuditorCallbackImpl();
	}
	
	public void testDeveriaLancarIllegalArgumentExceptionSeNenhumMetodoDeCrudFoiChamado() {
		
		seMetodoNaoCrudFoiChamado();
		
		try {
			callback.processa(metodoMockado);
			fail("Deveria ter lançado uma exception quando metodo chamado não foi de CRUD (save, update ou delete).");
		} catch (Throwable e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
		
	}
	
	public void testDeveriaProcessarCallbackQuandoMetodoUpdateForChamado() throws Throwable {
		
		seMetodoUpdateFoiChamado();
		
		Auditavel auditavel = callback.processa(metodoMockado);
		
		entaoDeveriaAuditarOsSeguintesDados("Cadastro de Bugigangas", 
				"Atualização Customizada", "Minha Festa 2010", DADOS_ESPERADOS_UPDATE, auditavel);
	}

	public void testDeveriaProcessarCallbackQuandoMetodoSaveForChamado() throws Throwable {
		
		seMetodoSaveFoiChamado();
		
		Auditavel auditavel = callback.processa(metodoMockado);
		
		entaoDeveriaAuditarOsSeguintesDados("Cadastro de Eventos", 
				"Inserção", "Natal", DADOS_ESPERADOS_SAVE, auditavel);
	}
	
	public void testDeveriaProcessarCallbackQuandoMetodoRemoveForChamado() throws Throwable {
		
		seMetodoRemoveFoiChamado();
		
		Auditavel auditavel = callback.processa(metodoMockado);
		
		entaoDeveriaAuditarOsSeguintesDados("Cadastro de Bugigangas", 
				"Remoçao Customizada", "Minha Festa", DADOS_ESPERADOS_REMOVE, auditavel);
	}
	
	private void entaoDeveriaAuditarOsSeguintesDados(String modulo, String operacao, String chave, String dados, Auditavel auditavel) {
		assertEquals(modulo, auditavel.getModulo());
		assertEquals(operacao, auditavel.getOperacao());
		assertEquals(chave, auditavel.getChave());
		assertEquals(dados, auditavel.getDados());
	}
	
	private void seMetodoNaoCrudFoiChamado() {
		metodoMockado = new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoNaoCrud());
	}
	private void seMetodoUpdateFoiChamado() {
		metodoMockado = new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoUpdate());
	}
	private void seMetodoSaveFoiChamado() {
		metodoMockado = new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoSave());
	}
	private void seMetodoRemoveFoiChamado() {
		metodoMockado = new MetodoInterceptadoImpl(this.mockaMethodInvocationParaMetodoRemove());
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
				return new Object[]{new Long(2)};
			}
			public AccessibleObject getStaticPart() {
				return null;
			}
			public Object getThis() {
				// se redefinir a classe aqui ele dá erro no teste (bizarro!)
				return new SomeManagerImpl();
			}
			public Object proceed() throws Throwable {
				return null;
			}
		};
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
				return evento;
			}
		};
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
	/**
	 * Mocka <code>MethodInvocation</code> para simular o teste na chamada
	 * do metodo NAO CRUD.
	 */
	private MethodInvocation mockaMethodInvocationParaMetodoNaoCrud() {
		return new MethodInvocation() {
			public Method getMethod() {
				Method m = new Mirror().on(SomeManager.class)
								.reflect().method("getCount")
								.withoutArgs();
				return m;
			}
			public Object[] getArguments() {
				return new Object[]{null};
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
