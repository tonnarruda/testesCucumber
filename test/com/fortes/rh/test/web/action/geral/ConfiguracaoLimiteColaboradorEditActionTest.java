package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.ConfiguracaoLimiteColaboradorManager;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.test.factory.geral.ConfiguracaoLimiteColaboradorFactory;
import com.fortes.rh.web.action.geral.ConfiguracaoLimiteColaboradorEditAction;

public class ConfiguracaoLimiteColaboradorEditActionTest extends MockObjectTestCase
{
	private ConfiguracaoLimiteColaboradorEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ConfiguracaoLimiteColaboradorManager.class);
		action = new ConfiguracaoLimiteColaboradorEditAction();
		action.setConfiguracaoLimiteColaboradorManager((ConfiguracaoLimiteColaboradorManager) manager.proxy());

		action.setConfiguracaoLimiteColaborador(new ConfiguracaoLimiteColaborador());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<ConfiguracaoLimiteColaborador>()));
		assertEquals("success", action.list());
		assertNotNull(action.getConfiguracaoLimiteColaboradors());
	}

	public void testDelete() throws Exception
	{
		ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = ConfiguracaoLimiteColaboradorFactory.getEntity(1L);
		action.setConfiguracaoLimiteColaborador(configuracaoLimiteColaborador);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<ConfiguracaoLimiteColaborador>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = ConfiguracaoLimiteColaboradorFactory.getEntity(1L);
		action.setConfiguracaoLimiteColaborador(configuracaoLimiteColaborador);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = ConfiguracaoLimiteColaboradorFactory.getEntity(1L);
		action.setConfiguracaoLimiteColaborador(configuracaoLimiteColaborador);

		manager.expects(once()).method("save").with(eq(configuracaoLimiteColaborador)).will(returnValue(configuracaoLimiteColaborador));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = ConfiguracaoLimiteColaboradorFactory.getEntity(1L);
		action.setConfiguracaoLimiteColaborador(configuracaoLimiteColaborador);

		manager.expects(once()).method("update").with(eq(configuracaoLimiteColaborador)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = ConfiguracaoLimiteColaboradorFactory.getEntity(1L);
		action.setConfiguracaoLimiteColaborador(configuracaoLimiteColaborador);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(configuracaoLimiteColaborador.getId())).will(returnValue(configuracaoLimiteColaborador));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setConfiguracaoLimiteColaborador(null);

		assertNotNull(action.getConfiguracaoLimiteColaborador());
		assertTrue(action.getConfiguracaoLimiteColaborador() instanceof ConfiguracaoLimiteColaborador);
	}
}
