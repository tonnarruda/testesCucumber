package com.fortes.rh.test.business.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.EstadoManagerImpl;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.factory.geral.EstadoFactory;

public class EstadoManagerTest extends MockObjectTestCase
{
	EstadoManagerImpl estadoManager = null;
	Mock estadoDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		estadoManager = new EstadoManagerImpl();

		estadoDao = new Mock(EstadoDao.class);
		estadoManager.setDao((EstadoDao) estadoDao.proxy());
	}

	public void testFindBySigla()
	{
		Estado estado = EstadoFactory.getEntity(1L);

		estadoDao.expects(once()).method("findBySigla").with(eq(estado.getSigla())).will(returnValue(estado));

		assertEquals(estado, estadoManager.findBySigla(estado.getSigla()));
	}
}
