package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.PcmatManagerImpl;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class PcmatManagerTest extends MockObjectTestCase
{
	private PcmatManagerImpl pcmatManager = new PcmatManagerImpl();
	private Mock pcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        pcmatDao = new Mock(PcmatDao.class);
        pcmatManager.setDao((PcmatDao) pcmatDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<Pcmat> pcmats = PcmatFactory.getCollection(1L);

		pcmatDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(pcmats));
		assertEquals(pcmats, pcmatManager.findAllSelect(empresaId));
	}
}
