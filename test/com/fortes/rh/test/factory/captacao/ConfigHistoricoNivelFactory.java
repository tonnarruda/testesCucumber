package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;

public class ConfigHistoricoNivelFactory
{
	public static ConfigHistoricoNivel getEntity()
	{
		ConfigHistoricoNivel configHistoricoNivel = new ConfigHistoricoNivel();
		configHistoricoNivel.setId(null);
		return configHistoricoNivel;
	}

	public static ConfigHistoricoNivel getEntity(Long id)
	{
		ConfigHistoricoNivel configHistoricoNivel = getEntity();
		configHistoricoNivel.setId(id);

		return configHistoricoNivel;
	}

	public static Collection<ConfigHistoricoNivel> getCollection()
	{
		Collection<ConfigHistoricoNivel> configHistoricoNivels = new ArrayList<ConfigHistoricoNivel>();
		configHistoricoNivels.add(getEntity());

		return configHistoricoNivels;
	}
	
	public static Collection<ConfigHistoricoNivel> getCollection(Long id)
	{
		Collection<ConfigHistoricoNivel> configHistoricoNivels = new ArrayList<ConfigHistoricoNivel>();
		configHistoricoNivels.add(getEntity(id));
		
		return configHistoricoNivels;
	}
	
	public static ConfigHistoricoNivel getEntityAndNivelCompetenciaAndNivelCOmpetenciaHistorico(Long id)
	{
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L, new Date());
		
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity();
		configHistoricoNivel.setNivelCompetencia(nivelCompetencia);
		configHistoricoNivel.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configHistoricoNivel.setPercentual(100.0);
		
		return configHistoricoNivel;
	}
	
	
}
