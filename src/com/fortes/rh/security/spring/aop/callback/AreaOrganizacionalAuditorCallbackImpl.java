package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class AreaOrganizacionalAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel insert(MetodoInterceptado metodo) throws Throwable 
	{
		AreaOrganizacional area = (AreaOrganizacional) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(null, area).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), area.getDescricao(), dados);
	}
	
	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		AreaOrganizacional area = (AreaOrganizacional) metodo.getParametros()[0];
		AreaOrganizacional areaAnterior = (AreaOrganizacional) carregaEntidade(metodo, area);
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(new Object[]{areaAnterior}, area).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), area.getNome(), dados);
	}
	
	public Auditavel deleteLotacaoAC(MetodoInterceptado metodo) throws Throwable {
		
		AreaOrganizacional area = (AreaOrganizacional) metodo.getParametros()[0];
		area = carregaEntidade(metodo, area);
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(new Object[]{area}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), area.getNome(), dados);
	}
	
	public Auditavel removeComDependencias(MetodoInterceptado metodo) throws Throwable {
		
		AreaOrganizacional area = new AreaOrganizacional();
		area.setId((Long)metodo.getParametros()[0]);
		AreaOrganizacionalManager manager = (AreaOrganizacionalManager) metodo.getComponente();
		area = manager.findById(area.getId());
		String dados = new GeraDadosAuditados(new Object[]{area}, null).gera();
		metodo.processa();
		
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), area.getNome(), dados);
	}
	
	
	private AreaOrganizacional carregaEntidade(MetodoInterceptado metodo, AreaOrganizacional area) {
		AreaOrganizacionalManager manager = (AreaOrganizacionalManager) metodo.getComponente();
		return manager.findByIdProjection(area.getId());
	}
}
