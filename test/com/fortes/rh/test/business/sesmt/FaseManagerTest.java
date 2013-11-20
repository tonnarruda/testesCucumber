package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.FaseManagerImpl;
import com.fortes.rh.dao.sesmt.FaseDao;
import com.fortes.rh.model.sesmt.Fase;
import com.fortes.rh.test.factory.sesmt.FaseFactory;

public class FaseManagerTest extends MockObjectTestCase
{
	private FaseManagerImpl PcmatManager = new FaseManagerImpl();
	private Mock faseDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        faseDao = new Mock(FaseDao.class);
        PcmatManager.setDao((FaseDao) faseDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<Fase> fases = FaseFactory.getCollection(1L);

		faseDao.expects(once()).method("findAllSelect").with(ANYTHING, eq(empresaId)).will(returnValue(fases));
		assertEquals(fases, PcmatManager.findAllSelect(null, empresaId));
	}
}
