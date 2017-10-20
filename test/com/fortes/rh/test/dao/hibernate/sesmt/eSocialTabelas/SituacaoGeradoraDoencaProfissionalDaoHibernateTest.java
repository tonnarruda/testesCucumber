package com.fortes.rh.test.dao.hibernate.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissionalDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissional;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissionalFactory;

public class SituacaoGeradoraDoencaProfissionalDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<SituacaoGeradoraDoencaProfissional>
{
	@Autowired
	private SituacaoGeradoraDoencaProfissionalDao situacaoGeradoraDoencaProfissionalDao;

	@Override
	public SituacaoGeradoraDoencaProfissional getEntity()
	{
		return SituacaoGeradoraDoencaProfissionalFactory.getEntity();
	}

	public GenericDao<SituacaoGeradoraDoencaProfissional> getGenericDao()
	{
		return situacaoGeradoraDoencaProfissionalDao;
	}
	
	@Test
	public void testeFindAllSelect(){
		assertEquals(59, situacaoGeradoraDoencaProfissionalDao.findAllSelect().size());
	}
}
