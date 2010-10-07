package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Beneficio;

public class BeneficioFactory
{
	public static Beneficio getEntity()
	{
		Beneficio beneficio = new Beneficio();

		beneficio.setId(null);
		beneficio.setNome("beneficio");
		beneficio.setEmpresa(null);

		return beneficio;
	}
}
