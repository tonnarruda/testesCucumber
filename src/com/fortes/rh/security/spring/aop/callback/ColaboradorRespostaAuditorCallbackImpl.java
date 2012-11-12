package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ColaboradorRespostaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		ColaboradorQuestionario colaboradorQuestionario = (ColaboradorQuestionario) metodo.getParametros()[1];
		Long usuarioId = (Long) metodo.getParametros()[2];
		Long candidatoId = (Long) metodo.getParametros()[3];
		
		StringBuilder dados = new StringBuilder();
		dados.append("\nMensagem: ");

		if (candidatoId != null)
		{
			dados.append("\nAs respostas foram salvas pelo candidato ID " + candidatoId + " pelo modulo externo");
		}
		else 
		{
			Usuario usuario = carregaUsuario(metodo, usuarioId);
			dados.append("\nAs respostas foram salvas pelo usuário ");
			dados.append(usuario.getNome());
		}
		
		dados.append(".\n");
		dados.append(new GeraDadosAuditados(new Object[]{}, colaboradorQuestionario).gera());
		 
		metodo.processa();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Resposta", dados.toString());
	}
	
	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		ColaboradorQuestionario colaboradorQuestionario = (ColaboradorQuestionario) metodo.getParametros()[1];
		Usuario usuario = carregaUsuario(metodo, (Long) metodo.getParametros()[2]);
		
		StringBuilder dados = new StringBuilder();
		dados.append("\nMensagem: ");
		dados.append("\nAs respostas foram salvas pelo usuário ");
		dados.append(usuario.getNome());
		dados.append(".\n");
		dados.append(new GeraDadosAuditados(new Object[]{}, colaboradorQuestionario).gera());
		 
		metodo.processa();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Resposta", dados.toString());
	}
	
	private Usuario carregaUsuario(MetodoInterceptado metodo, Long usuarioId) {
		ColaboradorRespostaManager manager = (ColaboradorRespostaManager) metodo.getComponente();
		return manager.findUsuarioParaAuditoria(usuarioId);
	}
	
}
