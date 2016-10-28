package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.TipoDespesaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.TipoDespesaFactory;
import com.fortes.rh.web.action.geral.TipoDespesaEditAction;

public class TipoDespesaEditActionTest extends MockObjectTestCase
{
	private TipoDespesaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(TipoDespesaManager.class);
		action = new TipoDespesaEditAction();
		action.setTipoDespesaManager((TipoDespesaManager) manager.proxy());

		action.setTipoDespesa(new TipoDespesa());
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
		
		manager.expects(once()).method("find").will(returnValue(new ArrayList<TipoDespesa>()));
		assertEquals("success", action.list());
		assertNotNull(action.getTipoDespesas());
	}

	public void testDelete() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity(1L);
		action.setTipoDespesa(tipoDespesa);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("find").will(returnValue(new ArrayList<TipoDespesa>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity(1L);
		action.setTipoDespesa(tipoDespesa);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("find").will(returnValue(new ArrayList<TipoDespesa>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity(1L);
		action.setTipoDespesa(tipoDespesa);

		manager.expects(once()).method("save").with(eq(tipoDespesa)).will(returnValue(tipoDespesa));

		assertEquals("success", action.insert());
	}


	public void testUpdate() throws Exception
	{
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity(1L);
		action.setTipoDespesa(tipoDespesa);

		manager.expects(once()).method("update").with(eq(tipoDespesa)).isVoid();

		assertEquals("success", action.update());
	}


	public void testGetSet() throws Exception
	{
		action.setTipoDespesa(null);

		assertNotNull(action.getTipoDespesa());
		assertTrue(action.getTipoDespesa() instanceof TipoDespesa);
	}
}
