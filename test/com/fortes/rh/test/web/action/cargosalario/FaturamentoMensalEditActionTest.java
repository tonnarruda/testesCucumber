package com.fortes.rh.test.web.action.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.FaturamentoMensalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaturamentoMensalFactory;
import com.fortes.rh.web.action.cargosalario.FaturamentoMensalEditAction;

public class FaturamentoMensalEditActionTest
{
	private FaturamentoMensalEditAction action;
	private FaturamentoMensalManager manager;
	private EstabelecimentoManager estabelecimentoManager;

	@Before
	public void setUp() throws Exception
	{
		manager = mock(FaturamentoMensalManager.class);
		action = new FaturamentoMensalEditAction();
		action.setFaturamentoMensalManager(manager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);

		action.setFaturamentoMensal(new FaturamentoMensal());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	@After
	public void tearDown() throws Exception
	{
		manager = null;
		action = null;
	}

	@Test
	public void testList() throws Exception
	{
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		when(manager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<FaturamentoMensal>());
		
		assertEquals("success", action.list());
		assertNotNull(action.getFaturamentoMensals());
	}

	@Test
	public void testDelete() throws Exception
	{
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity(1L);
		action.setFaturamentoMensal(faturamentoMensal);
		
		when(manager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<FaturamentoMensal>());
		
		assertEquals("success", action.delete());
	}

	@Test
	public void testInsert() throws Exception
	{
		action.setDataMesAno("02/2000");
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity(1L);
		action.setFaturamentoMensal(faturamentoMensal);

		when(manager.isExisteNaMesmaDataAndEstabelecimento(faturamentoMensal)).thenReturn(false);

		assertEquals("success", action.insert());
	}

	@Test
	public void testInsertException() throws Exception
	{
		action.setDataMesAno("02/2000");
		
		when(manager.isExisteNaMesmaDataAndEstabelecimento(action.getFaturamentoMensal())).thenReturn(true);
		
		assertEquals("input", action.insert());
	}

	@Test
	public void testUpdate() throws Exception
	{
		FaturamentoMensal faturamentoMensal = FaturamentoMensalFactory.getEntity(1L);
		action.setFaturamentoMensal(faturamentoMensal);

		when(manager.isExisteNaMesmaDataAndEstabelecimento(faturamentoMensal)).thenReturn(false);

		assertEquals("success", action.update());
	}
	
	@Test
	public void testupdateException() throws Exception
	{
		action.setDataMesAno("02/2000");
		
		when(manager.isExisteNaMesmaDataAndEstabelecimento(action.getFaturamentoMensal())).thenReturn(true);
		
		assertEquals("input", action.update());
	}

	@Test
	public void testGetSet() throws Exception
	{
		action.setFaturamentoMensal(null);

		assertNotNull(action.getFaturamentoMensal());
		assertTrue(action.getFaturamentoMensal() instanceof FaturamentoMensal);
	}
}
