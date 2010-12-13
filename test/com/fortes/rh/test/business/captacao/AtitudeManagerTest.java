package com.fortes.rh.test.business.captacao;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.AtitudeManagerImpl;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;

public class AtitudeManagerTest extends MockObjectTestCase
{
	private AtitudeManagerImpl atitudeManager = new AtitudeManagerImpl();
	private Mock atitudeDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        atitudeDao = new Mock(AtitudeDao.class);
        atitudeManager.setDao((AtitudeDao) atitudeDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<Atitude> atitudes = AtitudeFactory.getCollection(1L);

		atitudeDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(atitudes));
		//assertEquals(atitudes, atitudeManager.findAllSelect(empresaId));
	}
}
