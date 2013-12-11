package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AreaVivenciaPcmatManagerImpl;
import com.fortes.rh.dao.sesmt.AreaVivenciaPcmatDao;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.test.factory.sesmt.AreaVivenciaPcmatFactory;

public class AreaVivenciaPcmatManagerTest extends MockObjectTestCase
{
	private AreaVivenciaPcmatManagerImpl areaVivenciaPcmatManager = new AreaVivenciaPcmatManagerImpl();
	private Mock areaVivenciaPcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        areaVivenciaPcmatDao = new Mock(AreaVivenciaPcmatDao.class);
        areaVivenciaPcmatManager.setDao((AreaVivenciaPcmatDao) areaVivenciaPcmatDao.proxy());
    }

	public void testFindByPcmat()
	{
		Long pcmatId = 1L;
		
		Collection<AreaVivenciaPcmat> areaVivenciaPcmats = AreaVivenciaPcmatFactory.getCollection(1L);

		areaVivenciaPcmatDao.expects(once()).method("findByPcmat").with(eq(pcmatId)).will(returnValue(areaVivenciaPcmats));
		assertEquals(areaVivenciaPcmats, areaVivenciaPcmatManager.findByPcmat(pcmatId));
	}
}
