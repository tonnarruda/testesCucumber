package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.web.action.captacao.AtitudeEditAction;
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

	public void testPrepareInsert() throws Exception
	{
		Habilidade habilidade = new Habilidade();
		habilidade.setId(1L);
		action.setHabilidade(habilidade);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("findById").will(returnValue(habilidade));
		
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Habilidade habilidade = new Habilidade();
		habilidade.setId(1L);
		action.setHabilidade(habilidade);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("findById").will(returnValue(habilidade));
		
		assertEquals("success", action.prepareUpdate());
	}

	public void testList() throws Exception
	{
		Habilidade Habilidade = new Habilidade();
		Habilidade.setId(1L);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		manager.expects(once()).method("getCount").will(returnValue(new Integer (1)));
		manager.expects(once()).method("findToList").will(returnValue(new ArrayList<Habilidade>()));
		
		assertEquals("success", action.list());
		assertNotNull(action.getHabilidades());
	}

	public void testDelete() throws Exception
	{
		Habilidade Habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(Habilidade);

		manager.expects(once()).method("remove");
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Habilidade Habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(Habilidade);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Habilidade Habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(Habilidade);

		manager.expects(once()).method("save").with(eq(Habilidade)).will(returnValue(Habilidade));

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
		Habilidade Habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(Habilidade);

		manager.expects(once()).method("update").with(eq(Habilidade)).isVoid();

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
		action.setHabilidade(null);

		assertNotNull(action.getHabilidade());
		assertTrue(action.getHabilidade() instanceof Habilidade);
	}
}
