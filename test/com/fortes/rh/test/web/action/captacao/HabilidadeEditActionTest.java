package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.web.action.captacao.HabilidadeEditAction;

public class HabilidadeEditActionTest extends MockObjectTestCase
{
	private HabilidadeEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(HabilidadeManager.class);
		action = new HabilidadeEditAction();
		action.setHabilidadeManager((HabilidadeManager) manager.proxy());

		action.setHabilidade(new Habilidade());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Habilidade>()));
		assertEquals("success", action.list());
		assertNotNull(action.getHabilidades());
	}

	public void testDelete() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<Habilidade>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);

		manager.expects(once()).method("save").with(eq(habilidade)).will(returnValue(habilidade));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);

		manager.expects(once()).method("update").with(eq(habilidade)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(habilidade.getId())).will(returnValue(habilidade));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setHabilidade(null);

		assertNotNull(action.getHabilidade());
		assertTrue(action.getHabilidade() instanceof Habilidade);
	}
}
