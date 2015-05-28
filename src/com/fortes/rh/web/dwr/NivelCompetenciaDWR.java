package com.fortes.rh.web.dwr;

import java.util.Collection;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.util.DateUtil;


public class NivelCompetenciaDWR
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarialAndData(Long faixaSalarialId, String data)
	{
		return configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaSalarialId, DateUtil.criarDataDiaMesAno(data));
	}
	
	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) 
	{
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}
}