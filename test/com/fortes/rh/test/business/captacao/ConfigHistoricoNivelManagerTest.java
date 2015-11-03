package com.fortes.rh.test.business.captacao;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConfigHistoricoNivelManagerImpl;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;

public class ConfigHistoricoNivelManagerTest extends MockObjectTestCase
{
	private ConfigHistoricoNivelManagerImpl configHistoricoNivelManager = new ConfigHistoricoNivelManagerImpl();
	private Mock configHistoricoNivelDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configHistoricoNivelDao = new Mock(ConfigHistoricoNivelDao.class);
        configHistoricoNivelManager.setDao((ConfigHistoricoNivelDao) configHistoricoNivelDao.proxy());
    }

}
