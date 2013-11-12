package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class PcmatDaoHibernateTest extends GenericDaoHibernateTest<Pcmat>
{
	private PcmatDao pcmatDao;

	@Override
	public Pcmat getEntity()
	{
		return PcmatFactory.getEntity();
	}

	@Override
	public GenericDao<Pcmat> getGenericDao()
	{
		return pcmatDao;
	}

	public void setPcmatDao(PcmatDao pcmatDao)
	{
		this.pcmatDao = pcmatDao;
	}
}
