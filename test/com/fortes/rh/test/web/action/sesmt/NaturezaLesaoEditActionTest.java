package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.NaturezaLesaoManager;
import com.fortes.rh.model.sesmt.NaturezaLesao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.NaturezaLesaoFactory;
import com.fortes.rh.web.action.sesmt.NaturezaLesaoEditAction;

public class NaturezaLesaoEditActionTest extends MockObjectTestCase
{
	private NaturezaLesaoEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(NaturezaLesaoManager.class);
		action = new NaturezaLesaoEditAction();
		action.setNaturezaLesaoManager((NaturezaLesaoManager) manager.proxy());

		action.setNaturezaLesao(new NaturezaLesao());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<NaturezaLesao>()));
		assertEquals("success", action.list());
		assertNotNull(action.getNaturezaLesaos());
	}

	public void testDelete() throws Exception
	{
		NaturezaLesao naturezaLesao = NaturezaLesaoFactory.getEntity(1L);
		action.setNaturezaLesao(naturezaLesao);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<NaturezaLesao>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		NaturezaLesao naturezaLesao = NaturezaLesaoFactory.getEntity(1L);
		action.setNaturezaLesao(naturezaLesao);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<NaturezaLesao>()));
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		NaturezaLesao naturezaLesao = NaturezaLesaoFactory.getEntity(1L);
		action.setNaturezaLesao(naturezaLesao);

		manager.expects(once()).method("save").with(eq(naturezaLesao)).will(returnValue(naturezaLesao));

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		NaturezaLesao naturezaLesao = NaturezaLesaoFactory.getEntity(1L);
		action.setNaturezaLesao(naturezaLesao);

		manager.expects(once()).method("update").with(eq(naturezaLesao)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setNaturezaLesao(null);

		assertNotNull(action.getNaturezaLesao());
		assertTrue(action.getNaturezaLesao() instanceof NaturezaLesao);
	}
}
