package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Empresa;

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
	
	public static NivelCompetencia getEntity(String descricao)
	{
		NivelCompetencia nivelCompetencia = getEntity();
		nivelCompetencia.setDescricao(descricao);
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
		NivelCompetencia nivel1 = getEntity();
		nivel1.setOrdem(3);
		
		nivelCompetencias.add(nivel1);

		return nivelCompetencias;
	}
	
	public static Collection<NivelCompetencia> getCollection(Long id)
	{
		Collection<NivelCompetencia> nivelCompetencias = new ArrayList<NivelCompetencia>();
		nivelCompetencias.add(getEntity(id));
		
		return nivelCompetencias;
	}
	public static NivelCompetencia getEntity(Long id, String descricao, Empresa empresa){
		NivelCompetencia nivelCompetencia = getEntity(id);
		nivelCompetencia.setDescricao(descricao);
		nivelCompetencia.setEmpresa(empresa);
		return nivelCompetencia;
	}
	
}
