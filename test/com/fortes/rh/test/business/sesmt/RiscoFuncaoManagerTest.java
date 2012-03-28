package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.RiscoFuncaoManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;

public class RiscoFuncaoManagerTest extends MockObjectTestCase
{
	private RiscoFuncaoManagerImpl riscoFuncaoManager = new RiscoFuncaoManagerImpl();
	private Mock riscoFuncaoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        riscoFuncaoDao = new Mock(RiscoFuncaoDao.class);
        riscoFuncaoManager.setDao((RiscoFuncaoDao) riscoFuncaoDao.proxy());
    }
	
	public void testRemoveByHistoricoFuncao()
	{
		Long historicoFuncaoId = 1L;
		
		riscoFuncaoDao.expects(once()).method("removeByHistoricoFuncao").with(eq(historicoFuncaoId )).will(returnValue(true));
		
		assertTrue(riscoFuncaoManager.removeByHistoricoFuncao(historicoFuncaoId));
	}
}
