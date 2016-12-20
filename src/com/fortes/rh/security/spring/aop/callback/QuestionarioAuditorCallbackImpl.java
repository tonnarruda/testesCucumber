package com.fortes.rh.security.spring.aop.callback;

import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class QuestionarioAuditorCallbackImpl implements AuditorCallback {

	private Questionario questionario;
	
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
		return questionario.getTitulo();
	}

	private String geraDados() {
		return new DadosAuditados(new Object[]{questionario}, null).gera();
	}

	private void inicializa(MetodoInterceptado metodo) {
		QuestionarioManager manager = (QuestionarioManager) metodo.getComponente();
		questionario = manager.findEntidadeComAtributosSimplesById((Long) metodo.getParametros()[0]);
	}

}
