package com.fortes.rh.test.dao.hibernate.avaliacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;

public class AvaliacaoPraticaDaoHibernateTest extends GenericDaoHibernateTest<AvaliacaoPratica>
{
	private AvaliacaoPraticaDao avaliacaoPraticaDao;

	@Override
	public AvaliacaoPratica getEntity()
	{
		return AvaliacaoPraticaFactory.getEntity();
	}

	@Override
	public GenericDao<AvaliacaoPratica> getGenericDao()
	{
		return avaliacaoPraticaDao;
	}

	public void setAvaliacaoPraticaDao(AvaliacaoPraticaDao avaliacaoPraticaDao)
	{
		this.avaliacaoPraticaDao = avaliacaoPraticaDao;
	}
}
