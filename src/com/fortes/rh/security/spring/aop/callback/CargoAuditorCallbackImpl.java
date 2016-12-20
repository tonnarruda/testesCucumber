package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class CargoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		Long cargoId = (Long) metodo.getParametros()[0];
		Cargo cargo = carregaEntidade(metodo, cargoId);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{cargo}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), cargo.getNome(), dados);
	}
	
	private Cargo carregaEntidade(MetodoInterceptado metodo, Long cargoId) {
		CargoManager manager = (CargoManager) metodo.getComponente();
		return manager.findByIdAllProjection(cargoId);
	}
}
