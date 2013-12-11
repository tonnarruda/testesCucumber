package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AtividadeSegurancaPcmatManagerImpl;
import com.fortes.rh.dao.sesmt.AtividadeSegurancaPcmatDao;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;
import com.fortes.rh.test.factory.sesmt.AtividadeSegurancaPcmatFactory;

public class AtividadeSegurancaPcmatManagerTest extends MockObjectTestCase
{
	private AtividadeSegurancaPcmatManagerImpl atividadeSegurancaPcmatManager = new AtividadeSegurancaPcmatManagerImpl();
	private Mock atividadeSegurancaPcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        atividadeSegurancaPcmatDao = new Mock(AtividadeSegurancaPcmatDao.class);
        atividadeSegurancaPcmatManager.setDao((AtividadeSegurancaPcmatDao) atividadeSegurancaPcmatDao.proxy());
    }

	public void testFindByPcmat()
	{
		Long pcmatId = 1L;
		
		Collection<AtividadeSegurancaPcmat> atividadeSegurancaPcmats = AtividadeSegurancaPcmatFactory.getCollection(1L);

		atividadeSegurancaPcmatDao.expects(once()).method("findByPcmat").with(eq(pcmatId)).will(returnValue(atividadeSegurancaPcmats));
		assertEquals(atividadeSegurancaPcmats, atividadeSegurancaPcmatManager.findByPcmat(pcmatId));
	}
}
