package com.fortes.rh.test.business.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManagerImpl;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;

public class QuantidadeLimiteColaboradoresPorCargoManagerTest extends MockObjectTestCase
{
	private QuantidadeLimiteColaboradoresPorCargoManagerImpl quantidadeLimiteColaboradoresPorCargoManager = new QuantidadeLimiteColaboradoresPorCargoManagerImpl();
	private Mock quantidadeLimiteColaboradoresPorCargoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        quantidadeLimiteColaboradoresPorCargoDao = new Mock(QuantidadeLimiteColaboradoresPorCargoDao.class);
        quantidadeLimiteColaboradoresPorCargoManager.setDao((QuantidadeLimiteColaboradoresPorCargoDao) quantidadeLimiteColaboradoresPorCargoDao.proxy());
    }


}
