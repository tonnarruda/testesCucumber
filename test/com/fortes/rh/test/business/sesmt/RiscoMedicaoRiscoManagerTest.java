package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;

public class RiscoMedicaoRiscoManagerTest extends MockObjectTestCase
{
	private RiscoMedicaoRiscoManagerImpl riscoMedicaoRiscoManager = new RiscoMedicaoRiscoManagerImpl();
	private Mock riscoMedicaoRiscoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        riscoMedicaoRiscoDao = new Mock(RiscoMedicaoRiscoDao.class);
        riscoMedicaoRiscoManager.setDao((RiscoMedicaoRiscoDao) riscoMedicaoRiscoDao.proxy());
    }
	
	public void testRemoveByMedicaoRisco()
	{
		Long medicaoRiscoId = 1L;
		riscoMedicaoRiscoDao.expects(once()).method("removeByMedicaoRisco").with(eq(medicaoRiscoId)).will(returnValue(true));
		
		assertTrue(riscoMedicaoRiscoManager.removeByMedicaoRisco(medicaoRiscoId));
	}
}