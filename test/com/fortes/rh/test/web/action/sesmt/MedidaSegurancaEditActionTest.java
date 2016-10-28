package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.MedidaSegurancaManager;
import com.fortes.rh.model.sesmt.MedidaSeguranca;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.MedidaSegurancaFactory;
import com.fortes.rh.web.action.sesmt.MedidaSegurancaEditAction;

public class MedidaSegurancaEditActionTest extends MockObjectTestCase
{
	private MedidaSegurancaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(MedidaSegurancaManager.class);
		action = new MedidaSegurancaEditAction();
		action.setMedidaSegurancaManager((MedidaSegurancaManager) manager.proxy());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

		action.setMedidaSeguranca(new MedidaSeguranca());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<MedidaSeguranca>()));
		assertEquals("success", action.list());
		assertNotNull(action.getMedidasSeguranca());
	}

	public void testDelete() throws Exception
	{
		MedidaSeguranca medidaSeguranca = MedidaSegurancaFactory.getEntity(1L);
		action.setMedidaSeguranca(medidaSeguranca);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<MedidaSeguranca>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		MedidaSeguranca medidaSeguranca = MedidaSegurancaFactory.getEntity(1L);
		action.setMedidaSeguranca(medidaSeguranca);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<MedidaSeguranca>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		MedidaSeguranca medidaSeguranca = MedidaSegurancaFactory.getEntity(1L);
		action.setMedidaSeguranca(medidaSeguranca);

		manager.expects(once()).method("save").with(eq(medidaSeguranca)).will(returnValue(medidaSeguranca));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		MedidaSeguranca medidaSeguranca = MedidaSegurancaFactory.getEntity(1L);
		action.setMedidaSeguranca(medidaSeguranca);

		manager.expects(once()).method("update").with(eq(medidaSeguranca)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		MedidaSeguranca medidaSeguranca = MedidaSegurancaFactory.getEntity(1L);
		action.setMedidaSeguranca(medidaSeguranca);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(medidaSeguranca.getId())).will(returnValue(medidaSeguranca));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setMedidaSeguranca(null);

		assertNotNull(action.getMedidaSeguranca());
		assertTrue(action.getMedidaSeguranca() instanceof MedidaSeguranca);
	}
}
