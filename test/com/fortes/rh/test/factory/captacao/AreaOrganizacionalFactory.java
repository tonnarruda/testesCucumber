package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;

public class AreaOrganizacionalFactory
{
	public static AreaOrganizacional getEntity()
	{
		AreaOrganizacional areaOrganizacional = new AreaOrganizacional();

		areaOrganizacional.setId(1L);
		areaOrganizacional.setAreaMae(null);

		AreaInteresse areaInteresse = new AreaInteresse();
		areaInteresse.setId(1L);

		Collection<AreaInteresse> areaInteresses = new ArrayList<AreaInteresse>();
		areaInteresses.add(areaInteresse);

		areaOrganizacional.setAreasInteresse(areaInteresses);

		areaOrganizacional.setCodigoAC("0");
		areaOrganizacional.setConhecimentos(null);
		areaOrganizacional.setDescricao("descrição");
		areaOrganizacional.setNome("nome da area organizacional");

		return areaOrganizacional;
	}

	public static AreaOrganizacional getEntity(Long id)
	{
		AreaOrganizacional areaOrganizacional = getEntity();
		areaOrganizacional.setId(id);

		return areaOrganizacional;
	}

	public static Collection<AreaOrganizacional> getCollection()
	{
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(getEntity());

		return areaOrganizacionals;
	}
}
