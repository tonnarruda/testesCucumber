package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.NaturezaLesaoManagerImpl;
import com.fortes.rh.dao.sesmt.NaturezaLesaoDao;
import com.fortes.rh.model.sesmt.NaturezaLesao;
import com.fortes.rh.test.factory.sesmt.NaturezaLesaoFactory;

public class NaturezaLesaoManagerTest extends MockObjectTestCase
{
	private NaturezaLesaoManagerImpl naturezaLesaoManager = new NaturezaLesaoManagerImpl();
	private Mock naturezaLesaoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        naturezaLesaoDao = new Mock(NaturezaLesaoDao.class);
        naturezaLesaoManager.setDao((NaturezaLesaoDao) naturezaLesaoDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<NaturezaLesao> naturezaLesaos = NaturezaLesaoFactory.getCollection(1L);

		naturezaLesaoDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(naturezaLesaos));
		assertEquals(naturezaLesaos, naturezaLesaoManager.findAllSelect(empresaId));
	}
}
