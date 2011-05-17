package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;

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


	public void testeFindAllSemOutros()
	{
		comoFicouSabendoVagaDao.save(new ComoFicouSabendoVaga(2L, "f2rh"));
		comoFicouSabendoVagaDao.save(new ComoFicouSabendoVaga(3L, "rh"));
		
		assertEquals(comoFicouSabendoVagaDao.findAllSemOutros().size(), (comoFicouSabendoVagaDao.findAll().size()-1));
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
