package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.web.action.captacao.NivelCompetenciaEditAction;

public class NivelCompetenciaEditActionTest extends MockObjectTestCase
{
	private NivelCompetenciaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(NivelCompetenciaManager.class);
		action = new NivelCompetenciaEditAction();
		action.setNivelCompetenciaManager((NivelCompetenciaManager) manager.proxy());

		action.setNivelCompetencia(new NivelCompetencia());
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
		
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<NivelCompetencia>()));
		
		assertEquals("success", action.list());
		assertNotNull(action.getNivelCompetencias());
	}

	public void testDelete() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		action.setNivelCompetencia(nivelCompetencia);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<NivelCompetencia>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		action.setNivelCompetencia(nivelCompetencia);
		
		manager.expects(once()).method("remove").with(eq(nivelCompetencia.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<NivelCompetencia>()));

		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		action.setNivelCompetencia(nivelCompetencia);

		manager.expects(once()).method("validaLimite").with(ANYTHING).isVoid();
		manager.expects(once()).method("save").with(eq(nivelCompetencia)).will(returnValue(nivelCompetencia));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		manager.expects(once()).method("validaLimite").with(ANYTHING).isVoid();
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(1L);
		action.setNivelCompetencia(nivelCompetencia);

		manager.expects(once()).method("update").with(eq(nivelCompetencia)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setNivelCompetencia(null);

		assertNotNull(action.getNivelCompetencia());
		assertTrue(action.getNivelCompetencia() instanceof NivelCompetencia);
	}
}
