package com.fortes.rh.test.web.action.avaliacao;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.avaliacao.AvaliacaoPraticaEditAction;

public class AvaliacaoPraticaEditActionTest extends MockObjectTestCase
{
	private AvaliacaoPraticaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AvaliacaoPraticaManager.class);
		action = new AvaliacaoPraticaEditAction();
		action.setAvaliacaoPraticaManager((AvaliacaoPraticaManager) manager.proxy());

		action.setAvaliacaoPratica(new AvaliacaoPratica());
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("getCount").will(returnValue(0));
		manager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoPratica>()));
		assertEquals("success", action.list());
		assertNotNull(action.getAvaliacaoPraticas());
	}

	public void testDelete() throws Exception
	{
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		action.setAvaliacaoPratica(avaliacaoPratica);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("getCount").will(returnValue(0));
		manager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoPratica>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		action.setAvaliacaoPratica(avaliacaoPratica);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("getCount").will(returnValue(0));
		manager.expects(once()).method("find").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoPratica>()));
		
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		action.setAvaliacaoPratica(avaliacaoPratica);

		manager.expects(once()).method("save").with(eq(avaliacaoPratica)).will(returnValue(avaliacaoPratica));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		action.setAvaliacaoPratica(avaliacaoPratica);

		manager.expects(once()).method("update").with(eq(avaliacaoPratica)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		action.setAvaliacaoPratica(avaliacaoPratica);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(avaliacaoPratica.getId())).will(returnValue(avaliacaoPratica));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setAvaliacaoPratica(null);

		assertNotNull(action.getAvaliacaoPratica());
		assertTrue(action.getAvaliacaoPratica() instanceof AvaliacaoPratica);
	}
}
