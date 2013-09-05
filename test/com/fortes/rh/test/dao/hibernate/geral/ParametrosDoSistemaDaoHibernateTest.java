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
		parametrosDoSistema.setTelaInicialModuloExterno('L');

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
	
	public void testUpdateServidorRemprot()
	{
		ParametrosDoSistema ps = ParametrosDoSistemaFactory.getEntity();
		ps.setServidorRemprot("12.2.3.5");
		parametrosDoSistemaDao.save(ps);
		
		parametrosDoSistemaDao.updateServidorRemprot("9.9.9.8");
		assertEquals("9.9.9.8", parametrosDoSistemaDao.findByIdProjection(ps.getId()).getServidorRemprot());
	}

	public void getContexto()
	{
		ParametrosDoSistema ps = ParametrosDoSistemaFactory.getEntity();
		ps.setAppContext("/fortesrhteste");
		parametrosDoSistemaDao.save(ps);
		
		assertEquals("/fortesrhteste", parametrosDoSistemaDao.getContexto());
	}
}