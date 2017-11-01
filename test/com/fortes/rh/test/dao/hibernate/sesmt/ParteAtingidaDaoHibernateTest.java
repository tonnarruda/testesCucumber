package com.fortes.rh.test.dao.hibernate.sesmt;

import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.ParteAtingidaDao;
import com.fortes.rh.model.sesmt.ParteAtingida;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.sesmt.ParteAtingidaFactory;

public class ParteAtingidaDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<ParteAtingida>
{
	@Autowired
	private ParteAtingidaDao parteAtingidaDao;

	@Override
	public ParteAtingida getEntity()
	{
		return ParteAtingidaFactory.getEntity();
	}

	public GenericDao<ParteAtingida> getGenericDao()
	{
		return parteAtingidaDao;
	}
}
