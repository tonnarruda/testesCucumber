package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.NivelCompetencia;

public class NivelCompetenciaFactory
{
	public static NivelCompetencia getEntity()
	{
		NivelCompetencia nivelCompetencia = new NivelCompetencia();
		nivelCompetencia.setId(null);
		return nivelCompetencia;
	}

	public static NivelCompetencia getEntity(Long id)
	{
		NivelCompetencia nivelCompetencia = getEntity();
		nivelCompetencia.setId(id);

		return nivelCompetencia;
	}
	
	public static NivelCompetencia getEntity(String descricao, Integer ordem)
	{
		NivelCompetencia nivelCompetencia = getEntity();
		nivelCompetencia.setDescricao(descricao);
		nivelCompetencia.setOrdem(ordem);

		return nivelCompetencia;
	}

	public static Collection<NivelCompetencia> getCollection()
	{
		Collection<NivelCompetencia> nivelCompetencias = new ArrayList<NivelCompetencia>();
		nivelCompetencias.add(getEntity());

		return nivelCompetencias;
	}
	
	public static Collection<NivelCompetencia> getCollection(Long id)
	{
		Collection<NivelCompetencia> nivelCompetencias = new ArrayList<NivelCompetencia>();
		nivelCompetencias.add(getEntity(id));
		
		return nivelCompetencias;
	}
}
