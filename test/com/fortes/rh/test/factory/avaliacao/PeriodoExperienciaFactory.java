package com.fortes.rh.test.factory.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

public class PeriodoExperienciaFactory
{
	public static PeriodoExperiencia getEntity()
	{
		PeriodoExperiencia periodoExperiencia = new PeriodoExperiencia();
		periodoExperiencia.setId(null);
		periodoExperiencia.setDescricao("");
		return periodoExperiencia;
	}

	public static PeriodoExperiencia getEntity(Long id)
	{
		PeriodoExperiencia periodoExperiencia = getEntity();
		periodoExperiencia.setId(id);

		return periodoExperiencia;
	}

	public static Collection<PeriodoExperiencia> getCollection()
	{
		Collection<PeriodoExperiencia> periodoExperiencias = new ArrayList<PeriodoExperiencia>();
		periodoExperiencias.add(getEntity());

		return periodoExperiencias;
	}
	
	public static Collection<PeriodoExperiencia> getCollection(Long id)
	{
		Collection<PeriodoExperiencia> periodoExperiencias = new ArrayList<PeriodoExperiencia>();
		periodoExperiencias.add(getEntity(id));
		
		return periodoExperiencias;
	}
}
