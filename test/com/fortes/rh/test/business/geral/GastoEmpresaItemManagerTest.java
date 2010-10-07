package com.fortes.rh.test.business.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GastoEmpresaItemManagerImpl;
import com.fortes.rh.dao.geral.GastoEmpresaItemDao;

public class GastoEmpresaItemManagerTest extends MockObjectTestCase
{
	GastoEmpresaItemManagerImpl gastoEmpresaItemManager = null;
	Mock gastoEmpresaItemDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		gastoEmpresaItemManager = new GastoEmpresaItemManagerImpl();
		gastoEmpresaItemDao = new Mock(GastoEmpresaItemDao.class);
		gastoEmpresaItemManager.setDao((GastoEmpresaItemDao) gastoEmpresaItemDao.proxy());
	}

}