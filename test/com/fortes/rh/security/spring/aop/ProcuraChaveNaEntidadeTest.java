package com.fortes.rh.security.spring.aop;

import org.jmock.MockObjectTestCase;

import com.fortes.model.AbstractModel;
import com.fortes.security.auditoria.ChaveDaAuditoria;

public class ProcuraChaveNaEntidadeTest extends MockObjectTestCase {

	ChaveNaEntidade procuraChaveNaEntidade;
	
	protected void setUp() throws Exception {
		// nothing
	}
	
	public void testDeveriaAcharChaveNoCampo() {
		
		AbstractModel entidade = this.criaEntidadeComChaveNoCampo();
		procuraChaveNaEntidade = new ChaveNaEntidade(entidade);
		
		String chave = procuraChaveNaEntidade.procura();
		assertEquals("chaveNoCampo", chave);
	}
	
	public void testDeveriaAcharChaveNoGetter() {
		
		AbstractModel entidade = this.criaEntidadeComChaveNoGetter();
		procuraChaveNaEntidade = new ChaveNaEntidade(entidade);
		
		String chave = procuraChaveNaEntidade.procura();
		assertEquals("chaveNoGetter", chave);
	}
	
	public void testDeveriaNaoAcharChaveNemNoCampoNemNoGetter() {
		
		AbstractModel entidade = this.criaEntidadeSemChave();
		procuraChaveNaEntidade = new ChaveNaEntidade(entidade);
		
		String chave = procuraChaveNaEntidade.procura();
		assertNull(chave);
	}
	
	public void testDeveriaAcharChaveNoCampoMesmoQuandoHouverAnotacaoNoGetter() {
		
		AbstractModel entidade = this.criaEntidadeComChaveNoCampoENoGetter();
		procuraChaveNaEntidade = new ChaveNaEntidade(entidade);
		
		String chave = procuraChaveNaEntidade.procura();
		assertEquals("chaveNoCampo", chave);
	}

	/**
	 * Cria entidade SEM Chave na entidade.
	 */	
	private AbstractModel criaEntidadeSemChave() {
		return new AbstractModel() {};
	}
	/**
	 * Cria entidade com Chave no campo.
	 */
	private AbstractModel criaEntidadeComChaveNoCampo() {
		return new AbstractModel() {
			@ChaveDaAuditoria
			private String campo = "chaveNoCampo";
			public String getCampo() {
				throw new RuntimeException("Nao deveria executar este método.");
			}
		};
	}
	/**
	 * Cria entidade com Chave no getter.
	 */
	private AbstractModel criaEntidadeComChaveNoGetter() {
		return new AbstractModel() {
			private String campo = "chaveNoCampo";
			@ChaveDaAuditoria
			public String getCampo() {
				return "chaveNoGetter";
			}
		};
	}
	/**
	 * Cria entidade com Chave no campo e getter.
	 */
	private AbstractModel criaEntidadeComChaveNoCampoENoGetter() {
		return new AbstractModel() {
			@ChaveDaAuditoria
			private String campo = "chaveNoCampo";
			@ChaveDaAuditoria
			public String getCampo() {
				return "chaveNoGetter";
			}
		};
	}
	
}
