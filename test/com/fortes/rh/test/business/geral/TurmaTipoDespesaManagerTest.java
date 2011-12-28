package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.TurmaTipoDespesaManagerImpl;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.test.factory.geral.TurmaTipoDespesaFactory;

public class TurmaTipoDespesaManagerTest extends MockObjectTestCase
{
	private TurmaTipoDespesaManagerImpl turmaTipoDespesaManager = new TurmaTipoDespesaManagerImpl();
	private Mock turmaTipoDespesaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        turmaTipoDespesaDao = new Mock(TurmaTipoDespesaDao.class);
        turmaTipoDespesaManager.setDao((TurmaTipoDespesaDao) turmaTipoDespesaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Collection<TurmaTipoDespesa> turmaTipoDespesas = TurmaTipoDespesaFactory.getCollection(1L);

		turmaTipoDespesaDao.expects(once()).method("findAll").will(returnValue(turmaTipoDespesas));
		assertEquals(turmaTipoDespesas, turmaTipoDespesaManager.findAll());
	}
}
