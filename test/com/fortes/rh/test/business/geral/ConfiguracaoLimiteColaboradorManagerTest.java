package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ConfiguracaoLimiteColaboradorManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.test.factory.geral.ConfiguracaoLimiteColaboradorFactory;

public class ConfiguracaoLimiteColaboradorManagerTest extends MockObjectTestCase
{
	private ConfiguracaoLimiteColaboradorManagerImpl configuracaoLimiteColaboradorManager = new ConfiguracaoLimiteColaboradorManagerImpl();
	private Mock configuracaoLimiteColaboradorDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoLimiteColaboradorDao = new Mock(ConfiguracaoLimiteColaboradorDao.class);
        configuracaoLimiteColaboradorManager.setDao((ConfiguracaoLimiteColaboradorDao) configuracaoLimiteColaboradorDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<ConfiguracaoLimiteColaborador> configuracaoLimiteColaboradors = ConfiguracaoLimiteColaboradorFactory.getCollection(1L);

		configuracaoLimiteColaboradorDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(configuracaoLimiteColaboradors));
		assertTrue(false);
	}
}
