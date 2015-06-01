package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;

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
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setId(id);
		return configuracaoNivelCompetencia;
	}

	public static ConfiguracaoNivelCompetencia getEntity(NivelCompetencia nivelCompetencia, Long competenciaId, ConfiguracaoNivelCompetenciaColaborador cncColaborador, ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia.setCompetenciaId(competenciaId);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(cncColaborador);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarial(cncFaixaSalarial);
		return configuracaoNivelCompetencia;
	}
}