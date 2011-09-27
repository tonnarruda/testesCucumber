package com.fortes.rh.security.spring.aop.callback;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class InsertFaixaSalarialAuditorCallbackImpl implements AuditorCallback {

	private FaixaSalarial faixaSalarial;
	
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		
		inicializa(metodo);
		
		metodo.processa(); // processa metodo
		
		String modulo = metodo.getModulo();
		String operacao = metodo.getOperacao();
		String dados = geraDados();
		String chave = getChave();
		
		return new AuditavelImpl(modulo, operacao, chave, dados);
	}

	private String getChave() {
		return faixaSalarial.getNome();
	}

	private String geraDados() {
		return new GeraDadosAuditados(new Object[]{faixaSalarial}, null).gera();
	}

	private void inicializa(MetodoInterceptado metodo) {
		faixaSalarial = (FaixaSalarial) metodo.getParametros()[0];
	}

}
