package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class EmpresaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel auditaIntegracao(MetodoInterceptado metodo) throws Throwable {
		
		Empresa empresa = (Empresa) metodo.getParametros()[0];
		boolean tavaIntegradaComAC = (Boolean) metodo.getParametros()[1];
		
		metodo.processa();
		
		if(tavaIntegradaComAC != empresa.isAcIntegra())
		{
			String integrado = tavaIntegradaComAC ? "Sim" : "Não";
			String dados = "[DADOS ANTERIORES]  Integrado: " + integrado;
			integrado = empresa.isAcIntegra() ? "Sim" : "Não";
			dados += "\n[DADOS ATUALIZADOS] Integrado: " + integrado;
			dados += "\n                    Grupo AC: " + empresa.getGrupoAC();
			
			return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), empresa.getNome() + " (id: " + empresa.getId() + ")", dados);			
		}
		else
			return null;
	}
	
	public Auditavel removeEmpresa(MetodoInterceptado metodo) throws Throwable {
		
		Empresa empresa = (Empresa) metodo.getParametros()[0];
		String dados = new GeraDadosAuditados(new Object[]{empresa}, null).gera();
		metodo.processa();
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), empresa.getNome() + " (id: " + empresa.getId() + ")", dados);			
	}
}
