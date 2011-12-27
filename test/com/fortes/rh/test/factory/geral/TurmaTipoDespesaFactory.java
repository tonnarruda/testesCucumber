package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.TurmaTipoDespesa;

public class TurmaTipoDespesaFactory
{
	public static TurmaTipoDespesa getEntity()
	{
		TurmaTipoDespesa turmaTipoDespesa = new TurmaTipoDespesa();
		turmaTipoDespesa.setId(null);
		return turmaTipoDespesa;
	}

	public static TurmaTipoDespesa getEntity(Long id)
	{
		TurmaTipoDespesa turmaTipoDespesa = getEntity();
		turmaTipoDespesa.setId(id);

		return turmaTipoDespesa;
	}

	public static Collection<TurmaTipoDespesa> getCollection()
	{
		Collection<TurmaTipoDespesa> turmaTipoDespesas = new ArrayList<TurmaTipoDespesa>();
		turmaTipoDespesas.add(getEntity());

		return turmaTipoDespesas;
	}
	
	public static Collection<TurmaTipoDespesa> getCollection(Long id)
	{
		Collection<TurmaTipoDespesa> turmaTipoDespesas = new ArrayList<TurmaTipoDespesa>();
		turmaTipoDespesas.add(getEntity(id));
		
		return turmaTipoDespesas;
	}
}
