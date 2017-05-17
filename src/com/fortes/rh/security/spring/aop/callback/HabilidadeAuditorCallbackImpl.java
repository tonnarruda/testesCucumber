package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class HabilidadeAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel removeComDependencia(MetodoInterceptado metodo) throws Throwable 
	{
		Long habilidadeId = (Long) metodo.getParametros()[0];
		Habilidade habilidade = carregaEntidade(metodo, habilidadeId);
		
		metodo.processa();
		
		String dados = "[DADOS EXCLUIDOS]\n";
		try {
			dados+="\nId: " + habilidade.getId();
			dados+="\nNome: " + habilidade.getNome();
			dados+="\nEmpresa Id: " + habilidade.getEmpresa().getId();
		} catch (Exception e) {
			System.out.println("Problema ao auditar a remoção de Habilidade");
			e.printStackTrace();
		}
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), habilidade.getNome(), dados);
	}
	
	private Habilidade carregaEntidade(MetodoInterceptado metodo, Long habilidadeId) {
		HabilidadeManager manager = (HabilidadeManager) metodo.getComponente();
		return manager.findByIdProjection(habilidadeId);
	}
}
