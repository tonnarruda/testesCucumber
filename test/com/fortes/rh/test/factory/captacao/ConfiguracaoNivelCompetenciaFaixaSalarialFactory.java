package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaFaixaSalarialFactory
{
	public static ConfiguracaoNivelCompetenciaFaixaSalarial getEntity()
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarial();
		configuracaoNivelCompetenciaFaixaSalarial.setId(null);
		configuracaoNivelCompetenciaFaixaSalarial.setData(new Date());
		return configuracaoNivelCompetenciaFaixaSalarial;
	}
	
	public static ConfiguracaoNivelCompetenciaFaixaSalarial getEntity(Long id)
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setId(id);
		return configuracaoNivelCompetenciaFaixaSalarial;
	}
	
	public static ConfiguracaoNivelCompetenciaFaixaSalarial getEntity(FaixaSalarial faixaSalarial, String data)
	{
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarial.setData(DateUtil.criarDataDiaMesAno(data));
		
		return configuracaoNivelCompetenciaFaixaSalarial;
	}

	public static ConfiguracaoNivelCompetenciaFaixaSalarial getEntity(FaixaSalarial faixaSalarial, Date data) {
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarial.setData(data);
		return configuracaoNivelCompetenciaFaixaSalarial;
	}
	
	public static ConfiguracaoNivelCompetenciaFaixaSalarial getEntity(Long id, FaixaSalarial faixaSalarial, Date data) {
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = getEntity(faixaSalarial, data);
		configuracaoNivelCompetenciaFaixaSalarial.setId(id);
		return configuracaoNivelCompetenciaFaixaSalarial;
	}
	
	public static ConfiguracaoNivelCompetenciaFaixaSalarial getEntity(FaixaSalarial faixaSalarial, Date data, NivelCompetenciaHistorico nivelCompetenciaHistorico) {
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = getEntity(faixaSalarial, data);
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		return configuracaoNivelCompetenciaFaixaSalarial;
	}
	
	public static ConfiguracaoNivelCompetenciaFaixaSalarial getEntity(Long id, FaixaSalarial faixaSalarial, Date data, NivelCompetenciaHistorico nivelCompetenciaHistorico) {
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = getEntity(faixaSalarial, data, nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarial.setId(id);
		return configuracaoNivelCompetenciaFaixaSalarial;
	}
}