package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ConfiguracaoPerformanceDao;
import com.fortes.rh.model.geral.ConfiguracaoPerformance;

public class ConfiguracaoPerformanceManagerImpl extends GenericManagerImpl<ConfiguracaoPerformance, ConfiguracaoPerformanceDao> implements ConfiguracaoPerformanceManager
{

	public Collection<ConfiguracaoPerformance> gravaConfiguracao(Long usuarioId, String[] caixas, boolean[] caixasStatus) 
	{
		Collection<ConfiguracaoPerformance> configs = new ArrayList<ConfiguracaoPerformance>();
		getDao().removeByUsuario(usuarioId);
		
		int cont = 0;
		for (String caixa : caixas) 
		{
			ConfiguracaoPerformance configuracaoPerformance = new ConfiguracaoPerformance(usuarioId, caixa, cont, caixasStatus[cont]);
			getDao().save(configuracaoPerformance);
			
			configs.add(configuracaoPerformance);
			cont++;
		}
		
		return configs;
	}

	@TesteAutomatico
	public Collection<ConfiguracaoPerformance> findByUsuario(Long id) {
		return getDao().findByUsuario(id);
	}
}
