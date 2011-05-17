package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.ComoFicouSabendoVagaFactory;

public class ComoFicouSabendoVagaDaoHibernateTest extends GenericDaoHibernateTest<ComoFicouSabendoVaga>
{
	private ComoFicouSabendoVagaDao comoFicouSabendoVagaDao;

	@Override
	public ComoFicouSabendoVaga getEntity()
	{
		return ComoFicouSabendoVagaFactory.getEntity();
	}

	@Override
	public GenericDao<ComoFicouSabendoVaga> getGenericDao()
	{
		return comoFicouSabendoVagaDao;
	}

	public void setComoFicouSabendoVagaDao(ComoFicouSabendoVagaDao comoFicouSabendoVagaDao)
	{
		this.comoFicouSabendoVagaDao = comoFicouSabendoVagaDao;
	}
}
