package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EpcPcmatManagerImpl;
import com.fortes.rh.dao.sesmt.EpcPcmatDao;
import com.fortes.rh.model.sesmt.EpcPcmat;
import com.fortes.rh.test.factory.sesmt.EpcPcmatFactory;

public class EpcPcmatManagerTest extends MockObjectTestCase
{
	private EpcPcmatManagerImpl epcPcmatManager = new EpcPcmatManagerImpl();
	private Mock epcPcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        epcPcmatDao = new Mock(EpcPcmatDao.class);
        epcPcmatManager.setDao((EpcPcmatDao) epcPcmatDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long pcmatId = 1L;
		
		Collection<EpcPcmat> epcPcmats = EpcPcmatFactory.getCollection(1L);

		epcPcmatDao.expects(once()).method("findByPcmat").with(eq(pcmatId)).will(returnValue(epcPcmats));
		assertEquals(epcPcmats, epcPcmatManager.findByPcmat(pcmatId));
	}
}
