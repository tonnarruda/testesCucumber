package com.fortes.rh.security.spring.aop;

import com.fortes.security.auditoria.Auditavel;

/**
 * Implementação básica de um Auditavel.
 */
public  class AuditavelImpl implements Auditavel {
		
		private String chave;
		private String dados;
		private String modulo;
		private String operacao;
		
		public AuditavelImpl(String modulo, String operacao, String chave, String dados) {
			this.modulo = modulo;
			this.operacao = operacao;
			this.chave = chave;
			this.dados = dados;
		}
		
		public String getChave() {
			return chave;
		}
		public String getDados() {
			return dados;
		}
		public String getModulo() {
			return modulo;
		}
		public String getOperacao() {
			return operacao;
		}
		
	}