package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ProvidenciaManagerImpl;
import com.fortes.rh.dao.geral.ProvidenciaDao;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.test.factory.geral.ProvidenciaFactory;

public class ProvidenciaManagerTest extends MockObjectTestCase
{
	private ProvidenciaManagerImpl providenciaManager = new ProvidenciaManagerImpl();
	private Mock providenciaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        providenciaDao = new Mock(ProvidenciaDao.class);
        providenciaManager.setDao((ProvidenciaDao) providenciaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<Providencia> providencias = ProvidenciaFactory.getCollection(1L);

		providenciaDao.expects(once()).method("findAll").will(returnValue(providencias));
		assertEquals(providencias, providenciaManager.findAll());
	}
}
