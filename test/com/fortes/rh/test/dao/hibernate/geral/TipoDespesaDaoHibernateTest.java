package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.TipoDespesaDao;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.TipoDespesaFactory;

public class TipoDespesaDaoHibernateTest extends GenericDaoHibernateTest<TipoDespesa>
{
	private TipoDespesaDao tipoDespesaDao;

	@Override
	public TipoDespesa getEntity()
	{
		return TipoDespesaFactory.getEntity();
	}

	@Override
	public GenericDao<TipoDespesa> getGenericDao()
	{
		return tipoDespesaDao;
	}

	public void setTipoDespesaDao(TipoDespesaDao tipoDespesaDao)
	{
		this.tipoDespesaDao = tipoDespesaDao;
	}
}
