package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.FaturamentoMensal;

public class FaturamentoMensalFactory
{
	public static FaturamentoMensal getEntity()
	{
		FaturamentoMensal faturamentoMensal = new FaturamentoMensal();
		faturamentoMensal.setId(null);
		return faturamentoMensal;
	}

	public static FaturamentoMensal getEntity(Long id)
	{
		FaturamentoMensal faturamentoMensal = getEntity();
		faturamentoMensal.setId(id);

		return faturamentoMensal;
	}

	public static Collection<FaturamentoMensal> getCollection()
	{
		Collection<FaturamentoMensal> faturamentoMensals = new ArrayList<FaturamentoMensal>();
		faturamentoMensals.add(getEntity());

		return faturamentoMensals;
	}
	
	public static Collection<FaturamentoMensal> getCollection(Long id)
	{
		Collection<FaturamentoMensal> faturamentoMensals = new ArrayList<FaturamentoMensal>();
		faturamentoMensals.add(getEntity(id));
		
		return faturamentoMensals;
	}
}
