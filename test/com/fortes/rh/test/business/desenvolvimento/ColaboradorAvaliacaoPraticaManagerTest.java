package com.fortes.rh.test.business.desenvolvimento;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;

public class ColaboradorAvaliacaoPraticaManagerTest extends MockObjectTestCase
{
	private ColaboradorAvaliacaoPraticaManagerImpl colaboradorAvaliacaoPraticaManager = new ColaboradorAvaliacaoPraticaManagerImpl();
	private Mock colaboradorAvaliacaoPraticaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorAvaliacaoPraticaDao = new Mock(ColaboradorAvaliacaoPraticaDao.class);
        colaboradorAvaliacaoPraticaManager.setDao((ColaboradorAvaliacaoPraticaDao) colaboradorAvaliacaoPraticaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = ColaboradorAvaliacaoPraticaFactory.getCollection(1L);

		colaboradorAvaliacaoPraticaDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(colaboradorAvaliacaoPraticas));
		assertEquals(colaboradorAvaliacaoPraticas, colaboradorAvaliacaoPraticaManager.findAllSelect(empresaId));
	}
}
