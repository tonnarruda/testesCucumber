package com.fortes.rh.test.web.action.geral;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManager;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
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

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
        MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}


	public void testUpdate() throws Exception
	{
		manager.expects(once()).method("update").with(ANYTHING, ANYTHING, ANYTHING).isVoid();

		assertEquals("success", action.update());
	}
}
