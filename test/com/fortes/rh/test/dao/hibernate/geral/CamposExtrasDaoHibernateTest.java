package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.CamposExtrasDao;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;

public class CamposExtrasDaoHibernateTest extends GenericDaoHibernateTest<CamposExtras>
{
	private CamposExtrasDao camposExtrasDao;

	@Override
	public CamposExtras getEntity()
	{
		return CamposExtrasFactory.getEntity();
	}

	@Override
	public GenericDao<CamposExtras> getGenericDao()
	{
		return camposExtrasDao;
	}

	public void setCamposExtrasDao(CamposExtrasDao camposExtrasDao)
	{
		this.camposExtrasDao = camposExtrasDao;
	}
}
