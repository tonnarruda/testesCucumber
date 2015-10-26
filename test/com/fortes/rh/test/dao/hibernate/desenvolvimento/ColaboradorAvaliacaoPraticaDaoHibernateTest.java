package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;

public class ColaboradorAvaliacaoPraticaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorAvaliacaoPratica>
{
	private ColaboradorAvaliacaoPraticaDao colaboradorAvaliacaoPraticaDao;

	@Override
	public ColaboradorAvaliacaoPratica getEntity()
	{
		return ColaboradorAvaliacaoPraticaFactory.getEntity();
	}

	@Override
	public GenericDao<ColaboradorAvaliacaoPratica> getGenericDao()
	{
		return colaboradorAvaliacaoPraticaDao;
	}

	public void setColaboradorAvaliacaoPraticaDao(ColaboradorAvaliacaoPraticaDao colaboradorAvaliacaoPraticaDao)
	{
		this.colaboradorAvaliacaoPraticaDao = colaboradorAvaliacaoPraticaDao;
	}
}
