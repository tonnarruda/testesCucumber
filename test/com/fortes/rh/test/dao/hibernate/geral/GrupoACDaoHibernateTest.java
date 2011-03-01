package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.GrupoACFactory;

public class GrupoACDaoHibernateTest extends GenericDaoHibernateTest<GrupoAC>
{
	private GrupoACDao grupoACDao;

	@Override
	public GrupoAC getEntity()
	{
		return GrupoACFactory.getEntity();
	}

	@Override
	public GenericDao<GrupoAC> getGenericDao()
	{
		return grupoACDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao)
	{
		this.grupoACDao = grupoACDao;
	}
}
