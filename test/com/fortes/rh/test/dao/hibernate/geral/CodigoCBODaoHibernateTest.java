package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.test.dao.BaseDaoHibernateTest;

public class CodigoCBODaoHibernateTest extends BaseDaoHibernateTest
{
	private CodigoCBODao codigoCBODao;

	public void setCodigoCBODao(CodigoCBODao codigoCBODao)
	{
		this.codigoCBODao = codigoCBODao;
	}

	public void testBuscaCodigosCBO()
	{
		Collection<String> cbos = codigoCBODao.buscaCodigosCBO("84");
		assertEquals("840105", cbos.toArray()[0]);
	}

	public CodigoCBODao getCodigoCBODao() {
		return codigoCBODao;
	}
	
	
}
