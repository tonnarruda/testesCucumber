package com.fortes.rh.web.dwr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.ConfiguracaoPerformanceManager;

@Component
public class ConfiguracaoPerformanceDWR
{
	@Autowired
	private ConfiguracaoPerformanceManager configuracaoPerformanceManager;

	public void gravarConfiguracao(String usuarioId, String[] caixas, boolean[] caixasStatus)
	{
		Long usuarioIdLong = Long.parseLong(usuarioId.replace(".", ""));
		configuracaoPerformanceManager.gravaConfiguracao(usuarioIdLong, caixas, caixasStatus);
	}

	public void setConfiguracaoPerformanceManager(ConfiguracaoPerformanceManager configuracaoPerformanceManager) {
		this.configuracaoPerformanceManager = configuracaoPerformanceManager;
	}

}
