package com.fortes.rh.test.factory.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Empresa;

public class PeriodoExperienciaFactory
{
	public static PeriodoExperiencia getEntity()
	{
		PeriodoExperiencia periodoExperiencia = new PeriodoExperiencia();
		periodoExperiencia.setId(null);
		periodoExperiencia.setDescricao("");
		periodoExperiencia.setAtivo(true);
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
	
	public static PeriodoExperiencia getEntity (Empresa empresa, Integer dias, boolean ativo ) {
		PeriodoExperiencia periodoExperiencia = getEntity();
		periodoExperiencia.setEmpresa(empresa);
		periodoExperiencia.setDias(dias);
		periodoExperiencia.setAtivo(ativo);
		return periodoExperiencia;
	}
}
