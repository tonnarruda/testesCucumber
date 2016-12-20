package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class TabelaReajusteColaboradorAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		TabelaReajusteColaborador tabelaReajusteColaborador = (TabelaReajusteColaborador) metodo.getParametros()[0];
		TabelaReajusteColaborador tabelaReajusteColaboradorAnterior = (TabelaReajusteColaborador) carregaEntidade(metodo, tabelaReajusteColaborador);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{tabelaReajusteColaboradorAnterior}, tabelaReajusteColaborador).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), tabelaReajusteColaborador.getNome(), dados);
	}
	
	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		TabelaReajusteColaborador tabelaReajusteColaborador = (TabelaReajusteColaborador) metodo.getParametros()[0];
		TabelaReajusteColaborador tabelaReajusteColaboradorAnterior = (TabelaReajusteColaborador) carregaEntidade(metodo, tabelaReajusteColaborador);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{tabelaReajusteColaboradorAnterior}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), tabelaReajusteColaboradorAnterior.getNome(), dados);
	}
		
	public Auditavel aplicarPorColaborador(MetodoInterceptado metodo) throws Throwable {
		
		TabelaReajusteColaborador tabelaReajusteColaborador = (TabelaReajusteColaborador) metodo.getParametros()[0];
		tabelaReajusteColaborador = (TabelaReajusteColaborador) carregaEntidade(metodo, tabelaReajusteColaborador);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{tabelaReajusteColaborador}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), tabelaReajusteColaborador.getNome(), dados);
	}

	public Auditavel cancelar(MetodoInterceptado metodo) throws Throwable {
		
		Long tabelaReajusteColaboradorId = (Long) metodo.getParametros()[1];
		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();
		tabelaReajusteColaborador.setId(tabelaReajusteColaboradorId);
		tabelaReajusteColaborador = (TabelaReajusteColaborador) carregaEntidade(metodo, tabelaReajusteColaborador);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{tabelaReajusteColaborador}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), tabelaReajusteColaborador.getNome(), dados);
	}
	
	private TabelaReajusteColaborador carregaEntidade(MetodoInterceptado metodo, TabelaReajusteColaborador tabelaReajusteColaborador) {
		TabelaReajusteColaboradorManager manager = (TabelaReajusteColaboradorManager) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(tabelaReajusteColaborador.getId());
	}
}
