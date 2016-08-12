package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ColaboradorRepostaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel deleteRespostaAvaliacaoDesempenho(MetodoInterceptado metodo) throws Throwable 
	{
		metodo.processa();

		Long colaboradorQuestionarioId = (Long) metodo.getParametros()[0];
		ColaboradorQuestionario colabQuestionario = new ColaboradorQuestionario();
		colabQuestionario.setId(colaboradorQuestionarioId);
		ColaboradorQuestionario colaboradorquestionario = carregaEntidade(metodo, colabQuestionario);
		
		Map<String, Object> dadosColab = new LinkedHashMap<String, Object>();
		dadosColab.put("Avaliado", colaboradorquestionario.getColaborador().getNome());
		dadosColab.put("Avaliação Desempenho", colaboradorquestionario.getAvaliacaoDesempenho().getTitulo());
		
		String dados = new GeraDadosAuditados(null, dadosColab).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaboradorquestionario.getPessoaNome(), dados);
	}
	
	private ColaboradorQuestionario carregaEntidade(MetodoInterceptado metodo, ColaboradorQuestionario colaboradorQuestionario) {
		ColaboradorQuestionarioManager manager = (ColaboradorQuestionarioManager) metodo.getComponente();
		return manager.findByIdProjection(colaboradorQuestionario.getId());
	}
}
