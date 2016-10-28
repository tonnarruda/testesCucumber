package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ProvidenciaFactory;
import com.fortes.rh.web.action.geral.ProvidenciaEditAction;

public class ProvidenciaEditActionTest extends MockObjectTestCase
{
	private ProvidenciaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ProvidenciaManager.class);
		action = new ProvidenciaEditAction();
		action.setProvidenciaManager((ProvidenciaManager) manager.proxy());

		action.setProvidencia(new Providencia());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		manager.expects(once()).method("find").will(returnValue(new ArrayList<Providencia>()));
		assertEquals("success", action.list());
		assertNotNull(action.getProvidencias());
	}

	public void testDelete() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Providencia providencia = ProvidenciaFactory.getEntity(1L);
		action.setProvidencia(providencia);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("find").will(returnValue(new ArrayList<Providencia>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Providencia providencia = ProvidenciaFactory.getEntity(1L);
		action.setProvidencia(providencia);
		
		manager.expects(once()).method("find");
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Providencia providencia = ProvidenciaFactory.getEntity(1L);
		action.setProvidencia(providencia);

		manager.expects(once()).method("save").with(eq(providencia)).will(returnValue(providencia));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Providencia providencia = ProvidenciaFactory.getEntity(1L);
		action.setProvidencia(providencia);

		manager.expects(once()).method("update").with(eq(providencia)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Providencia providencia = ProvidenciaFactory.getEntity(1L);
		action.setProvidencia(providencia);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(eq(providencia.getId())).will(returnValue(providencia));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setProvidencia(null);

		assertNotNull(action.getProvidencia());
		assertTrue(action.getProvidencia() instanceof Providencia);
	}
}
