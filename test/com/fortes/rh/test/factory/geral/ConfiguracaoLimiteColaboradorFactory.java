package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;

public class ConfiguracaoLimiteColaboradorFactory
{
	public static ConfiguracaoLimiteColaborador getEntity()
	{
		ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = new ConfiguracaoLimiteColaborador();
		configuracaoLimiteColaborador.setId(null);
		configuracaoLimiteColaborador.setDescricao("numero do contrato");
		return configuracaoLimiteColaborador;
	}

	public static ConfiguracaoLimiteColaborador getEntity(Long id)
	{
		ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = getEntity();
		configuracaoLimiteColaborador.setId(id);

		return configuracaoLimiteColaborador;
	}

	public static Collection<ConfiguracaoLimiteColaborador> getCollection()
	{
		Collection<ConfiguracaoLimiteColaborador> configuracaoLimiteColaboradors = new ArrayList<ConfiguracaoLimiteColaborador>();
		configuracaoLimiteColaboradors.add(getEntity());

		return configuracaoLimiteColaboradors;
	}
	
	public static Collection<ConfiguracaoLimiteColaborador> getCollection(Long id)
	{
		Collection<ConfiguracaoLimiteColaborador> configuracaoLimiteColaboradors = new ArrayList<ConfiguracaoLimiteColaborador>();
		configuracaoLimiteColaboradors.add(getEntity(id));
		
		return configuracaoLimiteColaboradors;
	}
}
