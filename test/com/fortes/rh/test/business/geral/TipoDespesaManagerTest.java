package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.TipoDespesaManagerImpl;
import com.fortes.rh.dao.geral.TipoDespesaDao;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.test.factory.geral.TipoDespesaFactory;

public class TipoDespesaManagerTest extends MockObjectTestCase
{
	private TipoDespesaManagerImpl tipoDespesaManager = new TipoDespesaManagerImpl();
	private Mock tipoDespesaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        tipoDespesaDao = new Mock(TipoDespesaDao.class);
        tipoDespesaManager.setDao((TipoDespesaDao) tipoDespesaDao.proxy());
    }

	public void testFindAllSelect()
	{
		
		Collection<TipoDespesa> tipoDespesas = TipoDespesaFactory.getCollection(1L);

		tipoDespesaDao.expects(once()).method("findAll").will(returnValue(tipoDespesas));
		assertEquals(tipoDespesas, tipoDespesaManager.findAll());
	}
}
