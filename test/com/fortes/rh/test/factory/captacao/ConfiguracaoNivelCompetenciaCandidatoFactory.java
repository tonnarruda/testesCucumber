package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Solicitacao;

public class ConfiguracaoNivelCompetenciaCandidatoFactory
{
	public static ConfiguracaoNivelCompetenciaCandidato getEntity()
	{
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = new ConfiguracaoNivelCompetenciaCandidato();
		configuracaoNivelCompetenciaCandidato.setId(null);
		return configuracaoNivelCompetenciaCandidato;
	}

	public static ConfiguracaoNivelCompetenciaCandidato getEntity(Long id)
	{
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = getEntity();
		configuracaoNivelCompetenciaCandidato.setId(id);

		return configuracaoNivelCompetenciaCandidato;
	}
	
	public static ConfiguracaoNivelCompetenciaCandidato getEntity(Candidato candidato, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Date data)
	{
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = getEntity();
		configuracaoNivelCompetenciaCandidato.setCandidato(candidato);
		configuracaoNivelCompetenciaCandidato.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaCandidato.setData(data);
		return configuracaoNivelCompetenciaCandidato;
	}

	public static ConfiguracaoNivelCompetenciaCandidato getEntity(Candidato candidato, Solicitacao solicitacao, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Date data)
	{
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = getEntity(candidato, configuracaoNivelCompetenciaFaixaSalarial, data);
		configuracaoNivelCompetenciaCandidato.setSolicitacao(solicitacao);
		return configuracaoNivelCompetenciaCandidato;
	}

	public static Collection<ConfiguracaoNivelCompetenciaCandidato> getCollection()
	{
		Collection<ConfiguracaoNivelCompetenciaCandidato> configuracoesNivelCompetenciaCandidato = new ArrayList<ConfiguracaoNivelCompetenciaCandidato>();
		configuracoesNivelCompetenciaCandidato.add(getEntity());

		return configuracoesNivelCompetenciaCandidato;
	}
	
	public static Collection<ConfiguracaoNivelCompetenciaCandidato> getCollection(Long id)
	{
		Collection<ConfiguracaoNivelCompetenciaCandidato> configuracoesNivelCompetenciaCandidato = new ArrayList<ConfiguracaoNivelCompetenciaCandidato>();
		configuracoesNivelCompetenciaCandidato.add(getEntity(id));

		return configuracoesNivelCompetenciaCandidato;
	}
}
