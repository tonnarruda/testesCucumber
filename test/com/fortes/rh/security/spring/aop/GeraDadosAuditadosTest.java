package com.fortes.rh.security.spring.aop;

import junit.framework.TestCase;

import com.fortes.rh.model.sesmt.Evento;

public class GeraDadosAuditadosTest extends TestCase {

	/*
	 * O atributo chaveParaAuditoria foi removido da Entidade principal e está sendo
	 * gerado apenas para as entidades aninhadas a entidade principal, pois o atributo é
	 * somente relevante nas entidades filhas.
	 */
	private static final String ENTIDADE_FORMATADA = 
						"[DADOS ANTERIORES]\n" +
						"{\n" +
//						"  \"chaveParaAuditoria\": \"Carnaval\",\n" +
//						"  \"dependenciasDesconsideradasNaRemocao\": [],\n" +
						"  \"id\": 1,\n" +
						"  \"nome\": \"Carnaval\"\n" +
						"}" +
						"\n\n[DADOS ATUALIZADOS]\n" +
						"{\n" +
//						"  \"chaveParaAuditoria\": \"Carnaval\",\n" +
//						"  \"dependenciasDesconsideradasNaRemocao\": [],\n"+
						"  \"id\": 1,\n" +
						"  \"nome\": \"Carnaval\"\n" +
						"}";
	
	private static final String ID_FORMATADO = 
						"[DADOS ANTERIORES]\n" +
						"14";
	
	public void testDeveriaGerarJsonParaEntidade() {
		
		Evento parametro = new Evento();
		parametro.setId(1L);
		parametro.setNome("Carnaval");
		parametro.getDependenciasDesconsideradasNaRemocao();
		
		Evento resultado = new Evento();
		resultado.setId(1L);
		resultado.setNome("Carnaval");
		parametro.getDependenciasDesconsideradasNaRemocao();
		
		String dados = new GeraDadosAuditados(new Object[]{parametro}, resultado).gera();
		assertEquals(ENTIDADE_FORMATADA, dados);
	}
	
	public void testDeveriaGerarJsonParaId() {
		String dados = new GeraDadosAuditados(new Object[]{new Long(14)}, null).gera();
		assertEquals(ID_FORMATADO, dados);
	}
}
