package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
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
	
	public Auditavel update(MetodoInterceptado metodo) throws Throwable 
	{
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
	
	public Auditavel salvaQuestionarioRespondido(MetodoInterceptado metodo) throws Throwable 
	{
		ColaboradorRespostaManager colaboradorRespostaManager = (ColaboradorRespostaManager) metodo.getComponente();
		QuestionarioManager questionarioManager = colaboradorRespostaManager.getQuestionarioManager();
		Questionario questionario = questionarioManager.findEntidadeComAtributosSimplesById(((Questionario) metodo.getParametros()[1]).getId());

		StringBuilder dados = new StringBuilder();
		dados.append("\nMensagem: ");
		
		if (questionario.verificaTipo(TipoQuestionario.FICHAMEDICA) && ((Character) metodo.getParametros()[4]) == 'A')
		{
			CandidatoManager candidatoManager = colaboradorRespostaManager.getCandidatoManager();
			Candidato candidato = candidatoManager.findEntidadeComAtributosSimplesById((Long) metodo.getParametros()[2]);
		
			dados.append("\nAs respostas do(a) candidato(a) ");
			dados.append(candidato.getNome());
		}
		else
		{
			ColaboradorManager colaboradorManager = colaboradorRespostaManager.getColaboradorManager();
			Colaborador colaborador = colaboradorManager.findEntidadeComAtributosSimplesById((Long) metodo.getParametros()[2]);

			dados.append("\nAs respostas do(a) colaborador(a) ");
			dados.append(colaborador.getNome());
		}		
		
		dados.append(" para a ");
		dados.append(TipoQuestionario.getDescricao(questionario.getTipo()));
		dados.append(" ");
		dados.append(questionario.getTitulo());
		dados.append(" foram gravadas.\n");
		
		metodo.processa();
		
		return new AuditavelImpl(TipoQuestionario.getDescricaoMaisc(questionario.getTipo()), metodo.getOperacao(), "Respostas para a " + TipoQuestionario.getDescricao(questionario.getTipo()) + " " + questionario.getTitulo(), dados.toString());
	}
	
	private Usuario carregaUsuario(MetodoInterceptado metodo, Long usuarioId) 
	{
		ColaboradorRespostaManager manager = (ColaboradorRespostaManager) metodo.getComponente();
		return manager.findUsuarioParaAuditoria(usuarioId);
	}
}
