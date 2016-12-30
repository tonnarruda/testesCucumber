package com.fortes.rh.security.spring.aop.callback.crud;

import com.fortes.model.AbstractModel;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.rh.security.spring.aop.callback.AuditorCallbackImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.MetodoInterceptado;

public class UpdateAuditorCallbackImpl extends AuditorCallbackImpl {

	//MUITO CUIDADO AO MEXER NAS ORDENS DOS METODOS ABAIXO.
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		
		setEntidadeAntesDaAtualizacao(carregaEntidade(metodo));
		setEntidade((AbstractModel) metodo.getParametros()[0]);
		inicializa(metodo);
		metodo.processa();
		
		return new AuditavelImpl(getModulo(), getOperacao(), getChave(), getDados());
	}

	@Override
	protected String geraDadosAuditados() {
		return new DadosAuditados(new Object[]{getEntidadeAntesDaAtualizacao()}, getEntidade()).gera();
	}

}
