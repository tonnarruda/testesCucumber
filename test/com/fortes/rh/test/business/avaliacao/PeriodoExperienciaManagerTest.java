package com.fortes.rh.test.business.avaliacao;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.PeriodoExperienciaManagerImpl;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;

public class PeriodoExperienciaManagerTest extends MockObjectTestCase
{
	private PeriodoExperienciaManagerImpl periodoExperienciaManager = new PeriodoExperienciaManagerImpl();
	private Mock periodoExperienciaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        periodoExperienciaDao = new Mock(PeriodoExperienciaDao.class);
        periodoExperienciaManager.setDao((PeriodoExperienciaDao) periodoExperienciaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<PeriodoExperiencia> periodoExperiencias = PeriodoExperienciaFactory.getCollection(1L);

		periodoExperienciaDao.expects(once()).method("findAllSelect").with(eq(empresaId), eq(false)).will(returnValue(periodoExperiencias));
		assertEquals(periodoExperiencias, periodoExperienciaManager.findAllSelect(empresaId, false));
	}
}
