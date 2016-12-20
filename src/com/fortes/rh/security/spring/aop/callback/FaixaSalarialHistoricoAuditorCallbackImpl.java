package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class FaixaSalarialHistoricoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		FaixaSalarialHistorico faixaSalarialHistorico = (FaixaSalarialHistorico) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new DadosAuditados(null, faixaSalarialHistorico).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(),  DateUtil.formataDiaMesAno(faixaSalarialHistorico.getData()) + " tipo: " + faixaSalarialHistorico.getTipoSalarioDescricao() , dados);
	}

	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		FaixaSalarialHistorico faixaSalarialHistorico = (FaixaSalarialHistorico) metodo.getParametros()[0];
		FaixaSalarialHistorico faixaSalarialHistoricoAnterior = (FaixaSalarialHistorico) carregaEntidade(metodo, faixaSalarialHistorico);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{faixaSalarialHistoricoAnterior}, faixaSalarialHistorico).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), DateUtil.formataDiaMesAno(faixaSalarialHistorico.getData()) + " tipo: " + faixaSalarialHistorico.getTipoSalarioDescricao(), dados);
	}
	
	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		Long id = (Long) metodo.getParametros()[0];
		FaixaSalarialHistorico faixaSalarialHistorico = new FaixaSalarialHistorico();
		faixaSalarialHistorico.setId(id);
		
		FaixaSalarialHistorico faixaSalarialHistoricoAnterior = (FaixaSalarialHistorico) carregaEntidade(metodo, faixaSalarialHistorico);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{faixaSalarialHistoricoAnterior}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), DateUtil.formataDiaMesAno(faixaSalarialHistoricoAnterior.getData()) + " tipo: " + faixaSalarialHistoricoAnterior.getTipoSalarioDescricao(), dados);
	}
	
	private FaixaSalarialHistorico carregaEntidade(MetodoInterceptado metodo, FaixaSalarialHistorico faixaSalarialHistorico) {
		FaixaSalarialHistoricoManager manager = (FaixaSalarialHistoricoManager) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(faixaSalarialHistorico.getId());
	}
}
