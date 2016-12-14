package com.fortes.rh.security.spring.aop.callback.crud;

import junit.framework.TestCase;

import org.aopalliance.intercept.MethodInvocation;

import com.fortes.business.GenericManager;
import com.fortes.rh.business.sesmt.EventoManagerImpl;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.SomeManager;
import com.fortes.rh.security.spring.aop.SomeManagerImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class CrudAuditorCallbackImplTest extends TestCase {

	AuditorCallback callback;
	
	private static final String DADOS_ESPERADOS_SAVE = "[DADOS ATUALIZADOS]\n"
													+ "{\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Natal\"\n"
													+ "}";
	
	private static final String DADOS_ESPERADOS_UPDATE = "[DADOS ANTERIORES]\n"
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
	
	private static final String DADOS_ESPERADOS_REMOVE = "[DADOS ANTERIORES]\n"
													+ "{\n"
													+ "  \"id\": 2,\n"
													+ "  \"nome\": \"Minha Festa\"\n"
													+ "}";
	
	protected void setUp() {
		callback = new CrudAuditorCallbackImpl();
	}
	
	public void testDeveriaLancarIllegalArgumentExceptionSeNenhumMetodoDeCrudFoiChamado() {
		
		try {
			MethodInvocation update = new MethodInvocationDefault<SomeManager>("getCount", SomeManager.class, new Object[]{null}, new SomeManagerImpl(), null);
			callback.processa(new MetodoInterceptadoImpl(update));

			fail("Deveria ter lançado uma exception quando metodo chamado não foi de CRUD (save, update ou delete).");
		} catch (Throwable e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
		
	}
	
	public void testDeveriaProcessarCallbackQuandoMetodoUpdateForChamado() throws Throwable {
		
		Evento evento = new Evento();
		evento.setId(2L);
		evento.setNome("Minha Festa 2010");
		
		MethodInvocation update = new MethodInvocationDefault<SomeManager>("update", SomeManager.class, new Object[]{evento}, new SomeManagerImpl(), null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(update));
		
		entaoDeveriaAuditarOsSeguintesDados("Cadastro de Bugigangas", 
				"Atualização Customizada", "Minha Festa 2010", DADOS_ESPERADOS_UPDATE, auditavel);
	}

	@SuppressWarnings("rawtypes")
	public void testDeveriaProcessarCallbackQuandoMetodoSaveForChamado() throws Throwable {
		Evento eventoRertorno = new Evento();
		eventoRertorno.setId(2L);
		eventoRertorno.setNome("Natal");
		
		MethodInvocation save = new MethodInvocationDefault<GenericManager>("save", GenericManager.class, new Object[]{2L}, new EventoManagerImpl(), eventoRertorno);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(save));
		
		entaoDeveriaAuditarOsSeguintesDados("Cadastro de Eventos", 
				"Inserção", "Natal", DADOS_ESPERADOS_SAVE, auditavel);
	}
	
	public void testDeveriaProcessarCallbackQuandoMetodoRemoveForChamado() throws Throwable {
		
		MethodInvocation remove = new MethodInvocationDefault<SomeManager>("remove", SomeManager.class, new Object[]{2L}, new SomeManagerImpl(), null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(remove));

		entaoDeveriaAuditarOsSeguintesDados("Cadastro de Bugigangas", 
				"Remoçao Customizada", "Minha Festa", DADOS_ESPERADOS_REMOVE, auditavel);
	}
	
	private void entaoDeveriaAuditarOsSeguintesDados(String modulo, String operacao, String chave, String dados, Auditavel auditavel) {
		assertEquals(modulo, auditavel.getModulo());
		assertEquals(operacao, auditavel.getOperacao());
		assertEquals(chave, auditavel.getChave());
		assertEquals(dados, auditavel.getDados());
	}
	
}
