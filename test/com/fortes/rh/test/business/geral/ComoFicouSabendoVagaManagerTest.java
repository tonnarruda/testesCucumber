package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;

import com.fortes.rh.business.geral.ComoFicouSabendoVagaManagerImpl;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.geral.ComoFicouSabendoVagaFactory;

public class ComoFicouSabendoVagaManagerTest extends MockObjectTestCaseManager<ComoFicouSabendoVagaManagerImpl> implements TesteAutomaticoManager
{
	private Mock comoFicouSabendoVagaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        manager = new ComoFicouSabendoVagaManagerImpl();
        comoFicouSabendoVagaDao = new Mock(ComoFicouSabendoVagaDao.class);
        manager.setDao((ComoFicouSabendoVagaDao) comoFicouSabendoVagaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas = ComoFicouSabendoVagaFactory.getCollection(1L);

		comoFicouSabendoVagaDao.expects(once()).method("findAll").will(returnValue(comoFicouSabendoVagas));
		assertEquals(comoFicouSabendoVagas, manager.findAll());
	}

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(comoFicouSabendoVagaDao);
	}
}
