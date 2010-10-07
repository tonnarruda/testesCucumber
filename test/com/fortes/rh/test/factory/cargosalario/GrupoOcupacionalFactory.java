package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.GrupoOcupacional;

public class GrupoOcupacionalFactory
{
	public static GrupoOcupacional getGrupoOcupacional()
	{
		GrupoOcupacional grupoOcupacional = new GrupoOcupacional();

		grupoOcupacional.setId(null);
		grupoOcupacional.setNome("nome da faixa");
		grupoOcupacional.setCargos(null);
		grupoOcupacional.setEmpresa(null);

		return grupoOcupacional;
	}

	public static GrupoOcupacional getGrupoOcupacional(Long id)
	{
		GrupoOcupacional grupoOcupacional = getGrupoOcupacional();
		grupoOcupacional.setId(id);

		return grupoOcupacional;
	}

	public static Collection<GrupoOcupacional> getCollection()
	{
		Collection<GrupoOcupacional> collection = new ArrayList<GrupoOcupacional>();
		collection.add(getGrupoOcupacional());
		return collection;
	}
}
