package com.fortes.rh.test.web.dwr;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ConfiguracaoPerformanceManager;
import com.fortes.rh.web.dwr.ConfiguracaoPerformanceDWR;

public class ConfiguracaoPerformanceDWRTest extends MockObjectTestCase
{
	private ConfiguracaoPerformanceDWR configuracaoPerformanceDWR;
	private Mock configuracaoPerformanceManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		configuracaoPerformanceDWR = new ConfiguracaoPerformanceDWR();

		configuracaoPerformanceManager = new Mock(ConfiguracaoPerformanceManager.class);

		configuracaoPerformanceDWR.setConfiguracaoPerformanceManager((ConfiguracaoPerformanceManager) configuracaoPerformanceManager.proxy());
	}

	public void testGetConfiguracaoPerformances()
	{
		configuracaoPerformanceManager.expects(once()).method("gravaConfiguracao").with(ANYTHING, ANYTHING, ANYTHING);

		Exception exception = null;
		try {
			configuracaoPerformanceDWR.gravarConfiguracao("1.000.1", new String[]{"1"}, new boolean[]{false});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}

}
