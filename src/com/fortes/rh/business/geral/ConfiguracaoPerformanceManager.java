package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ConfiguracaoPerformance;

public interface ConfiguracaoPerformanceManager extends GenericManager<ConfiguracaoPerformance>
{

	Collection<ConfiguracaoPerformance> gravaConfiguracao(Long usuarioId, String[] caixas, boolean[] caixasStatus);

	Collection<ConfiguracaoPerformance> findByUsuario(Long id);
}
