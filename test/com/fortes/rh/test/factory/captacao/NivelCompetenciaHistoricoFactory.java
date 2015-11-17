package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.geral.Empresa;

public class NivelCompetenciaHistoricoFactory
{
	public static NivelCompetenciaHistorico getEntity()
	{
		NivelCompetenciaHistorico nivelCompetenciaHistorico = new NivelCompetenciaHistorico();
		nivelCompetenciaHistorico.setId(null);
		return nivelCompetenciaHistorico;
	}

	public static NivelCompetenciaHistorico getEntity(Long id)
	{
		NivelCompetenciaHistorico nivelCompetenciaHistorico = getEntity();
		nivelCompetenciaHistorico.setId(id);

		return nivelCompetenciaHistorico;
	}
	
	public static NivelCompetenciaHistorico getEntity(Date data, Empresa empresa)
	{
		NivelCompetenciaHistorico nivelCompetenciaHistorico = new NivelCompetenciaHistorico();
		nivelCompetenciaHistorico.setId(null);
		nivelCompetenciaHistorico.setData(data);
		nivelCompetenciaHistorico.setEmpresa(empresa);
		return nivelCompetenciaHistorico;
	}

	public static Collection<NivelCompetenciaHistorico> getCollection()
	{
		Collection<NivelCompetenciaHistorico> nivelCompetenciaHistoricos = new ArrayList<NivelCompetenciaHistorico>();
		nivelCompetenciaHistoricos.add(getEntity());

		return nivelCompetenciaHistoricos;
	}
	
	public static Collection<NivelCompetenciaHistorico> getCollection(Long id)
	{
		Collection<NivelCompetenciaHistorico> nivelCompetenciaHistoricos = new ArrayList<NivelCompetenciaHistorico>();
		nivelCompetenciaHistoricos.add(getEntity(id));
		
		return nivelCompetenciaHistoricos;
	}
}
