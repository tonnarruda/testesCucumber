package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.AreaInteresse;

public class AreaInteresseFactory
{
	public static AreaInteresse getAreaInteresse()
	{
		AreaInteresse areaInteresse = new AreaInteresse();

		areaInteresse.setId(null);
		areaInteresse.setAreasOrganizacionais(null);
		areaInteresse.setNome("nome da area de formação");
		areaInteresse.setObservacao("observação");
		areaInteresse.setEmpresa(null);

		return areaInteresse;
	}

	public static AreaInteresse getAreaInteresse(long id)
	{
		AreaInteresse areaInteresse = getAreaInteresse();
		areaInteresse.setId(id);
		return areaInteresse;
	}
}
