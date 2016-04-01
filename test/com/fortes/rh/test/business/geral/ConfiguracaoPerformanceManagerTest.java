package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;

import com.fortes.rh.business.geral.ConfiguracaoPerformanceManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoPerformanceDao;
import com.fortes.rh.model.geral.ConfiguracaoPerformance;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;

public class ConfiguracaoPerformanceManagerTest extends MockObjectTestCaseManager<ConfiguracaoPerformanceManagerImpl> implements TesteAutomaticoManager
{
	private Mock configuracaoPerformanceDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        manager = new ConfiguracaoPerformanceManagerImpl();
        configuracaoPerformanceDao = new Mock(ConfiguracaoPerformanceDao.class);
        manager.setDao((ConfiguracaoPerformanceDao) configuracaoPerformanceDao.proxy());
    }

	public void testGravaConfiguracao()
	{
		String[] caixas = new String[]{"2","3","1"}; 
		boolean[] caixasStatus = new boolean[]{true, false, true};
		
		configuracaoPerformanceDao.expects(once()).method("removeByUsuario").with(ANYTHING);
		configuracaoPerformanceDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		
		
		Collection<ConfiguracaoPerformance> configuracaoPerformances = manager.gravaConfiguracao(1L, caixas, caixasStatus);
		
		assertEquals(3, configuracaoPerformances.size());
		
		validaCaixa((ConfiguracaoPerformance) configuracaoPerformances.toArray()[0], "2", 0, true);
		validaCaixa((ConfiguracaoPerformance) configuracaoPerformances.toArray()[1], "3", 1, false);
		validaCaixa((ConfiguracaoPerformance) configuracaoPerformances.toArray()[2], "1", 2, true);
	}

	private void validaCaixa(ConfiguracaoPerformance config, String caixa, int ordem, boolean aberta) 
	{
		assertEquals(caixa, config.getCaixa());
		assertEquals(ordem, config.getOrdem());
		assertEquals(aberta, config.isAberta());
	}

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(configuracaoPerformanceDao);
	}
}
