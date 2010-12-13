package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.captacao.AtitudeEditAction;

public class AtitudeEditActionTest extends MockObjectTestCase
{
	private AtitudeEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AtitudeManager.class);
		action = new AtitudeEditAction();
		action.setAtitudeManager((AtitudeManager) manager.proxy());

		action.setAtitude(new Atitude());
	}

	public void testPrepareInsert() throws Exception
	{
		Atitude Atitude = new Atitude();
		Atitude.setId(1L);
		action.setAtitude(Atitude);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("findById").will(returnValue(Atitude));
		
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Atitude Atitude = new Atitude();
		Atitude.setId(1L);
		action.setAtitude(Atitude);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("findById").will(returnValue(Atitude));
		
		assertEquals("success", action.prepareUpdate());
	}
	
	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		Atitude atitude = new Atitude();
		atitude.setId(1L);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("getCount").will(returnValue(new Integer (1)));
		manager.expects(once()).method("findToList").will(returnValue(new ArrayList<Atitude>()));
		
		assertEquals("success", action.list());
		assertNotNull(action.getAtitudes());
	}

	public void testDelete() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		manager.expects(once()).method("remove");
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		manager.expects(once()).method("save").with(eq(atitude)).will(returnValue(atitude));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		Exception exception = null;
		
    	try
		{
    		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    		action.insert();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testUpdate() throws Exception
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		action.setAtitude(atitude);

		manager.expects(once()).method("update").with(eq(atitude)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Exception exception = null;
		
    	try
		{
    		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    		action.update();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
		
	}

	public void testGetSet() throws Exception
	{
		action.setAtitude(null);

		assertNotNull(action.getAtitude());
		assertTrue(action.getAtitude() instanceof Atitude);
	}
}
