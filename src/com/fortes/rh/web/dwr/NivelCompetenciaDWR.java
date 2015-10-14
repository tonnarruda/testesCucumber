package com.fortes.rh.web.dwr;

import java.util.Collection;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.util.DateUtil;


public class NivelCompetenciaDWR
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private NivelCompetenciaManager nivelCompetenciaManager;
	private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarialAndData(Long faixaSalarialId, String data)
	{
		return configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaSalarialId, DateUtil.criarDataDiaMesAno(data));
	}
	
	public boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual)
	{
		return nivelCompetenciaManager.existePercentual(nivelCompetenciaId, empresaId, percentual);
	}
	
	public boolean existeNivelCompetenciaSemPercentual(Long empresaId)
	{
		return nivelCompetenciaManager.existeNivelCompetenciaSemPercentual(empresaId);
	}
	
	public boolean existeCriterioAvaliacaoCompetencia(Long empresaId)
	{
		return criterioAvaliacaoCompetenciaManager.existeCriterioAvaliacaoCompetencia(empresaId);
	}
	
	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) 
	{
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager) 
	{
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}

	public void setCriterioAvaliacaoCompetenciaManager(CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager) {
		this.criterioAvaliacaoCompetenciaManager = criterioAvaliacaoCompetenciaManager;
	}
}