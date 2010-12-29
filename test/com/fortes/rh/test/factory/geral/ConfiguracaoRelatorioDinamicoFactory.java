package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;

public class ConfiguracaoRelatorioDinamicoFactory
{
	public static ConfiguracaoRelatorioDinamico getEntity()
	{
		ConfiguracaoRelatorioDinamico configuracaoRelatorioDinamico = new ConfiguracaoRelatorioDinamico();
		configuracaoRelatorioDinamico.setId(null);
		return configuracaoRelatorioDinamico;
	}

	public static ConfiguracaoRelatorioDinamico getEntity(Long id)
	{
		ConfiguracaoRelatorioDinamico configuracaoRelatorioDinamico = getEntity();
		configuracaoRelatorioDinamico.setId(id);

		return configuracaoRelatorioDinamico;
	}

	public static Collection<ConfiguracaoRelatorioDinamico> getCollection()
	{
		Collection<ConfiguracaoRelatorioDinamico> configuracaoRelatorioDinamicos = new ArrayList<ConfiguracaoRelatorioDinamico>();
		configuracaoRelatorioDinamicos.add(getEntity());

		return configuracaoRelatorioDinamicos;
	}
	
	public static Collection<ConfiguracaoRelatorioDinamico> getCollection(Long id)
	{
		Collection<ConfiguracaoRelatorioDinamico> configuracaoRelatorioDinamicos = new ArrayList<ConfiguracaoRelatorioDinamico>();
		configuracaoRelatorioDinamicos.add(getEntity(id));
		
		return configuracaoRelatorioDinamicos;
	}
}
