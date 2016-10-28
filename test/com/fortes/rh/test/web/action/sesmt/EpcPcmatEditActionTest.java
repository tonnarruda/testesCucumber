package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.EpcPcmatManager;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.EpcPcmat;
import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpcPcmatFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.web.action.sesmt.EpcPcmatEditAction;

public class EpcPcmatEditActionTest extends MockObjectTestCase
{
	private EpcPcmatEditAction action;
	private Mock manager;
	private Mock epcManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(EpcPcmatManager.class);
		action = new EpcPcmatEditAction();
		action.setEpcPcmatManager((EpcPcmatManager) manager.proxy());

		epcManager = new Mock(EpcManager.class);
		action.setEpcManager((EpcManager) epcManager.proxy());
		
		action.setEpcPcmat(new EpcPcmat());
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
		assertNotNull(action.getEpcPcmats());
	}

	public void testDelete() throws Exception
	{
		EpcPcmat epcPcmat = EpcPcmatFactory.getEntity(1L);
		action.setEpcPcmat(epcPcmat);
		
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findByPcmat").with(eq(pcmat.getId())).will(returnValue(new ArrayList<EpiPcmat>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		EpcPcmat epcPcmat = EpcPcmatFactory.getEntity(1L);
		action.setEpcPcmat(epcPcmat);
		
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);
		
		manager.expects(once()).method("findByPcmat").with(eq(pcmat.getId())).will(returnValue(new ArrayList<EpiPcmat>()));
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		EpcPcmat epcPcmat = EpcPcmatFactory.getEntity(1L);
		action.setEpcPcmat(epcPcmat);

		manager.expects(once()).method("save").with(eq(epcPcmat)).will(returnValue(epcPcmat));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		epcManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Epc>()));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		EpcPcmat epcPcmat = EpcPcmatFactory.getEntity(1L);
		action.setEpcPcmat(epcPcmat);

		manager.expects(once()).method("update").with(eq(epcPcmat)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		EpcPcmat epcPcmat = EpcPcmatFactory.getEntity(1L);
		action.setEpcPcmat(epcPcmat);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(epcPcmat.getId())).will(returnValue(epcPcmat));
		epcManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<Epc>()));
		
		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setEpcPcmat(null);

		assertNotNull(action.getEpcPcmat());
		assertTrue(action.getEpcPcmat() instanceof EpcPcmat);
	}
}
