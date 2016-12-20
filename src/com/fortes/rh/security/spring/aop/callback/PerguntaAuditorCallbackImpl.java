package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.rh.util.StringUtil;
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
		
		String dados = new DadosAuditados(null, pergunta).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), StringUtil.subStr(pergunta.getTexto(), 100), dados);
	}
	
	public Auditavel atualizarPergunta(MetodoInterceptado metodo) throws Throwable {
		
		Pergunta pergunta = (Pergunta) metodo.getParametros()[0];
		Pergunta perguntaAnterior = (Pergunta) carregaEntidade(metodo, pergunta);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{perguntaAnterior}, pergunta).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), StringUtil.subStr(pergunta.getTexto(), 100), dados);
	}
	
	public Auditavel removerPergunta(MetodoInterceptado metodo) throws Throwable {
		
		Pergunta pergunta = (Pergunta) metodo.getParametros()[0];
		Pergunta perguntaAnterior = (Pergunta) carregaEntidade(metodo, pergunta);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{perguntaAnterior}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), StringUtil.subStr(perguntaAnterior.getTexto(), 255), dados);
	}
	
	private Pergunta carregaEntidade(MetodoInterceptado metodo, Pergunta pergunta) {
		PerguntaManager manager = (PerguntaManager) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(pergunta.getId());
	}
}
