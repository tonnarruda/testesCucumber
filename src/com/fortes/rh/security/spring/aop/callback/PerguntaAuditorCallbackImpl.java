package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class PerguntaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel salvarPergunta(MetodoInterceptado metodo) throws Throwable 
	{
		Pergunta pergunta = (Pergunta) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(null, pergunta).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), pergunta.getTexto(), dados);
	}
	
	public Auditavel atualizarPergunta(MetodoInterceptado metodo) throws Throwable {
		
		Pergunta pergunta = (Pergunta) metodo.getParametros()[0];
		Pergunta perguntaAnterior = (Pergunta) carregaEntidade(metodo, pergunta);
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(new Object[]{perguntaAnterior}, pergunta).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), pergunta.getTexto(), dados);
	}
	
	public Auditavel removerPergunta(MetodoInterceptado metodo) throws Throwable {
		
		Pergunta pergunta = (Pergunta) metodo.getParametros()[0];
		Pergunta perguntaAnterior = (Pergunta) carregaEntidade(metodo, pergunta);
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(new Object[]{perguntaAnterior}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), perguntaAnterior.getTexto(), dados);
	}
	
	private Pergunta carregaEntidade(MetodoInterceptado metodo, Pergunta pergunta) {
		PerguntaManager manager = (PerguntaManager) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(pergunta.getId());
	}
}
