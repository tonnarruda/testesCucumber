package com.fortes.rh.test.business.geral;

import org.jmock.Mock;

import com.fortes.rh.business.geral.ConfiguracaoLimiteColaboradorManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;

public class ConfiguracaoLimiteColaboradorManagerTest extends MockObjectTestCaseManager<ConfiguracaoLimiteColaboradorManagerImpl> implements TesteAutomaticoManager
{
	private Mock configuracaoLimiteColaboradorDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        manager = new ConfiguracaoLimiteColaboradorManagerImpl();
        configuracaoLimiteColaboradorDao = new Mock(ConfiguracaoLimiteColaboradorDao.class);
        manager.setDao((ConfiguracaoLimiteColaboradorDao) configuracaoLimiteColaboradorDao.proxy());
    }

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(configuracaoLimiteColaboradorDao);
	}
}
