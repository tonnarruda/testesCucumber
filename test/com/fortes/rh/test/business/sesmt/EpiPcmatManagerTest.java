package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EpiPcmatManagerImpl;
import com.fortes.rh.dao.sesmt.EpiPcmatDao;
import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.rh.test.factory.sesmt.EpiPcmatFactory;

public class EpiPcmatManagerTest extends MockObjectTestCase
{
	private EpiPcmatManagerImpl epiPcmatManager = new EpiPcmatManagerImpl();
	private Mock epiPcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        epiPcmatDao = new Mock(EpiPcmatDao.class);
        epiPcmatManager.setDao((EpiPcmatDao) epiPcmatDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long pcmatId = 1L;
		
		Collection<EpiPcmat> epiPcmats = EpiPcmatFactory.getCollection(1L);

		epiPcmatDao.expects(once()).method("findByPcmat").with(eq(pcmatId)).will(returnValue(epiPcmats));
		assertEquals(epiPcmats, epiPcmatManager.findByPcmat(pcmatId));
	}
}
