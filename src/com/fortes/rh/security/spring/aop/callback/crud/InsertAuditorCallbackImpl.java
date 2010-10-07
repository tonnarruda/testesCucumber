package com.fortes.rh.security.spring.aop.callback.crud;

import com.fortes.model.AbstractModel;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.rh.security.spring.aop.ProcuraChaveNaEntidade;
import com.fortes.rh.security.spring.aop.callback.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class InsertAuditorCallbackImpl implements AuditorCallback {

	private AbstractModel entidade;
	
	private String modulo;
	private String operacao;
	private String dados;
	private String chave;
	
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		
		entidade = (AbstractModel) metodo.processa();
		inicializa(metodo);
		
		return new AuditavelImpl(modulo, operacao, chave, dados);
	}

	private String getChave() {
		return new ProcuraChaveNaEntidade(entidade).procura();
	}

	private String geraDados() {
		return new GeraDadosAuditados(null, entidade).gera();
	}
	
	private void inicializa(MetodoInterceptado metodo) {
		modulo = metodo.getModulo();
		operacao = metodo.getOperacao();
		dados = geraDados();
		chave = getChave();
	}

}
