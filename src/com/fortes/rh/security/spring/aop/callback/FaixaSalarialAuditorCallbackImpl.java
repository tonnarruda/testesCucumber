package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class FaixaSalarialAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel saveFaixaSalarial(MetodoInterceptado metodo) throws Throwable 
	{
		try{
			FaixaSalarial faixa = (FaixaSalarial) metodo.getParametros()[0];
			metodo.processa();
			String dados = new DadosAuditados(null, faixa).gera();

			return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), faixa.getDescricao(), dados);
		} catch (Exception e) {
			System.out.println("Auditoria ao salvar Faixa Salarial não funcionou.");
			e.printStackTrace();
			return null;
		}
	}

	public Auditavel deleteFaixaSalarial(MetodoInterceptado metodo) throws Throwable 
	{
		try {
			FaixaSalarial faixa = new FaixaSalarial();
			faixa.setId((Long)metodo.getParametros()[0]);
			faixa = carregaEntidade(metodo, faixa);
			String dados = new DadosAuditados(new Object[]{faixa}, null).gera();

			metodo.processa();
			return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), faixa.getDescricao(), dados);
		} catch (Exception e) {
			System.out.println("Auditoria ao deletar Faixa Salarial não funcionou.");
			e.printStackTrace();
			return null;
		}
	}

	private FaixaSalarial carregaEntidade(MetodoInterceptado metodo, FaixaSalarial faixaSalarial) {
		FaixaSalarialManager manager = (FaixaSalarialManager) metodo.getComponente();
		return manager.findById(faixaSalarial.getId());
	}
}
