package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.SinalizacaoPcmatManagerImpl;
import com.fortes.rh.dao.sesmt.SinalizacaoPcmatDao;

public class SinalizacaoPcmatManagerTest extends MockObjectTestCase
{
	private SinalizacaoPcmatManagerImpl sinalizacaoPcmatManager = new SinalizacaoPcmatManagerImpl();
	private Mock sinalizacaoPcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        sinalizacaoPcmatDao = new Mock(SinalizacaoPcmatDao.class);
        sinalizacaoPcmatManager.setDao((SinalizacaoPcmatDao) sinalizacaoPcmatDao.proxy());
    }
}
