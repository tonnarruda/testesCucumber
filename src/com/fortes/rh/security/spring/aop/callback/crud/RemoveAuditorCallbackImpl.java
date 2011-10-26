package com.fortes.rh.security.spring.aop.callback.crud;

import com.fortes.business.GenericManager;
import com.fortes.model.AbstractModel;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.rh.security.spring.aop.ProcuraChaveNaEntidade;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class RemoveAuditorCallbackImpl implements AuditorCallback {

	private AbstractModel entidade;
	
	private String modulo;
	private String operacao;
	private String dados;
	private String chave;
	
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		
		inicializa(metodo);
		metodo.processa();
		
		return new AuditavelImpl(modulo, operacao, chave, dados);
	}

	private String getChave() {
		return new ProcuraChaveNaEntidade(entidade).procura();
	}

	private String geraDados() {
		return new GeraDadosAuditados(new Object[]{entidade}, null).gera();
	}
	
	private void inicializa(MetodoInterceptado metodo) {
		entidade = carregaEntidade(metodo);
		modulo = metodo.getModulo();
		operacao = metodo.getOperacao();
		dados = geraDados();
		chave = getChave();
	}

	@SuppressWarnings("unchecked")
	private AbstractModel carregaEntidade(MetodoInterceptado metodo) {
		GenericManager manager = (GenericManager) metodo.getComponente();
		return (AbstractModel) manager.findEntidadeComAtributosSimplesById((Long) metodo.getParametros()[0]);
	}

}
