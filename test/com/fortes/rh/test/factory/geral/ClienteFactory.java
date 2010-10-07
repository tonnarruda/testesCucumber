package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.Cliente;

public class ClienteFactory
{
	public static Cliente getEntity()
	{
		Cliente cliente = new Cliente();
		cliente.setId(null);
		cliente.setNome("FFF");
		return cliente;
	}

	public static Cliente getEntity(Long id)
	{
		Cliente cliente = getEntity();
		cliente.setId(id);

		return cliente;
	}

	public static Collection<Cliente> getCollection()
	{
		Collection<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(getEntity());

		return clientes;
	}
	
	public static Collection<Cliente> getCollection(Long id)
	{
		Collection<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(getEntity(id));
		
		return clientes;
	}
}
