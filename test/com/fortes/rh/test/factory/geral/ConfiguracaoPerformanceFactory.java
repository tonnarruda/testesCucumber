package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.ConfiguracaoPerformance;

public class ConfiguracaoPerformanceFactory
{

	public static ConfiguracaoPerformance getEntity()
	{
		ConfiguracaoPerformance configuracaoPerformance = new ConfiguracaoPerformance();
		configuracaoPerformance.setId(null);
		return configuracaoPerformance;
	}

	public static ConfiguracaoPerformance getEntity(Long id)
	{
		ConfiguracaoPerformance configuracaoPerformance = getEntity();
		configuracaoPerformance.setId(id);

		return configuracaoPerformance;
	}

	public static Collection<ConfiguracaoPerformance> getCollection()
	{
		Collection<ConfiguracaoPerformance> configuracaoPerformances = new ArrayList<ConfiguracaoPerformance>();
		configuracaoPerformances.add(getEntity());

		return configuracaoPerformances;
	}
	
	public static Collection<ConfiguracaoPerformance> getCollection(Long id)
	{
		Collection<ConfiguracaoPerformance> configuracaoPerformances = new ArrayList<ConfiguracaoPerformance>();
		configuracaoPerformances.add(getEntity(id));
		
		return configuracaoPerformances;
	}

}
