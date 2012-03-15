package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ParametrosDoSistemaDao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;

public class ParametrosDoSistemaDaoHibernateTest extends GenericDaoHibernateTest<ParametrosDoSistema>
{
	private ParametrosDoSistemaDao parametrosDoSistemaDao;

	public ParametrosDoSistema getEntity()
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();

		parametrosDoSistema.setId(null);
		parametrosDoSistema.setAppUrl("url");

		return parametrosDoSistema;
	}

	public GenericDao<ParametrosDoSistema> getGenericDao()
	{
		return parametrosDoSistemaDao;
	}

	public void setParametrosDoSistemaDao(ParametrosDoSistemaDao parametrosDoSistemaDao)
	{
		this.parametrosDoSistemaDao = parametrosDoSistemaDao;
	}

	public void testFindByIdProjection()
	{
		ParametrosDoSistema ps = ParametrosDoSistemaFactory.getEntity();

		ps = parametrosDoSistemaDao.save(ps);

		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaDao.findByIdProjection(ps.getId());

		assertEquals(ps.getId(), parametrosDoSistema.getId());
	}
	
	public void testUpdateModulos()
	{
		ParametrosDoSistema ps = ParametrosDoSistemaFactory.getEntity();
		ps.setModulos("teste");
		ps = parametrosDoSistemaDao.save(ps);
		
		parametrosDoSistemaDao.updateModulos("bla");
		assertEquals("bla", parametrosDoSistemaDao.findByIdProjection(ps.getId()).getModulos());
	}
	
	public void testUpdateServidorRemprot()
	{
		ParametrosDoSistema ps = ParametrosDoSistemaFactory.getEntity();
		ps.setServidorRemprot("12.2.3.5");
		parametrosDoSistemaDao.save(ps);
		
		parametrosDoSistemaDao.updateServidorRemprot("9.9.9.8");
		assertEquals("9.9.9.8", parametrosDoSistemaDao.findByIdProjection(ps.getId()).getServidorRemprot());
	}
	
	public void testDisablePapeisIds()
	{
		ParametrosDoSistema ps = ParametrosDoSistemaFactory.getEntity();
		ps.setAtualizaPapeisIdsAPartirDe(1L);
		parametrosDoSistemaDao.save(ps);
		
		parametrosDoSistemaDao.disablePapeisIds();
	}
}