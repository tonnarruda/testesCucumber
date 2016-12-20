package com.fortes.rh.security.spring.aop.callback.crud;

import com.fortes.model.AbstractModel;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.rh.security.spring.aop.callback.AuditorCallbackImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.MetodoInterceptado;

public class UpdateAuditorCallbackImpl extends AuditorCallbackImpl {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		
		inicializa(metodo);
		metodo.processa();
		
		return new AuditavelImpl(getModulo(), getOperacao(), getChave(), getDados());
	}

	@Override
	protected String geraDadosAuditados() {
		return new DadosAuditados(new Object[]{getEntidadeAntesDaAtualizacao()}, getEntidade()).gera();
	}
	
	@Override
	protected void inicializa(MetodoInterceptado metodo) {
		setEntidade((AbstractModel) metodo.getParametros()[0]);
		setEntidadeAntesDaAtualizacao(carregaEntidade(metodo));
		setModulo(metodo.getModulo());
		setOperacao(metodo.getOperacao());
		setDados(geraDadosAuditados());
		setChave(getChaveNaEntidade());
	}

}
