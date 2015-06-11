package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.FaixaSalarial;

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

	public static ConfiguracaoNivelCompetencia getEntity(NivelCompetencia nivelCompetencia, Long competenciaId, ConfiguracaoNivelCompetenciaColaborador cncColaborador, ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial, Candidato candidato, FaixaSalarial faixaSalarial)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia.setCompetenciaId(competenciaId);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(cncColaborador);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarial(cncFaixaSalarial);
		configuracaoNivelCompetencia.setCandidato(candidato);
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntityFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial, NivelCompetencia nivelCompetencia, Long competenciaId, Character tipoCompetencia)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia.setCompetenciaId(competenciaId);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarial(cncFaixaSalarial);
		configuracaoNivelCompetencia.setTipoCompetencia(tipoCompetencia);
		
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntity(Character tipo)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setTipoCompetencia(tipo);
		return configuracaoNivelCompetencia;
	}

	public static ConfiguracaoNivelCompetencia getEntityColaborador(ConfiguracaoNivelCompetenciaColaborador cncColaborador,	NivelCompetencia nivelCompetencia, Long competenciaId, Character tipoCompetencia) 
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia.setCompetenciaId(competenciaId);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(cncColaborador);
		configuracaoNivelCompetencia.setTipoCompetencia(tipoCompetencia);
		
		return configuracaoNivelCompetencia;
	}

	public static ConfiguracaoNivelCompetencia getEntityCandidato(Candidato candidato, FaixaSalarial faixaSalarial, NivelCompetencia nivelCompetencia, Long competenciaId, Character tipoCompetencia) 
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia.setCompetenciaId(competenciaId);
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia.setTipoCompetencia(tipoCompetencia);
		configuracaoNivelCompetencia.setCandidato(candidato);
		
		return configuracaoNivelCompetencia;
	}
}