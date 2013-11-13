package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.FasePcmatManagerImpl;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.test.factory.sesmt.FasePcmatFactory;

public class FasePcmatManagerTest extends MockObjectTestCase
{
	private FasePcmatManagerImpl fasePcmatManager = new FasePcmatManagerImpl();
	private Mock fasePcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        fasePcmatDao = new Mock(FasePcmatDao.class);
        fasePcmatManager.setDao((FasePcmatDao) fasePcmatDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<FasePcmat> fasepcmats = FasePcmatFactory.getCollection(1L);

		fasePcmatDao.expects(once()).method("findAllSelect").with(ANYTHING, eq(empresaId)).will(returnValue(fasepcmats));
		assertEquals(fasepcmats, fasePcmatManager.findAllSelect(null, empresaId));
	}
}
