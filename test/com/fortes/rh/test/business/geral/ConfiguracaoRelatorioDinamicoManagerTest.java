package com.fortes.rh.test.business.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoRelatorioDinamicoDao;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;

public class ConfiguracaoRelatorioDinamicoManagerTest extends MockObjectTestCase
{
	private ConfiguracaoRelatorioDinamicoManagerImpl configuracaoRelatorioDinamicoManager = new ConfiguracaoRelatorioDinamicoManagerImpl();
	private Mock configuracaoRelatorioDinamicoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoRelatorioDinamicoDao = new Mock(ConfiguracaoRelatorioDinamicoDao.class);
        configuracaoRelatorioDinamicoManager.setDao((ConfiguracaoRelatorioDinamicoDao) configuracaoRelatorioDinamicoDao.proxy());
    }

	public void testUpdate()
	{
		configuracaoRelatorioDinamicoDao.expects(once()).method("removeByUsuario").with(ANYTHING);
		configuracaoRelatorioDinamicoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		
		configuracaoRelatorioDinamicoManager.update("cargo,nome", "titulo", 1L);
	}
	
	public void testFindCamposByUsuario()
	{
		configuracaoRelatorioDinamicoDao.expects(once()).method("findByUsuario").with(ANYTHING).will(returnValue(null));		
		assertNull(configuracaoRelatorioDinamicoManager.findByUsuario(1L));
		
		ConfiguracaoRelatorioDinamico config = new ConfiguracaoRelatorioDinamico();
		config.setCampos("nome");
		
		configuracaoRelatorioDinamicoDao.expects(once()).method("findByUsuario").with(ANYTHING).will(returnValue(config));
		assertEquals("nome", configuracaoRelatorioDinamicoManager.findByUsuario(1L).getCampos());
	}
}
