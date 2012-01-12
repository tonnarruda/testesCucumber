package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ProvidenciaDao;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.ProvidenciaFactory;

public class ProvidenciaDaoHibernateTest extends GenericDaoHibernateTest<Providencia>
{
	private ProvidenciaDao providenciaDao;

	@Override
	public Providencia getEntity()
	{
		return ProvidenciaFactory.getEntity();
	}

	@Override
	public GenericDao<Providencia> getGenericDao()
	{
		return providenciaDao;
	}

	public void setProvidenciaDao(ProvidenciaDao providenciaDao)
	{
		this.providenciaDao = providenciaDao;
	}
}
