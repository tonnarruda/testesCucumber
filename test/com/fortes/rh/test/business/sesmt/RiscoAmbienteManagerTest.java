package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.RiscoAmbienteManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;

public class RiscoAmbienteManagerTest extends MockObjectTestCase
{
	private RiscoAmbienteManagerImpl riscoAmbienteManager = new RiscoAmbienteManagerImpl();
	private Mock riscoAmbienteDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        riscoAmbienteDao = new Mock(RiscoAmbienteDao.class);
        riscoAmbienteManager.setDao((RiscoAmbienteDao) riscoAmbienteDao.proxy());
    }
	
	public void testRemoveByHistoricoAmbiente()
	{
		Long historicoAmbienteId = 1L;
		
		riscoAmbienteDao.expects(once()).method("removeByHistoricoAmbiente").with(eq(historicoAmbienteId )).will(returnValue(true));
		
		assertTrue(riscoAmbienteManager.removeByHistoricoAmbiente(historicoAmbienteId));
	}
}
