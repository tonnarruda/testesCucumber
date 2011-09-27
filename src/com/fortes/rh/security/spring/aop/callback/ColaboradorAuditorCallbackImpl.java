package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.business.GenericManager;
import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ColaboradorAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel insert(MetodoInterceptado metodo) throws Throwable 
	{
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		
		metodo.processa(); // processa metodo
		
		String modulo = metodo.getModulo();
		String operacao = metodo.getOperacao();
		String chave = colaborador.getNome();
		String dados = new GeraDadosAuditados(new Object[]{colaborador}, null).gera();
		
		return new AuditavelImpl(modulo, operacao, chave, dados);
	}
	
	@SuppressWarnings("unchecked")
	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		Colaborador colaboradorAnterior = (Colaborador) carregaEntidade(metodo, colaborador);
		
		metodo.processa(); // processa metodo
		
		String modulo = metodo.getModulo();
		String operacao = metodo.getOperacao();
		String chave = colaborador.getNome();
		String dados = new GeraDadosAuditados(new Object[]{colaboradorAnterior}, colaborador).gera();
		
		return new AuditavelImpl(modulo, operacao, chave, dados);
	}
	
	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		colaborador = carregaEntidade(metodo, colaborador);
		
		metodo.processa(); // processa metodo
		
		String modulo = metodo.getModulo();
		String operacao = metodo.getOperacao();
		String chave = colaborador.getNome();
		String dados = new GeraDadosAuditados(new Object[]{colaborador}, null).gera();
		
		return new AuditavelImpl(modulo, operacao, chave, dados);
	}
	
	@SuppressWarnings("unchecked")
	private Colaborador carregaEntidade(MetodoInterceptado metodo, Colaborador colaborador) {
		GenericManager<Colaborador> manager = (GenericManager<Colaborador>) metodo.getComponente();
		return (Colaborador) manager.findEntidadeComAtributosSimplesById(colaborador.getId());
	}
}
