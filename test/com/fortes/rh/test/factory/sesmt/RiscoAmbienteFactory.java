package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.RiscoAmbiente;

public class RiscoAmbienteFactory
{
	public static RiscoAmbiente getEntity()
	{
		RiscoAmbiente riscoAmbiente = new RiscoAmbiente();
		riscoAmbiente.setId(null);
		return riscoAmbiente;
	}

	public static RiscoAmbiente getEntity(Long id)
	{
		RiscoAmbiente riscoAmbiente = getEntity();
		riscoAmbiente.setId(id);

		return riscoAmbiente;
	}

	public static Collection<RiscoAmbiente> getCollection()
	{
		Collection<RiscoAmbiente> riscoAmbientes = new ArrayList<RiscoAmbiente>();
		riscoAmbientes.add(getEntity());

		return riscoAmbientes;
	}
	
	public static Collection<RiscoAmbiente> getCollection(Long id)
	{
		Collection<RiscoAmbiente> riscoAmbientes = new ArrayList<RiscoAmbiente>();
		riscoAmbientes.add(getEntity(id));
		
		return riscoAmbientes;
	}
}
