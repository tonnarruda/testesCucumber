package com.fortes.rh.test.business.cargosalario;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaturamentoMensalManagerImpl;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.test.factory.cargosalario.FaturamentoMensalFactory;

public class FaturamentoMensalManagerTest extends MockObjectTestCase
{
	private FaturamentoMensalManagerImpl faturamentoMensalManager = new FaturamentoMensalManagerImpl();
	private Mock faturamentoMensalDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        faturamentoMensalDao = new Mock(FaturamentoMensalDao.class);
        faturamentoMensalManager.setDao((FaturamentoMensalDao) faturamentoMensalDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<FaturamentoMensal> faturamentoMensals = FaturamentoMensalFactory.getCollection(1L);

		faturamentoMensalDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(faturamentoMensals));
		assertEquals(faturamentoMensals, faturamentoMensalManager.findAllSelect(empresaId));
	}
}
