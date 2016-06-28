package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Date;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class OrdemDeServicoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel auditaImpressao(MetodoInterceptado metodo) throws Throwable {
		OrdemDeServico ordemDeServico = (OrdemDeServico) metodo.getParametros()[0];
		Usuario usuario = (Usuario) metodo.getParametros()[1];
		
		String dados = "Colaborador: " + ordemDeServico.getNomeColaborador();
		dados+= "\nEmpresa: " +  ordemDeServico.getNomeEmpresa();
		dados+= "\nNº da revisão: " + ordemDeServico.getRevisao();
		dados+= "\nData da Ordem de Serviço: " + ordemDeServico.getDataFormatada();
		dados+= "\nData da impressão: " + DateUtil.formataDiaMesAno(new Date());
		dados+= "\nUsuário que executou a impressão: " + usuario.getNome();
		
		metodo.processa();
			return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Ordem de Serviço", dados);			
	}
}
