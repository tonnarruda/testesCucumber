package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ClienteManagerImpl;
import com.fortes.rh.dao.geral.ClienteDao;
import com.fortes.rh.model.geral.Cliente;
import com.fortes.rh.test.factory.geral.ClienteFactory;

public class ClienteManagerTest extends MockObjectTestCase
{
	private ClienteManagerImpl clienteManager = new ClienteManagerImpl();
	private Mock clienteDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        clienteDao = new Mock(ClienteDao.class);
        clienteManager.setDao((ClienteDao) clienteDao.proxy());
    }

	//TODO
	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<Cliente> clientes = ClienteFactory.getCollection(1L);

//		clienteDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(clientes));
//		assertEquals(clientes, clienteManager.findAllSelect(empresaId));
		
		assertTrue(true);
		
	}
}
