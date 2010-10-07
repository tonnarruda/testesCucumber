package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.EstadoFactory;

public class EstadoDaoHibernateTest extends GenericDaoHibernateTest<Estado>
{
	private EstadoDao estadoDao;

	public Estado getEntity()
	{
		Estado estado = EstadoFactory.getEntity();
		estado.setSigla("YY");
		estado = estadoDao.save(estado);

		return estado;
	}

	public void testFindBySigla()
	{
		Estado estado = EstadoFactory.getEntity();
		estado.setSigla("XX");
		estado = estadoDao.save(estado);

		assertEquals(estado, estadoDao.findBySigla(estado.getSigla()));
	}

	public GenericDao<Estado> getGenericDao()
	{
		return estadoDao;
	}

	public void setEstadoDao(EstadoDao estadoDao)
	{
		this.estadoDao = estadoDao;
	}
}