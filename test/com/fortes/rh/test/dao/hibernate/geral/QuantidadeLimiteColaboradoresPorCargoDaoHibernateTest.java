package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class QuantidadeLimiteColaboradoresPorCargoDaoHibernateTest extends GenericDaoHibernateTest<QuantidadeLimiteColaboradoresPorCargo>
{
	private QuantidadeLimiteColaboradoresPorCargoDao quantidadeLimiteColaboradoresPorCargoDao;

	@Override
	public QuantidadeLimiteColaboradoresPorCargo getEntity()
	{
		return new QuantidadeLimiteColaboradoresPorCargo();
	}

	@Override
	public GenericDao<QuantidadeLimiteColaboradoresPorCargo> getGenericDao()
	{
		return quantidadeLimiteColaboradoresPorCargoDao;
	}
	
	public void setQuantidadeLimiteColaboradoresPorCargoDao(QuantidadeLimiteColaboradoresPorCargoDao quantidadeLimiteColaboradoresPorCargoDao)
	{
		this.quantidadeLimiteColaboradoresPorCargoDao = quantidadeLimiteColaboradoresPorCargoDao;
	}
}
