package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;

public class HabilidadeDaoHibernateTest extends GenericDaoHibernateTest<Habilidade>
{
	private HabilidadeDao habilidadeDao;

	@Override
	public Habilidade getEntity()
	{
		return HabilidadeFactory.getEntity();
	}

	@Override
	public GenericDao<Habilidade> getGenericDao()
	{
		return habilidadeDao;
	}

	public void setHabilidadeDao(HabilidadeDao habilidadeDao)
	{
		this.habilidadeDao = habilidadeDao;
	}
}
