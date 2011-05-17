package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ComoFicouSabendoVagaManagerImpl;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.test.factory.geral.ComoFicouSabendoVagaFactory;

public class ComoFicouSabendoVagaManagerTest extends MockObjectTestCase
{
	private ComoFicouSabendoVagaManagerImpl comoFicouSabendoVagaManager = new ComoFicouSabendoVagaManagerImpl();
	private Mock comoFicouSabendoVagaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        comoFicouSabendoVagaDao = new Mock(ComoFicouSabendoVagaDao.class);
        comoFicouSabendoVagaManager.setDao((ComoFicouSabendoVagaDao) comoFicouSabendoVagaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas = ComoFicouSabendoVagaFactory.getCollection(1L);

		comoFicouSabendoVagaDao.expects(once()).method("findAll").will(returnValue(comoFicouSabendoVagas));
		assertEquals(comoFicouSabendoVagas, comoFicouSabendoVagaManager.findAll());
	}
}
