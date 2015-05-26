package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;

public class ConfiguracaoNivelCompetenciaFaixaSalarialFactory
{
	public static ConfiguracaoNivelCompetenciaFaixaSalarial getEntity()
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarialF = new ConfiguracaoNivelCompetenciaFaixaSalarial();
		configuracaoNivelCompetenciaFaixaSalarialF.setId(null);
		configuracaoNivelCompetenciaFaixaSalarialF.setData(new Date());
		return configuracaoNivelCompetenciaFaixaSalarialF;
	}

}
