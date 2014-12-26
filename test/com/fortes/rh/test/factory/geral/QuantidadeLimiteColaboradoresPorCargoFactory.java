package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public class QuantidadeLimiteColaboradoresPorCargoFactory
{
	public static QuantidadeLimiteColaboradoresPorCargo getEntity()
	{
		QuantidadeLimiteColaboradoresPorCargo quantidadeLimiteColaboradoresPorCargo = new QuantidadeLimiteColaboradoresPorCargo();
		quantidadeLimiteColaboradoresPorCargo.setId(null);
		quantidadeLimiteColaboradoresPorCargo.setDescricao("QuantidadeLimiteColaboradoresPorCargo 1");
		
		return quantidadeLimiteColaboradoresPorCargo;
	}

	public static QuantidadeLimiteColaboradoresPorCargo getEntity(AreaOrganizacional areaOrganizacional, Cargo cargo)
	{
		QuantidadeLimiteColaboradoresPorCargo quantidadeLimiteColaboradoresPorCargo = getEntity();
		quantidadeLimiteColaboradoresPorCargo.setAreaOrganizacional(areaOrganizacional);
		quantidadeLimiteColaboradoresPorCargo.setCargo(cargo);
		
		return quantidadeLimiteColaboradoresPorCargo;
	}
	
	public static QuantidadeLimiteColaboradoresPorCargo getEntity(Long id)
	{
		QuantidadeLimiteColaboradoresPorCargo quantidadeLimiteColaboradoresPorCargo = getEntity();
		quantidadeLimiteColaboradoresPorCargo.setId(id);

		return quantidadeLimiteColaboradoresPorCargo;
	}

	public static Collection<QuantidadeLimiteColaboradoresPorCargo> getCollection()
	{
		Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos = new ArrayList<QuantidadeLimiteColaboradoresPorCargo>();
		quantidadeLimiteColaboradoresPorCargos.add(getEntity());

		return quantidadeLimiteColaboradoresPorCargos;
	}
	
	public static Collection<QuantidadeLimiteColaboradoresPorCargo> getCollection(Long id)
	{
		Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos = new ArrayList<QuantidadeLimiteColaboradoresPorCargo>();
		quantidadeLimiteColaboradoresPorCargos.add(getEntity(id));
		
		return quantidadeLimiteColaboradoresPorCargos;
	}
}
