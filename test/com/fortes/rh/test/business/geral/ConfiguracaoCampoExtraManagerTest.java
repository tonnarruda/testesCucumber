package com.fortes.rh.test.business.geral;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;

import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;

public class ConfiguracaoCampoExtraManagerTest extends MockObjectTestCaseManager<ConfiguracaoCampoExtraManagerImpl> implements TesteAutomaticoManager
{
	private Mock configuracaoCampoExtraDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        manager = new ConfiguracaoCampoExtraManagerImpl();
        configuracaoCampoExtraDao = new Mock(ConfiguracaoCampoExtraDao.class);
        manager.setDao((ConfiguracaoCampoExtraDao) configuracaoCampoExtraDao.proxy());
    }

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(configuracaoCampoExtraDao);
	}
	
	public void testAtualizaTodasTrue() 
	{
		Collection<ConfiguracaoCampoExtra> configuracaoesCamposExtras =  Arrays.asList(new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra(), new ConfiguracaoCampoExtra());
		configuracaoCampoExtraDao.expects(once()).method("findDistinct").withAnyArguments().will(returnValue(configuracaoesCamposExtras));
		assertTrue(manager.atualizaTodas());
	}
	
	public void testAtualizaTodasFalse() 
	{
		Collection<ConfiguracaoCampoExtra> configuracaoesCamposExtras =  Arrays.asList();
		configuracaoCampoExtraDao.expects(once()).method("findDistinct").withAnyArguments().will(returnValue(configuracaoesCamposExtras));
		assertFalse(manager.atualizaTodas());
	}
}
