package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.util.DateUtil;

@Component
@RemoteProxy(name="NivelCompetenciaDWR")
public class NivelCompetenciaDWR
{
	@Autowired private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	@Autowired private NivelCompetenciaManager nivelCompetenciaManager;
	@Autowired private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;
	@Autowired private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;

	@RemoteMethod
	public Collection<NivelCompetencia> findNiveisCompetencia(String data, Long faixaSalarialId, Long empresaId)
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarialId, DateUtil.criarDataDiaMesAno(data));
		
		if(configuracaoNivelCompetenciaFaixaSalarial == null)
			return new ArrayList<NivelCompetencia>();
		
		return nivelCompetenciaManager.findAllSelect(empresaId, configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId(), null);
	}
	
	@RemoteMethod
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarialAndData(String data, Long faixaSalarialId)
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarialId, DateUtil.criarDataDiaMesAno(data));
		
		if(configuracaoNivelCompetenciaFaixaSalarial == null)
			return new ArrayList<ConfiguracaoNivelCompetencia>();
		
		return configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaSalarialId, DateUtil.criarDataDiaMesAno(data), configuracaoNivelCompetenciaFaixaSalarial.getId(), null, null);
	}
	
	@RemoteMethod
	public Collection<NivelCompetencia> findNiveisCompetenciaByAvDesempenho(Long avaliacaoDesempenhoId)
	{
		return nivelCompetenciaManager.findNiveisCompetenciaByAvDesempenho(avaliacaoDesempenhoId);
	}
	
	@RemoteMethod
	public boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual)
	{
		return nivelCompetenciaManager.existePercentual(nivelCompetenciaId, empresaId, percentual);
	}
	
	@RemoteMethod
	public boolean existeNivelCompetenciaSemPercentual(Long empresaId)
	{
		return nivelCompetenciaManager.existeNivelCompetenciaSemPercentual(empresaId);
	}
	
	@RemoteMethod
	public boolean existeCriterioAvaliacaoCompetencia(Long empresaId)
	{
		return criterioAvaliacaoCompetenciaManager.existeCriterioAvaliacaoCompetencia(empresaId);
	}	
}