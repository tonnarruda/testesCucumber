package com.fortes.rh.test.factory.avaliacao;

import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoFactory
{
	public static ConfiguracaoCompetenciaAvaliacaoDesempenho getEntity()
	{
		ConfiguracaoCompetenciaAvaliacaoDesempenho ConfiguracaoCompetenciaAvaliacaoDesempenho = new ConfiguracaoCompetenciaAvaliacaoDesempenho();
		ConfiguracaoCompetenciaAvaliacaoDesempenho.setId(null);
		
		return ConfiguracaoCompetenciaAvaliacaoDesempenho;
	}

	public static ConfiguracaoCompetenciaAvaliacaoDesempenho getEntity(Long id)
	{
		ConfiguracaoCompetenciaAvaliacaoDesempenho ConfiguracaoCompetenciaAvaliacaoDesempenho = getEntity();
		ConfiguracaoCompetenciaAvaliacaoDesempenho.setId(id);

		return ConfiguracaoCompetenciaAvaliacaoDesempenho;
	}
	
	public static ConfiguracaoCompetenciaAvaliacaoDesempenho getEntity(Long id, Colaborador avaliador, AvaliacaoDesempenho avaliacaoDesempenho,
			ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Character tipoCompetencia, Long competenciaId)
	{
		ConfiguracaoCompetenciaAvaliacaoDesempenho ConfiguracaoCompetenciaAvaliacaoDesempenho = getEntity(id);
		ConfiguracaoCompetenciaAvaliacaoDesempenho.setAvaliador(avaliador);
		ConfiguracaoCompetenciaAvaliacaoDesempenho.setAvaliacaoDesempenho(avaliacaoDesempenho);
		ConfiguracaoCompetenciaAvaliacaoDesempenho.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		ConfiguracaoCompetenciaAvaliacaoDesempenho.setTipoCompetencia(tipoCompetencia);
		ConfiguracaoCompetenciaAvaliacaoDesempenho.setCompetenciaId(competenciaId);
		
		return ConfiguracaoCompetenciaAvaliacaoDesempenho;
	}
}
