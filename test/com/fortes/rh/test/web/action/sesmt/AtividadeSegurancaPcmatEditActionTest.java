package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.AtividadeSegurancaPcmatManager;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AtividadeSegurancaPcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.web.action.sesmt.AtividadeSegurancaPcmatEditAction;

public class AtividadeSegurancaPcmatEditActionTest extends MockObjectTestCase
{
	private AtividadeSegurancaPcmatEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AtividadeSegurancaPcmatManager.class);
		action = new AtividadeSegurancaPcmatEditAction();
		action.setAtividadeSegurancaPcmatManager((AtividadeSegurancaPcmatManager) manager.proxy());

		action.setAtividadeSegurancaPcmat(new AtividadeSegurancaPcmat());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		action.setPcmat(PcmatFactory.getEntity(1L));
		manager.expects(once()).method("findByPcmat").will(returnValue(new ArrayList<AtividadeSegurancaPcmat>()));
		assertEquals("success", action.list());
		assertNotNull(action.getAtividadesSegurancaPcmat());
	}

	public void testDelete() throws Exception
	{
		action.setPcmat(PcmatFactory.getEntity(1L));
		AtividadeSegurancaPcmat atividadeSegurancaPcmat = AtividadeSegurancaPcmatFactory.getEntity(1L);
		action.setAtividadeSegurancaPcmat(atividadeSegurancaPcmat);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findByPcmat").will(returnValue(new ArrayList<AtividadeSegurancaPcmat>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		action.setPcmat(PcmatFactory.getEntity(1L));
		AtividadeSegurancaPcmat atividadeSegurancaPcmat = AtividadeSegurancaPcmatFactory.getEntity(1L);
		action.setAtividadeSegurancaPcmat(atividadeSegurancaPcmat);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findByPcmat").will(returnValue(new ArrayList<AtividadeSegurancaPcmat>()));
		
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		AtividadeSegurancaPcmat atividadeSegurancaPcmat = AtividadeSegurancaPcmatFactory.getEntity(1L);
		action.setAtividadeSegurancaPcmat(atividadeSegurancaPcmat);

		manager.expects(once()).method("save").with(eq(atividadeSegurancaPcmat)).will(returnValue(atividadeSegurancaPcmat));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		AtividadeSegurancaPcmat atividadeSegurancaPcmat = AtividadeSegurancaPcmatFactory.getEntity(1L);
		action.setAtividadeSegurancaPcmat(atividadeSegurancaPcmat);

		manager.expects(once()).method("update").with(eq(atividadeSegurancaPcmat)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		AtividadeSegurancaPcmat atividadeSegurancaPcmat = AtividadeSegurancaPcmatFactory.getEntity(1L);
		action.setAtividadeSegurancaPcmat(atividadeSegurancaPcmat);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(atividadeSegurancaPcmat.getId())).will(returnValue(atividadeSegurancaPcmat));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setAtividadeSegurancaPcmat(null);

		assertNotNull(action.getAtividadeSegurancaPcmat());
		assertTrue(action.getAtividadeSegurancaPcmat() instanceof AtividadeSegurancaPcmat);
	}
}
