package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ClienteDao;
import com.fortes.rh.model.geral.Cliente;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.ClienteFactory;

public class ClienteDaoHibernateTest extends GenericDaoHibernateTest<Cliente>
{
	private ClienteDao clienteDao;

	@Override
	public Cliente getEntity()
	{
		return ClienteFactory.getEntity();
	}

	@Override
	public GenericDao<Cliente> getGenericDao()
	{
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao)
	{
		this.clienteDao = clienteDao;
	}
}
