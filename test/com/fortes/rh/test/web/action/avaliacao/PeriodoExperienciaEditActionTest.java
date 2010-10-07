package com.fortes.rh.test.web.action.avaliacao;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.avaliacao.PeriodoExperienciaEditAction;

public class PeriodoExperienciaEditActionTest extends MockObjectTestCase
{
	private PeriodoExperienciaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(PeriodoExperienciaManager.class);
		action = new PeriodoExperienciaEditAction();
		action.setPeriodoExperienciaManager((PeriodoExperienciaManager) manager.proxy());

		action.setPeriodoExperiencia(new PeriodoExperiencia());
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
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<PeriodoExperiencia>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getPeriodoExperiencias());
	}

	public void testDelete() throws Exception
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		action.setPeriodoExperiencia(periodoExperiencia);

		manager.expects(once()).method("remove");
		assertEquals(action.delete(), "success");
	}
	
	public void testInsert() throws Exception
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		action.setPeriodoExperiencia(periodoExperiencia);

		manager.expects(once()).method("save").with(eq(periodoExperiencia)).will(returnValue(periodoExperiencia));

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		action.setPeriodoExperiencia(periodoExperiencia);

		manager.expects(once()).method("update").with(eq(periodoExperiencia)).isVoid();

		assertEquals("success", action.update());
	}
	
	public void testPrepareInsert() throws Exception
	{
		assertEquals("success", action.prepareInsert());
	}
	
	public void testPrepareUpdate() throws Exception
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		action.setPeriodoExperiencia(periodoExperiencia);
		manager.expects(once()).method("findById").will(returnValue(periodoExperiencia));
		assertEquals("success", action.prepareUpdate());
	}

	public void testGetSet() throws Exception
	{
		action.setPeriodoExperiencia(null);

		assertNotNull(action.getPeriodoExperiencia());
		assertTrue(action.getPeriodoExperiencia() instanceof PeriodoExperiencia);
	}
}
