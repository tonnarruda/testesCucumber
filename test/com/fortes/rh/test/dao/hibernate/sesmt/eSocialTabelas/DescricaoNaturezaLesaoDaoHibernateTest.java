package com.fortes.rh.test.dao.hibernate.sesmt.eSocialTabelas;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.eSocialTabelas.DescricaoNaturezaLesaoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.DescricaoNaturezaLesaoFactory;

public class DescricaoNaturezaLesaoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<DescricaoNaturezaLesao>
{
	@Autowired
	private DescricaoNaturezaLesaoDao descricaoNaturezaLesaoDao;

	@Override
	public DescricaoNaturezaLesao getEntity()
	{
		return DescricaoNaturezaLesaoFactory.getEntity();
	}

	public GenericDao<DescricaoNaturezaLesao> getGenericDao()
	{
		return descricaoNaturezaLesaoDao;
	}
	
	@Test
	public void testeFindAllSelect(){
		assertEquals(29, descricaoNaturezaLesaoDao.findAllSelect().size());
	}
}
