package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.util.DateUtil;


public class NivelCompetenciaDWR
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private NivelCompetenciaManager nivelCompetenciaManager;
	private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;

	public Collection<NivelCompetencia> findNiveisCompetencia(String data, Long faixaSalarialId, Long empresaId)
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarialId, DateUtil.criarDataDiaMesAno(data));
		
		if(configuracaoNivelCompetenciaFaixaSalarial == null)
			return new ArrayList<NivelCompetencia>();
		
		return nivelCompetenciaManager.findAllSelect(empresaId, configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId(), null);
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarialAndData(String data, Long faixaSalarialId)
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarialId, DateUtil.criarDataDiaMesAno(data));
		
		if(configuracaoNivelCompetenciaFaixaSalarial == null)
			return new ArrayList<ConfiguracaoNivelCompetencia>();
		
		return configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaSalarialId, DateUtil.criarDataDiaMesAno(data), configuracaoNivelCompetenciaFaixaSalarial.getId(), null, null);
	}
	
	public Collection<NivelCompetencia> findNiveisCompetenciaByAvDesempenho(Long avaliacaoDesempenhoId, Long avaliadoId)
	{
		return nivelCompetenciaManager.findNiveisCompetenciaByAvDesempenho(avaliacaoDesempenhoId,avaliadoId);
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

	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager) {
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}
}