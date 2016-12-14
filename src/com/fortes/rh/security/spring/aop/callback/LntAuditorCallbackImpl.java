package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.desenvolvimento.LntManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;


public class LntAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		Lnt lnt = (Lnt) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(null, lnt).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), lnt.getDescricao(), dados);
	}
	
	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		Lnt lnt = (Lnt) metodo.getParametros()[0];
		Lnt lntAnterior = (Lnt) carregaEntidadeCompleta(metodo, lnt);
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(new Object[]{lntAnterior}, lnt).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), lnt.getDescricao(), dados);
	}
	
	public Auditavel removeComDependencias(MetodoInterceptado metodo) throws Throwable {
		
		Long lntId = (Long) metodo.getParametros()[0];
		Lnt lnt  = new Lnt();
		lnt.setId(lntId); 
		lnt = carregaEntidadeCompleta(metodo, lnt);
		
		String dados = new GeraDadosAuditados(new Object[]{lnt}, null).gera();

		metodo.processa();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), lnt.getDescricao(), dados);
	}

	public Auditavel finalizar(MetodoInterceptado metodo) throws Throwable {
		metodo.processa();
		Long lntId = (Long) metodo.getParametros()[0];
		Lnt lnt  = carregaEntidadeComAtributosSimples(metodo, lntId);
		
		StringBuilder dados = new StringBuilder();
		dados.append("\nId: " + lnt.getId());
		dados.append("\nDescrição: " + lnt.getDescricao());
		dados.append("\nData início: " + DateUtil.formataDiaMesAno(lnt.getDataInicio()));
		dados.append("\nData fim: " + DateUtil.formataDiaMesAno(lnt.getDataInicio()));
		dados.append("\nData em que foi finalizada: " + DateUtil.formataDiaMesAno(lnt.getDataFinalizada()));
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Finalizar LNT - " + lnt.getDescricao(), dados.toString());
	}
	
	public Auditavel reabrir(MetodoInterceptado metodo) throws Throwable {
		Long lntId = (Long) metodo.getParametros()[0];
		Lnt lntAnterior  = carregaEntidadeComAtributosSimples(metodo, lntId);

		metodo.processa();
		
		StringBuilder dados = new StringBuilder();
		dados.append("[DADOS ANTERIORES]");
		dados.append("\n[");
		dados.append("\n  Descrição: " + lntAnterior.getDescricao());
		dados.append("\n  Data início: " +  DateUtil.formataDiaMesAno(lntAnterior.getDataInicio()));
		dados.append("\n  Data fim: " + DateUtil.formataDiaMesAno(lntAnterior.getDataInicio()));
		dados.append("\n  Data em que foi finalizada: " + DateUtil.formataDiaMesAno(lntAnterior.getDataFinalizada()));
		dados.append("\n]");
		
		dados.append("\n\n\n[DADOS ATUALIZADOS]");
		dados.append("\n[");
		dados.append("\n  Descrição: " + lntAnterior.getDescricao());
		dados.append("\n  Data início: " + DateUtil.formataDiaMesAno(lntAnterior.getDataInicio()));
		dados.append("\n  Data fim: " + DateUtil.formataDiaMesAno(lntAnterior.getDataInicio()));
		dados.append("\n\n  Esta LNT foi reaberta." );
		dados.append("\n]");
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Reabrir LNT - " + lntAnterior.getDescricao(), dados.toString());
	}
	
	private Lnt carregaEntidadeCompleta(MetodoInterceptado metodo, Lnt lnt) {
		LntManager lntManager = (LntManager) metodo.getComponente();
		EmpresaManager empresaManager = lntManager.getEmpresaManager();
		AreaOrganizacionalManager areaOrganizacionalManager = lntManager.getAreaOrganizacionalManager();
		
		lnt = lntManager.findEntidadeComAtributosSimplesById(lnt.getId());
		lnt.setAreasOrganizacionais(areaOrganizacionalManager.findByLntIdComEmpresa(lnt.getId(), new Long[]{}));
		lnt.setEmpresas(empresaManager.findByLntId(lnt.getId()));
		
		return lnt;
	}
	
	private Lnt carregaEntidadeComAtributosSimples(MetodoInterceptado metodo, Long lntId){
		LntManager lntManager = (LntManager) metodo.getComponente();
		Lnt lnt = lntManager.findEntidadeComAtributosSimplesById(lntId);
		return lnt;
	}
}
