package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;

public class ConfiguracaoNivelCompetenciaFactory
{
	public static ConfiguracaoNivelCompetencia getEntity()
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setId(null);
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntity(Long id)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixaSalarial = getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setId(id);
		return configuracaoNivelCompetenciaFaixaSalarial;
	}
}