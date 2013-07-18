package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Extintor;

public class ExtintorFactory
{
	public static Extintor getEntity()
	{
		Extintor extintor = new Extintor();
		extintor.setTipo("1");
		extintor.setNumeroCilindro(1234);
		extintor.setFabricante("fabricante");
		extintor.setAtivo(true);
		extintor.setPeriodoMaxHidrostatico(60);
		extintor.setPeriodoMaxInspecao(1);
		extintor.setPeriodoMaxRecarga(12);
		extintor.setCapacidade("Capacidade");

		return extintor;
	}

	public static Extintor getEntity(Long id)
	{
		Extintor extintor = getEntity();

		extintor.setId(id);

		return extintor;
	}
}