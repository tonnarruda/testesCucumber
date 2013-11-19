package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.MedidaSegurancaManagerImpl;
import com.fortes.rh.dao.sesmt.MedidaSegurancaDao;
import com.fortes.rh.model.sesmt.MedidaSeguranca;
import com.fortes.rh.test.factory.sesmt.MedidaSegurancaFactory;

public class MedidaSegurancaManagerTest extends MockObjectTestCase
{
	private MedidaSegurancaManagerImpl medidaSegurancaManager = new MedidaSegurancaManagerImpl();
	private Mock medidaSegurancaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        medidaSegurancaDao = new Mock(MedidaSegurancaDao.class);
        medidaSegurancaManager.setDao((MedidaSegurancaDao) medidaSegurancaDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<MedidaSeguranca> medidaSegurancas = MedidaSegurancaFactory.getCollection(1L);

		medidaSegurancaDao.expects(once()).method("findAllSelect").with(eq(null), eq(empresaId)).will(returnValue(medidaSegurancas));
		assertEquals(medidaSegurancas, medidaSegurancaManager.findAllSelect(null, empresaId));
	}
}
