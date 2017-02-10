package com.fortes.rh.web.dwr;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.ConfiguracaoPerformanceManager;

@Component
@RemoteProxy(name="ConfiguracaoPerformanceDWR")
public class ConfiguracaoPerformanceDWR
{
	@Autowired private ConfiguracaoPerformanceManager configuracaoPerformanceManager;

	@RemoteMethod
	public void gravarConfiguracao(String usuarioId, String[] caixas, boolean[] caixasStatus)
	{
		Long usuarioIdLong = Long.parseLong(usuarioId.replace(".", ""));
		configuracaoPerformanceManager.gravaConfiguracao(usuarioIdLong, caixas, caixasStatus);
	}
}