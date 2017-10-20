package com.fortes.rh.test.dao.hibernate.sesmt.eSocialTabelas;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.eSocialTabelas.ParteCorpoAtingidaDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.ParteCorpoAtingidaFactory;

public class ParteCorpoAtingidaDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<ParteCorpoAtingida>
{
	@Autowired
	private ParteCorpoAtingidaDao parteCorpoAtingidaDao;

	@Override
	public ParteCorpoAtingida getEntity()
	{
		return ParteCorpoAtingidaFactory.getEntity();
	}

	public GenericDao<ParteCorpoAtingida> getGenericDao()
	{
		return parteCorpoAtingidaDao;
	}
	
	@Test
	public void testeFindAllSelect(){
		assertEquals(45, parteCorpoAtingidaDao.findAllSelect().size());
	}
}
