package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ColaboradorAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel insert(MetodoInterceptado metodo) throws Throwable 
	{
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(null, colaborador).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
	
	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		Colaborador colaboradorAnterior = (Colaborador) carregaEntidade(metodo, colaborador);
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(new Object[]{colaboradorAnterior}, colaborador).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
	
	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		colaborador = carregaEntidade(metodo, colaborador);
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(new Object[]{colaborador}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}

	public Auditavel desligaColaborador(MetodoInterceptado metodo) throws Throwable 
	{
		metodo.processa();

		Long colaboradorId = (Long) metodo.getParametros()[4];
		Colaborador colaborador = carregaEntidade(metodo, ColaboradorFactory.getEntity(colaboradorId));

		Long motivoDemissaoId = (Long) metodo.getParametros()[3];

		Map<String, Object> desligamento = new LinkedHashMap<String, Object>();
		desligamento.put("Colaborador ID", colaborador.getId());
		desligamento.put("Colaborador", colaborador.getNome());
		desligamento.put("Data de desligamento", colaborador.getDataDesligamento());
		desligamento.put("Motivo de demissao ID", motivoDemissaoId);
		desligamento.put("Observacao", colaborador.getObservacaoDemissao());
		
		String dados = new GeraDadosAuditados(null, desligamento).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}

	public Auditavel religaColaborador(MetodoInterceptado metodo) throws Throwable 
	{
		metodo.processa();

		Long colaboradorId = (Long) metodo.getParametros()[0];
		Colaborador colaborador = carregaEntidade(metodo, ColaboradorFactory.getEntity(colaboradorId));
		
		Map<String, Object> dadosColab = new LinkedHashMap<String, Object>();
		dadosColab.put("Colaborador ID", colaborador.getId());
		dadosColab.put("Colaborador", colaborador.getNome());
		
		String dados = new GeraDadosAuditados(null, dadosColab).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
	
	private Colaborador carregaEntidade(MetodoInterceptado metodo, Colaborador colaborador) {
		ColaboradorManager manager = (ColaboradorManager) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(colaborador.getId());
	}
}
