package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.AreaVivenciaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.AreaVivencia;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AreaVivenciaFactory;
import com.fortes.rh.web.action.sesmt.AreaVivenciaEditAction;

public class AreaVivenciaEditActionTest extends MockObjectTestCase
{
	private AreaVivenciaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AreaVivenciaManager.class);
		action = new AreaVivenciaEditAction();
		action.setAreaVivenciaManager((AreaVivenciaManager) manager.proxy());

		action.setAreaVivencia(new AreaVivencia());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		mockFindAllSelect();
		
		assertEquals("success", action.list());
		assertNotNull(action.getAreaVivencias());
	}

	public void testDelete() throws Exception
	{
		mockFindAllSelect();
		
		AreaVivencia areaVivencia = AreaVivenciaFactory.getEntity(1L);
		action.setAreaVivencia(areaVivencia);

		manager.expects(once()).method("remove");
		assertEquals("success", action.delete());
	}

	public void testDeleteException() throws Exception
	{
		mockFindAllSelect();
		
		AreaVivencia areaVivencia = AreaVivenciaFactory.getEntity(1L);
		action.setAreaVivencia(areaVivencia);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		assertEquals("success", action.delete());
	}
	
	private void mockFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		String nome = "Área Vivência";
		
		action.setEmpresaSistema(empresa);
		action.setNome(nome);

		manager.expects(once()).method("findAllSelect").with(eq(nome), eq(empresa.getId())).will(returnValue(new ArrayList<AreaVivencia>()));
	}
	
	public void testInsert() throws Exception
	{
		AreaVivencia areaVivencia = AreaVivenciaFactory.getEntity(1L);
		action.setAreaVivencia(areaVivencia);

		manager.expects(once()).method("save").with(eq(areaVivencia)).will(returnValue(areaVivencia));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		AreaVivencia areaVivencia = AreaVivenciaFactory.getEntity(1L);
		action.setAreaVivencia(areaVivencia);

		manager.expects(once()).method("update").with(eq(areaVivencia)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		AreaVivencia areaVivencia = AreaVivenciaFactory.getEntity(1L);
		action.setAreaVivencia(areaVivencia);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findByIdProjection").with(eq(areaVivencia.getId())).will(returnValue(areaVivencia));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setAreaVivencia(null);

		assertNotNull(action.getAreaVivencia());
		assertTrue(action.getAreaVivencia() instanceof AreaVivencia);
	}
}
