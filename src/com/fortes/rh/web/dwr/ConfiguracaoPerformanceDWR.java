package com.fortes.rh.web.dwr;

import com.fortes.rh.business.geral.ConfiguracaoPerformanceManager;

public class ConfiguracaoPerformanceDWR
{
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
