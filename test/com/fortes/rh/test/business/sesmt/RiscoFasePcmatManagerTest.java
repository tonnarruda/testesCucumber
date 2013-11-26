package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.RiscoFasePcmatManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoFasePcmatDao;

public class RiscoFasePcmatManagerTest extends MockObjectTestCase
{
	private RiscoFasePcmatManagerImpl riscoFasePcmatManager = new RiscoFasePcmatManagerImpl();
	private Mock riscoFasePcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        riscoFasePcmatDao = new Mock(RiscoFasePcmatDao.class);
        riscoFasePcmatManager.setDao((RiscoFasePcmatDao) riscoFasePcmatDao.proxy());
    }
}
