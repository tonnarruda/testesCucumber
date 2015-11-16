package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class HistoricoColaboradorAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel insertHistorico(MetodoInterceptado metodo) throws Throwable 
	{
		HistoricoColaborador historico = (HistoricoColaborador) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(null, historico).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "", dados);
	}

	public Auditavel updateHistorico(MetodoInterceptado metodo) throws Throwable 
	{
		HistoricoColaborador historico = (HistoricoColaborador) metodo.getParametros()[0];
		HistoricoColaborador historicoAnterior = (HistoricoColaborador) carregaEntidade(metodo, historico);
		
		metodo.processa();
		
		HistoricoColaborador historicoAtual = (HistoricoColaborador) carregaEntidade(metodo, historico);
		
		String dados = new GeraDadosAuditados(new Object[]{historicoAnterior}, historicoAtual).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "", dados);
	}

	public Auditavel removeHistoricoAndReajuste(MetodoInterceptado metodo) throws Throwable 
	{
		Long historicoId = (Long) metodo.getParametros()[0];
		HistoricoColaborador hist = new HistoricoColaborador();
		hist.setId(historicoId);
		HistoricoColaborador historico = carregaEntidade(metodo, hist);
		
		String dados = new GeraDadosAuditados(new Object[]{historico}, null).gera();
		
		metodo.processa();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "", dados);
	}
	
	private HistoricoColaborador carregaEntidade(MetodoInterceptado metodo, HistoricoColaborador historico) {
		HistoricoColaboradorManager manager = (HistoricoColaboradorManager) metodo.getComponente();
		return manager.findByIdProjection(historico.getId());
	}
}
