package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.EpiPcmatManager;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpiPcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.web.action.sesmt.EpiPcmatEditAction;

public class EpiPcmatEditActionTest extends MockObjectTestCase
{
	private EpiPcmatEditAction action;
	private Mock manager;
	private Mock epiManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(EpiPcmatManager.class);
		action = new EpiPcmatEditAction();
		action.setEpiPcmatManager((EpiPcmatManager) manager.proxy());

		epiManager = new Mock(EpiManager.class);
		action.setEpiManager((EpiManager) epiManager.proxy());

		action.setEpiPcmat(new EpiPcmat());
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
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);
		
		manager.expects(once()).method("findByPcmat").with(eq(pcmat.getId())).will(returnValue(new ArrayList<EpiPcmat>()));
		assertEquals("success", action.list());
		assertNotNull(action.getEpiPcmats());
	}

	public void testDelete() throws Exception
	{
		EpiPcmat epiPcmat = EpiPcmatFactory.getEntity(1L);
		action.setEpiPcmat(epiPcmat);
		
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findByPcmat").with(eq(pcmat.getId())).will(returnValue(new ArrayList<EpiPcmat>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		EpiPcmat epiPcmat = EpiPcmatFactory.getEntity(1L);
		action.setEpiPcmat(epiPcmat);
		
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);
		
		manager.expects(once()).method("findByPcmat").with(eq(pcmat.getId())).will(returnValue(new ArrayList<EpiPcmat>()));
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		EpiPcmat epiPcmat = EpiPcmatFactory.getEntity(1L);
		action.setEpiPcmat(epiPcmat);

		manager.expects(once()).method("save").with(eq(epiPcmat)).will(returnValue(epiPcmat));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		epiManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Epi>()));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		EpiPcmat epiPcmat = EpiPcmatFactory.getEntity(1L);
		action.setEpiPcmat(epiPcmat);

		manager.expects(once()).method("update").with(eq(epiPcmat)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		EpiPcmat epiPcmat = EpiPcmatFactory.getEntity(1L);
		action.setEpiPcmat(epiPcmat);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(epiPcmat.getId())).will(returnValue(epiPcmat));
		epiManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Epi>()));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setEpiPcmat(null);

		assertNotNull(action.getEpiPcmat());
		assertTrue(action.getEpiPcmat() instanceof EpiPcmat);
	}
}
