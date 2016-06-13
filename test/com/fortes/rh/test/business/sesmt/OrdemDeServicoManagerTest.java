package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.OrdemDeServicoManagerImpl;
import com.fortes.rh.dao.sesmt.OrdemDeServicoDao;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.test.factory.sesmt.OrdemDeServicoFactory;

public class OrdemDeServicoManagerTest extends MockObjectTestCase
{
	private OrdemDeServicoManagerImpl ordemDeServicoManager = new OrdemDeServicoManagerImpl();
	private Mock ordemDeServicoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        ordemDeServicoDao = new Mock(OrdemDeServicoDao.class);
        ordemDeServicoManager.setDao((OrdemDeServicoDao) ordemDeServicoDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<OrdemDeServico> ordemDeServicos = OrdemDeServicoFactory.getCollection(1L);

		ordemDeServicoDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(ordemDeServicos));
		assertEquals(ordemDeServicos, ordemDeServicoManager.findAllSelect(empresaId));
	}
}
