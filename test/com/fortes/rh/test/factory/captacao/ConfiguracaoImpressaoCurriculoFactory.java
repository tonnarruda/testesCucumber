package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;

public class ConfiguracaoImpressaoCurriculoFactory
{
	public static ConfiguracaoImpressaoCurriculo getEntity()
	{
		ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo = new ConfiguracaoImpressaoCurriculo();
		configuracaoImpressaoCurriculo.setId(null);
		return configuracaoImpressaoCurriculo;
	}

	public static ConfiguracaoImpressaoCurriculo getEntity(Long id)
	{
		ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo = getEntity();
		configuracaoImpressaoCurriculo.setId(id);

		return configuracaoImpressaoCurriculo;
	}

	public static Collection<ConfiguracaoImpressaoCurriculo> getCollection()
	{
		Collection<ConfiguracaoImpressaoCurriculo> configuracaoImpressaoCurriculos = new ArrayList<ConfiguracaoImpressaoCurriculo>();
		configuracaoImpressaoCurriculos.add(getEntity());

		return configuracaoImpressaoCurriculos;
	}
	
	public static Collection<ConfiguracaoImpressaoCurriculo> getCollection(Long id)
	{
		Collection<ConfiguracaoImpressaoCurriculo> configuracaoImpressaoCurriculos = new ArrayList<ConfiguracaoImpressaoCurriculo>();
		configuracaoImpressaoCurriculos.add(getEntity(id));
		
		return configuracaoImpressaoCurriculos;
	}
}
