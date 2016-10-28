package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.MotivoSolicitacaoEpiManager;
import com.fortes.rh.model.sesmt.MotivoSolicitacaoEpi;
import com.fortes.rh.test.factory.sesmt.MotivoSolicitacaoEpiFactory;
import com.fortes.rh.web.action.sesmt.MotivoSolicitacaoEpiEditAction;

public class MotivoSolicitacaoEpiEditActionTest extends MockObjectTestCase
{
	private MotivoSolicitacaoEpiEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(MotivoSolicitacaoEpiManager.class);
		action = new MotivoSolicitacaoEpiEditAction();
		action.setMotivoSolicitacaoEpiManager((MotivoSolicitacaoEpiManager) manager.proxy());

		action.setMotivoSolicitacaoEpi(new MotivoSolicitacaoEpi());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<MotivoSolicitacaoEpi>()));
		assertEquals("success", action.list());
		assertNotNull(action.getMotivoSolicitacaoEpis());
	}

	public void testDelete() throws Exception
	{
		MotivoSolicitacaoEpi motivoSolicitacaoEpi = MotivoSolicitacaoEpiFactory.getEntity(1L);
		action.setMotivoSolicitacaoEpi(motivoSolicitacaoEpi);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<MotivoSolicitacaoEpi>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		MotivoSolicitacaoEpi motivoSolicitacaoEpi = MotivoSolicitacaoEpiFactory.getEntity(1L);
		action.setMotivoSolicitacaoEpi(motivoSolicitacaoEpi);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findAll").will(returnValue(new ArrayList<MotivoSolicitacaoEpi>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		MotivoSolicitacaoEpi motivoSolicitacaoEpi = MotivoSolicitacaoEpiFactory.getEntity(1L);
		action.setMotivoSolicitacaoEpi(motivoSolicitacaoEpi);

		manager.expects(once()).method("save").with(eq(motivoSolicitacaoEpi)).will(returnValue(motivoSolicitacaoEpi));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		MotivoSolicitacaoEpi motivoSolicitacaoEpi = MotivoSolicitacaoEpiFactory.getEntity(1L);
		action.setMotivoSolicitacaoEpi(motivoSolicitacaoEpi);

		manager.expects(once()).method("update").with(eq(motivoSolicitacaoEpi)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		MotivoSolicitacaoEpi motivoSolicitacaoEpi = MotivoSolicitacaoEpiFactory.getEntity(1L);
		action.setMotivoSolicitacaoEpi(motivoSolicitacaoEpi);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(motivoSolicitacaoEpi.getId())).will(returnValue(motivoSolicitacaoEpi));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setMotivoSolicitacaoEpi(null);

		assertNotNull(action.getMotivoSolicitacaoEpi());
		assertTrue(action.getMotivoSolicitacaoEpi() instanceof MotivoSolicitacaoEpi);
	}
}
