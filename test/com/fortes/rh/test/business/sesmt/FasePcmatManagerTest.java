package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.FasePcmatManagerImpl;
import com.fortes.rh.dao.sesmt.FasePcmatDao;

public class FasePcmatManagerTest extends MockObjectTestCase
{
	private FasePcmatManagerImpl fasePcmatManager = new FasePcmatManagerImpl();
	private Mock fasePcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        fasePcmatDao = new Mock(FasePcmatDao.class);
        fasePcmatManager.setDao((FasePcmatDao) fasePcmatDao.proxy());
    }
}
