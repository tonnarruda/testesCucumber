package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.AfastamentoDao;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class AfastamentoDaoHibernateTest extends GenericDaoHibernateTest<Afastamento>
{
	private AfastamentoDao afastamentoDao;

	@Override
	public Afastamento getEntity()
	{
		Afastamento afastamento = new Afastamento();
		return afastamento;
	}

	@Override
	public GenericDao<Afastamento> getGenericDao()
	{
		return afastamentoDao;
	}

	public void setAfastamentoDao(AfastamentoDao afastamentoDao)
	{
		this.afastamentoDao = afastamentoDao;
	}
}