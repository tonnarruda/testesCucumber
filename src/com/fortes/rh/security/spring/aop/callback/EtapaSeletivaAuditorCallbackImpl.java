package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class EtapaSeletivaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		EtapaSeletiva etapaSeletiva = (EtapaSeletiva) metodo.getParametros()[0];
		EtapaSeletiva etapaAnterior = (EtapaSeletiva) carregaEntidade(metodo, etapaSeletiva);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{etapaAnterior}, etapaSeletiva).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), etapaSeletiva.getNome(), dados);
	}
	
	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		EtapaSeletiva etapaSeletiva = (EtapaSeletiva) metodo.getParametros()[0];
		EtapaSeletiva etapaAnterior = (EtapaSeletiva) carregaEntidade(metodo, etapaSeletiva);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{etapaAnterior}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), etapaAnterior.getNome(), dados);
	}
	
	private EtapaSeletiva carregaEntidade(MetodoInterceptado metodo, EtapaSeletiva etapaSeletiva) {
		EtapaSeletivaManager manager = (EtapaSeletivaManager) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(etapaSeletiva.getId());
	}
}
