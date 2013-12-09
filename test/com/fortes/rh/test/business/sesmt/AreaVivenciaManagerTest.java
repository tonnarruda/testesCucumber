package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AreaVivenciaManagerImpl;
import com.fortes.rh.dao.sesmt.AreaVivenciaDao;

public class AreaVivenciaManagerTest extends MockObjectTestCase
{
	private AreaVivenciaManagerImpl areaVivenciaManager = new AreaVivenciaManagerImpl();
	private Mock areaVivenciaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        areaVivenciaDao = new Mock(AreaVivenciaDao.class);
        areaVivenciaManager.setDao((AreaVivenciaDao) areaVivenciaDao.proxy());
    }
}
