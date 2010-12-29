package com.fortes.rh.test.web.action.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManager;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;
import com.fortes.rh.test.factory.geral.ConfiguracaoRelatorioDinamicoFactory;
import com.fortes.rh.web.action.geral.ConfiguracaoRelatorioDinamicoEditAction;

public class ConfiguracaoRelatorioDinamicoEditActionTest extends MockObjectTestCase
{
	private ConfiguracaoRelatorioDinamicoEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ConfiguracaoRelatorioDinamicoManager.class);
		action = new ConfiguracaoRelatorioDinamicoEditAction();
		action.setConfiguracaoRelatorioDinamicoManager((ConfiguracaoRelatorioDinamicoManager) manager.proxy());

	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}


	public void testUpdate() throws Exception
	{
		ConfiguracaoRelatorioDinamico configuracaoRelatorioDinamico = ConfiguracaoRelatorioDinamicoFactory.getEntity(1L);

		manager.expects(once()).method("update").with(eq(configuracaoRelatorioDinamico)).isVoid();

		assertEquals("success", action.update());
	}
}
