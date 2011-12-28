package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.TipoDespesa;

public class TipoDespesaFactory
{
	public static TipoDespesa getEntity()
	{
		TipoDespesa tipoDespesa = new TipoDespesa();
		tipoDespesa.setId(null);
		return tipoDespesa;
	}

	public static TipoDespesa getEntity(Long id)
	{
		TipoDespesa tipoDespesa = getEntity();
		tipoDespesa.setId(id);

		return tipoDespesa;
	}

	public static Collection<TipoDespesa> getCollection()
	{
		Collection<TipoDespesa> tipoDespesas = new ArrayList<TipoDespesa>();
		tipoDespesas.add(getEntity());

		return tipoDespesas;
	}
	
	public static Collection<TipoDespesa> getCollection(Long id)
	{
		Collection<TipoDespesa> tipoDespesas = new ArrayList<TipoDespesa>();
		tipoDespesas.add(getEntity(id));
		
		return tipoDespesas;
	}
}
