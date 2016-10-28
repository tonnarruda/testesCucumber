package com.fortes.rh.test.web.action.geral;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;
import com.fortes.rh.web.action.geral.ConfiguracaoCampoExtraEditAction;

public class ConfiguracaoCampoExtraEditActionTest extends MockObjectTestCase
{
	private ConfiguracaoCampoExtraEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ConfiguracaoCampoExtraManager.class);
		action = new ConfiguracaoCampoExtraEditAction();
		action.setConfiguracaoCampoExtraManager((ConfiguracaoCampoExtraManager) manager.proxy());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testUpdate() throws Exception
	{
		ConfiguracaoCampoExtra configuracaoCampoExtra = ConfiguracaoCampoExtraFactory.getEntity(1L);

		manager.expects(once()).method("update").with(eq(configuracaoCampoExtra)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		ConfiguracaoCampoExtra configuracaoCampoExtra = ConfiguracaoCampoExtraFactory.getEntity(1L);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(configuracaoCampoExtra.getId())).will(returnValue(configuracaoCampoExtra));

		assertEquals("input", action.update());
	}
}
