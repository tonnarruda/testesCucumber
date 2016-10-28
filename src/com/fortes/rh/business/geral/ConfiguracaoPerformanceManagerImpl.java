package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ConfiguracaoPerformanceDao;
import com.fortes.rh.model.geral.ConfiguracaoPerformance;

@Component
public class ConfiguracaoPerformanceManagerImpl extends GenericManagerImpl<ConfiguracaoPerformance, ConfiguracaoPerformanceDao> implements ConfiguracaoPerformanceManager
{
	@Autowired
	ConfiguracaoPerformanceManagerImpl(ConfiguracaoPerformanceDao dao) {
		setDao(dao);
	}

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
