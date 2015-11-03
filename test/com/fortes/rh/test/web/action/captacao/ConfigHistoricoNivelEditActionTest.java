package com.fortes.rh.test.web.action.captacao;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.ConfigHistoricoNivelManager;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;
import com.fortes.rh.web.action.captacao.ConfigHistoricoNivelEditAction;

public class ConfigHistoricoNivelEditActionTest extends MockObjectTestCase
{
	private ConfigHistoricoNivelEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ConfigHistoricoNivelManager.class);
		action = new ConfigHistoricoNivelEditAction();
		action.setConfigHistoricoNivelManager((ConfigHistoricoNivelManager) manager.proxy());

		action.setConfigHistoricoNivel(new ConfigHistoricoNivel());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testInsert() throws Exception
	{
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity(1L);
		action.setConfigHistoricoNivel(configHistoricoNivel);

		manager.expects(once()).method("save").with(eq(configHistoricoNivel)).will(returnValue(configHistoricoNivel));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity(1L);
		action.setConfigHistoricoNivel(configHistoricoNivel);

		manager.expects(once()).method("update").with(eq(configHistoricoNivel)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity(1L);
		action.setConfigHistoricoNivel(configHistoricoNivel);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(configHistoricoNivel.getId())).will(returnValue(configHistoricoNivel));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setConfigHistoricoNivel(null);

		assertNotNull(action.getConfigHistoricoNivel());
		assertTrue(action.getConfigHistoricoNivel() instanceof ConfigHistoricoNivel);
	}
}
