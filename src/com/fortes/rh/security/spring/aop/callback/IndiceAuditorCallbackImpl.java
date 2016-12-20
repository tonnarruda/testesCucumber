package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class IndiceAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		Indice indice = (Indice) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new DadosAuditados(null, indice).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), indice.getNome(), dados);
	}
	
	public Auditavel removeIndice(MetodoInterceptado metodo) throws Throwable {
		
		Long indiceId = (Long) metodo.getParametros()[0];
		Indice indice = new Indice();
		indice.setId(indiceId);
		indice = carregaEntidade(metodo, indice);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{indice}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), indice.getNome(), dados);
	}
	
	private Indice carregaEntidade(MetodoInterceptado metodo, Indice indice) {
		IndiceManager manager = (IndiceManager) metodo.getComponente();
		return manager.findByIdProjection(indice.getId());
	}
}
