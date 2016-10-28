package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.sesmt.ObraManager;
import com.fortes.rh.model.dicionario.Estado;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ObraFactory;
import com.fortes.rh.web.action.sesmt.ObraEditAction;

public class ObraEditActionTest extends MockObjectTestCase
{
	private ObraEditAction action;
	private Mock manager;
	private Mock estadoManager;
	private Mock cidadeManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ObraManager.class);
		
		action = new ObraEditAction();
		action.setObraManager((ObraManager) manager.proxy());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		estadoManager = new Mock(EstadoManager.class);
		action.setEstadoManager((EstadoManager) estadoManager.proxy());
		
		cidadeManager = new Mock(CidadeManager.class);
		action.setCidadeManager((CidadeManager) cidadeManager.proxy());
		
		action.setObra(new Obra());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Obra>()));
		assertEquals("success", action.list());
		assertNotNull(action.getObras());
	}

	public void testDelete() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Obra>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);
		
		manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Obra>()));
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);

		manager.expects(once()).method("save").with(eq(obra)).will(returnValue(obra));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		estadoManager.expects(once()).method("findAll").withAnyArguments().will(returnValue(new ArrayList<Estado>()));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);

		manager.expects(once()).method("update").with(eq(obra)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Obra obra = ObraFactory.getEntity(1L);
		action.setObra(obra);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(new Obra()));
		estadoManager.expects(once()).method("findAll").withAnyArguments().will(returnValue(new ArrayList<Estado>()));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setObra(null);

		assertNotNull(action.getObra());
		assertTrue(action.getObra() instanceof Obra);
	}
}
