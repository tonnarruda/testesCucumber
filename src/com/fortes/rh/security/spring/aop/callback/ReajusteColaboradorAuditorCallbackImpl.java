package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ReajusteColaboradorAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel insertSolicitacaoReajuste(MetodoInterceptado metodo) throws Throwable 
	{
		ReajusteColaborador reajuste = (ReajusteColaborador) metodo.getParametros()[0];

		metodo.processa();
		ReajusteColaborador reajusteComColaborador = carregaEntidade(metodo, reajuste);
		
		String dados = new DadosAuditados(null, reajusteComColaborador).gera();
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), reajusteComColaborador.getColaborador().getNome(), dados);
	}
	
	public Auditavel updateReajusteColaborador(MetodoInterceptado metodo) throws Throwable 
	{
		ReajusteColaborador reajuste = (ReajusteColaborador) metodo.getParametros()[0];
		
		metodo.processa();
		ReajusteColaborador reajusteAnterior = carregaEntidade(metodo, reajuste);
		
		String dados = new DadosAuditados(new ReajusteColaborador[]{reajusteAnterior}, reajuste).gera();
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), reajusteAnterior.getColaborador().getNome(), dados);
	}
	
	private ReajusteColaborador carregaEntidade(MetodoInterceptado metodo, ReajusteColaborador reajuste) {
		ReajusteColaboradorManager manager = (ReajusteColaboradorManager) metodo.getComponente();
		return manager.findByIdProjection(reajuste.getId());
	}
}
