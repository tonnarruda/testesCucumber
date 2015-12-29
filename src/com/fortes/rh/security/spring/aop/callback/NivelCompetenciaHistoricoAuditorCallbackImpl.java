package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManager;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class NivelCompetenciaHistoricoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	
	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		NivelCompetenciaHistorico nivelCompetenciaHistorico = (NivelCompetenciaHistorico) metodo.getParametros()[0];
		
		metodo.processa();
		
		StringBuilder dados = new StringBuilder();
		dados.append("\nData: " + nivelCompetenciaHistorico.getDataFormatada());
		dados.append("\nEmpresa ID: " + nivelCompetenciaHistorico.getEmpresa().getId());
		dados.append("\nEmpresa Nome: " + nivelCompetenciaHistorico.getEmpresa().getNome());
		
		dados.append("\n\nConfiguração de níveis: ");
		
		for (ConfigHistoricoNivel configHistoricoNivel : nivelCompetenciaHistorico.getConfigHistoricoNiveis()) {
			dados.append("\n\nNivel Competência ID: " + configHistoricoNivel.getNivelCompetencia().getId());
			dados.append("\nNivel Competência Descrição: " + configHistoricoNivel.getNivelCompetencia().getDescricao());
			dados.append("\nPeso: " + configHistoricoNivel.getOrdem());
			dados.append("\nPercentual: " + configHistoricoNivel.getPercentualFormatado());
		}
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Histórico Nível Competência", dados.toString());
	}
	
	public Auditavel updateNivelConfiguracaoHistorico(MetodoInterceptado metodo) throws Throwable 
	{
		NivelCompetenciaHistorico nivelCompetenciaHistorico = (NivelCompetenciaHistorico) metodo.getParametros()[0];
		NivelCompetenciaHistorico nivelCompetenciaHistoricoAnterior = carregaEntidade(metodo, nivelCompetenciaHistorico.getId());
		
		metodo.processa();
		
		StringBuilder dados = new StringBuilder();
		
		dados.append("Dados Atualizados: ");
		dados.append("\nID: " + nivelCompetenciaHistorico.getId());
		dados.append("\nData: " + nivelCompetenciaHistorico.getDataFormatada());
		dados.append("\nEmpresa ID: " + nivelCompetenciaHistorico.getEmpresa().getId());
		dados.append("\nEmpresa Nome: " + nivelCompetenciaHistorico.getEmpresa().getNome());
		
		dados.append("\n\nConfiguração de níveis: ");
		
		for (ConfigHistoricoNivel configHistoricoNivel : nivelCompetenciaHistorico.getConfigHistoricoNiveis()) {
			dados.append("\n\nNivel Competência ID: " + configHistoricoNivel.getNivelCompetencia().getId());
			dados.append("\nNivel Competência Descrição: " + configHistoricoNivel.getNivelCompetencia().getDescricao());
			dados.append("\nPeso: " + configHistoricoNivel.getOrdem());
			dados.append("\nPercentual: " + configHistoricoNivel.getPercentualFormatado());
		}
		
		dados.append("\n\n\nDados Anteriores: ");
		dados.append("\nID: " + nivelCompetenciaHistoricoAnterior.getId());
		dados.append("\nData: " + nivelCompetenciaHistoricoAnterior.getDataFormatada());
		dados.append("\nEmpresa ID: " + nivelCompetenciaHistoricoAnterior.getEmpresa().getId());
		dados.append("\nEmpresa Nome: " + nivelCompetenciaHistoricoAnterior.getEmpresa().getNome());
		
		dados.append("\n\nConfiguração de níveis: ");
		
		for (ConfigHistoricoNivel configHistoricoNivel : nivelCompetenciaHistoricoAnterior.getConfigHistoricoNiveis()) {
			dados.append("\n\nNivel Competência ID: " + configHistoricoNivel.getNivelCompetencia().getId());
			dados.append("\nNivel Competência Descrição: " + configHistoricoNivel.getNivelCompetencia().getDescricao());
			dados.append("\nPeso: " + configHistoricoNivel.getOrdem());
			dados.append("\nPercentual: " + configHistoricoNivel.getPercentualFormatado());
		}
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Histórico Nível Competência", dados.toString());
	}
	
	public Auditavel removeNivelConfiguracaoHistorico(MetodoInterceptado metodo) throws Throwable {
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = carregaEntidade(metodo, (Long) metodo.getParametros()[0]);
		
		StringBuilder dados = new StringBuilder();
		dados.append("\nID: " + nivelCompetenciaHistorico.getId());
		dados.append("\nData: " + nivelCompetenciaHistorico.getDataFormatada());
		dados.append("\nEmpresa ID: " + nivelCompetenciaHistorico.getEmpresa().getId());
		dados.append("\nEmpresa Nome: " + nivelCompetenciaHistorico.getEmpresa().getNome());
		
		dados.append("\n\nConfiguração de níveis: ");
		
		for (ConfigHistoricoNivel configHistoricoNivel : nivelCompetenciaHistorico.getConfigHistoricoNiveis()) {
			dados.append("\n\nNivel Competência ID: " + configHistoricoNivel.getNivelCompetencia().getId());
			dados.append("\nNivel Competência Descrição: " + configHistoricoNivel.getNivelCompetencia().getDescricao());
			dados.append("\nPeso: " + configHistoricoNivel.getOrdem());
			dados.append("\nPercentual: " + configHistoricoNivel.getPercentualFormatado());
		}

		metodo.processa();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Histórico Nível Competência", dados.toString());
	}
	
	private NivelCompetenciaHistorico carregaEntidade(MetodoInterceptado metodo, Long nivelCompetenciaHistoricoId) {
		NivelCompetenciaHistoricoManager manager = (NivelCompetenciaHistoricoManager) metodo.getComponente();
		return manager.findById(nivelCompetenciaHistoricoId);
	}
	
	
	
	
}