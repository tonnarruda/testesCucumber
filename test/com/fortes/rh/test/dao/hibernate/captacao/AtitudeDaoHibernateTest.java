package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;

public class AtitudeDaoHibernateTest extends GenericDaoHibernateTest<Atitude>
{
	private AtitudeDao atitudeDao;

	@Override
	public Atitude getEntity()
	{
		return AtitudeFactory.getEntity();
	}

	@Override
	public GenericDao<Atitude> getGenericDao()
	{
		return atitudeDao;
	}

	public void setAtitudeDao(AtitudeDao atitudeDao)
	{
		this.atitudeDao = atitudeDao;
	}
}
