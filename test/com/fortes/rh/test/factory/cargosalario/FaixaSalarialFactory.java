package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.FaixaSalarial;

public class FaixaSalarialFactory
{
	public static FaixaSalarial getEntity()
	{
		FaixaSalarial faixaSalarial = new FaixaSalarial();

		faixaSalarial.setCargo(null);
		faixaSalarial.setCodigoAC("0");
		faixaSalarial.setId(null);
		faixaSalarial.setNome("faixa");

		return faixaSalarial;
	}

	public static FaixaSalarial getEntity(Long id)
	{
		FaixaSalarial faixaSalarial = getEntity();
		faixaSalarial.setId(id);

		return faixaSalarial;
	}

	public static Collection<FaixaSalarial> getCollection()
	{
		Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
		faixaSalarials.add(getEntity());

		return faixaSalarials;
	}
}
