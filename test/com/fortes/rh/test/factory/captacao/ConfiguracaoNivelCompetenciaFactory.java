package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

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

	public static ConfiguracaoNivelCompetencia getEntity(NivelCompetencia nivelCompetencia, Long competenciaId, ConfiguracaoNivelCompetenciaColaborador cncColaborador, ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial, FaixaSalarial faixaSalarial)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(cncColaborador);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarial(cncFaixaSalarial);
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		setCompetenciaNivelTipo(competenciaId, null, nivelCompetencia, configuracaoNivelCompetencia);
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntityFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial, NivelCompetencia nivelCompetencia, Long competenciaId, Character tipoCompetencia)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarial(cncFaixaSalarial);
		setCompetenciaNivelTipo(competenciaId, tipoCompetencia, nivelCompetencia, configuracaoNivelCompetencia);
		
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntityFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial, NivelCompetencia nivelCompetencia, String competenciaDescricao, Long competenciaId, Character tipoCompetencia)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntityFaixaSalarial(cncFaixaSalarial, nivelCompetencia, competenciaId, tipoCompetencia);
		configuracaoNivelCompetencia.setCompetenciaDescricao(competenciaDescricao);
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntityFaixaSalarial(Long id, ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial, NivelCompetencia nivelCompetencia, String competenciaDescricao, Long competenciaId, Character tipoCompetencia)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntityFaixaSalarial(cncFaixaSalarial, nivelCompetencia, competenciaDescricao, competenciaId, tipoCompetencia);
		configuracaoNivelCompetencia.setId(id);
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
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(cncColaborador);
		setCompetenciaNivelTipo(competenciaId, tipoCompetencia,nivelCompetencia, configuracaoNivelCompetencia);
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntityColaborador(ConfiguracaoNivelCompetenciaColaborador cncColaborador,	NivelCompetencia nivelCompetencia,  Long competenciaId, String competenciaDescricao, Character tipoCompetencia) 
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaColaborador(cncColaborador);
		configuracaoNivelCompetencia.setCompetenciaDescricao(competenciaDescricao);
		setCompetenciaNivelTipo(competenciaId, tipoCompetencia,nivelCompetencia, configuracaoNivelCompetencia);
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntityColaborador(Long id, ConfiguracaoNivelCompetenciaColaborador cncColaborador,	NivelCompetencia nivelCompetencia,  Long competenciaId, String competenciaDescricao, Character tipoCompetencia) 
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntityColaborador(cncColaborador, nivelCompetencia, competenciaId, competenciaDescricao, tipoCompetencia);
		configuracaoNivelCompetencia.setId(id);
		return configuracaoNivelCompetencia;
	}
	
	public static ConfiguracaoNivelCompetencia getEntity(FaixaSalarial faixaSalarial, NivelCompetencia nivelCompetencia, Long competenciaId, Character tipoCompetencia) 
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		setCompetenciaNivelTipo(competenciaId, tipoCompetencia,nivelCompetencia, configuracaoNivelCompetencia);
		return configuracaoNivelCompetencia;
	}


	public static ConfiguracaoNivelCompetencia getEntityCandidato(ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato, FaixaSalarial faixaSalarial, NivelCompetencia nivelCompetencia, Long competenciaId, Character tipoCompetencia) 
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaCandidato(configuracaoNivelCompetenciaCandidato);
		setCompetenciaNivelTipo(competenciaId, tipoCompetencia,nivelCompetencia, configuracaoNivelCompetencia);

		return configuracaoNivelCompetencia;
	}

	private static void setCompetenciaNivelTipo(Long competenciaId,Character tipoCompetencia, NivelCompetencia nivelCompetencia, ConfiguracaoNivelCompetencia configuracaoNivelCompetencia) {
		configuracaoNivelCompetencia.setCompetenciaId(competenciaId);
		configuracaoNivelCompetencia.setTipoCompetencia(tipoCompetencia);
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
	}
	
	public static ConfiguracaoNivelCompetencia getEntity(Competencia competencia, Colaborador colaborador, Cargo cargo, Integer ordem)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = getEntity();
		configuracaoNivelCompetencia.setCompetencia(competencia);
		configuracaoNivelCompetencia.setCargo(cargo);
		configuracaoNivelCompetencia.setColaborador(colaborador);
		configuracaoNivelCompetencia.setNivelCompetenciaOrdemProjection(ordem);
		return configuracaoNivelCompetencia;
	}
}