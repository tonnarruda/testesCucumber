package com.fortes.rh.test.web.action.cargosalario;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaturamentoMensalManager;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaturamentoMensalFactory;
import com.fortes.rh.web.action.cargosalario.FaturamentoMensalEditAction;

public class FaturamentoMensalEditActionTest extends MockObjectTestCase
{
	private FaturamentoMensalEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(FaturamentoMensalManager.class);
		action = new FaturamentoMensalEditAction();
		action.setFaturamentoMensalManager((FaturamentoMensalManager) manager.proxy());

		action.setFaturamentoMensal(new FaturamentoMensal());
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
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<FaturamentoMensal>()));
		assertEquals("success", action.list());
		assertNotNull(action.getFaturamentoMensals());
	}

	public void testDelete() throws Exception
	{
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity(1L);
		action.setFaturamentoMensal(faturamentoMensal);

		manager.expects(once()).method("remove");
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<FaturamentoMensal>()));
		assertEquals("success", action.delete());
	}

	public void testInsert() throws Exception
	{
		action.setDataMesAno("02/2000");
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity(1L);
		action.setFaturamentoMensal(faturamentoMensal);

		manager.expects(once()).method("verifyExists").will(returnValue(false));
		manager.expects(once()).method("save").with(eq(faturamentoMensal)).will(returnValue(faturamentoMensal));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		action.setDataMesAno("02/2000");
		manager.expects(once()).method("verifyExists").will(returnValue(true));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity(1L);
		action.setFaturamentoMensal(faturamentoMensal);

		manager.expects(once()).method("update").with(eq(faturamentoMensal)).isVoid();

		assertEquals("success", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setFaturamentoMensal(null);

		assertNotNull(action.getFaturamentoMensal());
		assertTrue(action.getFaturamentoMensal() instanceof FaturamentoMensal);
	}
}
