package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Collection;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ConfiguracaoNivelCompetenciaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	
	public Auditavel saveCompetenciasFaixaSalarial(MetodoInterceptado metodo) throws Throwable 
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial =  (ConfiguracaoNivelCompetenciaFaixaSalarial) metodo.getParametros()[1];
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarialAnterior = carregaEntidade(metodo, configuracaoNivelCompetenciaFaixaSalarial.getId());
		metodo.processa();
		
		StringBuilder dados = new StringBuilder();
		
		dados.append("Dados Atuais:[ ");
		dados.append("\nData: " + configuracaoNivelCompetenciaFaixaSalarial.getData());
		dados.append("\nFaixa Salarial ID: " + configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId());
		dados.append("\nFaixa Salarial Descrição: " + configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getAtributoDescricao());
		dados.append("\nNivel Competência Histórico ID: " + configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId());

		dados.append("\n\nConfiguração das Competências");
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : carregaConfiguracaoNivelCompetencias(metodo, configuracaoNivelCompetenciaFaixaSalarial.getId())) {
			dados.append("\n\nCompetência ID: " + configuracaoNivelCompetencia.getCompetenciaId());
			dados.append("\nCompetência Descrição: " + configuracaoNivelCompetencia.getCompetenciaDescricao());
			dados.append("\nCompetência Peso: " + configuracaoNivelCompetencia.getPesoCompetencia());
			dados.append("\nCompetência Tipo: " + getTipoCompetencia(configuracaoNivelCompetencia.getTipoCompetencia()) );
			dados.append("\nNível: " + configuracaoNivelCompetencia.getNivelCompetencia().getDescricao());
		}
		dados.append("\n]");
		
		if(configuracaoNivelCompetenciaFaixaSalarialAnterior!=null){
			dados.append("\n\nDados Anteriores:[ ");
			dados.append("\nData: " + configuracaoNivelCompetenciaFaixaSalarialAnterior.getData());
			dados.append("\nFaixa Salarial ID: " + configuracaoNivelCompetenciaFaixaSalarialAnterior.getFaixaSalarial().getId());
			dados.append("\nFaixa Salarial Descrição: " + configuracaoNivelCompetenciaFaixaSalarialAnterior.getFaixaSalarial().getDescricao());
			dados.append("\nNivel Competência Histórico ID: " + configuracaoNivelCompetenciaFaixaSalarialAnterior.getNivelCompetenciaHistorico().getId());

			dados.append("\n\nConfiguração das Competências");
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetenciaFaixaSalarialAnterior.getConfiguracaoNivelCompetencias()) {
				dados.append("\n\nCompetência ID: " + configuracaoNivelCompetencia.getCompetenciaId());
				dados.append("\nCompetência Descrição: " + configuracaoNivelCompetencia.getCompetenciaDescricao());
				dados.append("\nCompetência Peso: " + configuracaoNivelCompetencia.getPesoCompetencia());
				dados.append("\nCompetência Tipo: " + getTipoCompetencia(configuracaoNivelCompetencia.getTipoCompetencia()) );
				dados.append("\nNível: " + configuracaoNivelCompetencia.getNivelCompetencia().getDescricao());
			}
			dados.append("\n]: ");
		}
		return new AuditavelImpl("Config. Nível Competência F. Salarial - Detalhado", metodo.getOperacao(), "Configuração Nível Competência Faixa Salarial", dados.toString());
	}
	
	private String getTipoCompetencia(Character tipoCompetencia) {
		if(tipoCompetencia  == TipoCompetencia.CONHECIMENTO.charValue())
			return "Conhecimento";
		else if(tipoCompetencia  == TipoCompetencia.HABILIDADE.charValue())
			return "Habilidade";
		else
			return "Atitude";
	}
	
	private ConfiguracaoNivelCompetenciaFaixaSalarial carregaEntidade(MetodoInterceptado metodo, Long configuracaoNivelCompetenciaFaixaSalarialId) {
		if(configuracaoNivelCompetenciaFaixaSalarialId != null){
			ConfiguracaoNivelCompetenciaManager manager = (ConfiguracaoNivelCompetenciaManager) metodo.getComponente();
			ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager = manager.getConfiguracaoNivelCompetenciaFaixaSalarialManager();
			ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findByProjection(configuracaoNivelCompetenciaFaixaSalarialId);
			configuracaoNivelCompetenciaFaixaSalarial.setConfiguracaoNivelCompetencias(manager.findByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarialId));
			return configuracaoNivelCompetenciaFaixaSalarial;
		}
		else 
			return null;
	}
	
	private Collection<ConfiguracaoNivelCompetencia> carregaConfiguracaoNivelCompetencias(MetodoInterceptado metodo, Long configuracaoNivelCompetenciaFaixaSalarialId) {
			ConfiguracaoNivelCompetenciaManager manager = (ConfiguracaoNivelCompetenciaManager) metodo.getComponente();
			return manager.findByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarialId);
	}
}