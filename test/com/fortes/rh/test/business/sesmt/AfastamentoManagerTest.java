package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AfastamentoManagerImpl;
import com.fortes.rh.dao.sesmt.AfastamentoDao;

public class AfastamentoManagerTest extends MockObjectTestCase
{
	private AfastamentoManagerImpl afastamentoManager = new AfastamentoManagerImpl();
	private Mock afastamentoDao = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        afastamentoDao = new Mock(AfastamentoDao.class);
        afastamentoManager.setDao((AfastamentoDao) afastamentoDao.proxy());
    }
	
	public void testTemporario()
	{
		assertEquals(true, true);
	}
}