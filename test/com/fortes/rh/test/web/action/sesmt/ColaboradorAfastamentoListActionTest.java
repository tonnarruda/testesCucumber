package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ColaboradorAfastamentoFactory;
import com.fortes.rh.web.action.sesmt.ColaboradorAfastamentoListAction;

public class ColaboradorAfastamentoListActionTest extends MockObjectTestCase
{
	private ColaboradorAfastamentoListAction action;
	private Mock manager;
	private Mock afastamentoManager;
	private Mock estabelecimentoManager;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ColaboradorAfastamentoManager.class);
		action = new ColaboradorAfastamentoListAction();
		action.setColaboradorAfastamentoManager((ColaboradorAfastamentoManager) manager.proxy());

		afastamentoManager = mock(AfastamentoManager.class);
		action.setAfastamentoManager((AfastamentoManager)afastamentoManager.proxy());
		estabelecimentoManager = mock (EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager)estabelecimentoManager.proxy());
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals(action.execute(), "success");
	}

	public void testList() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(afastamento);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

		manager.expects(once()).method("getCount").will(returnValue(1));
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<ColaboradorAfastamento>()));
		afastamentoManager.expects(once()).method("findAll");

		assertEquals(action.list(), "success");
		assertNotNull(action.getColaboradorAfastamentos());
	}

	public void testDelete() throws Exception
	{
		ColaboradorAfastamento afastamento = ColaboradorAfastamentoFactory.getEntity(1L);
		action.setColaboradorAfastamento(afastamento);
		manager.expects(once()).method("remove").with(eq(afastamento.getId())).isVoid();

		assertEquals(action.delete(), "success");
	}

	public void testPrepareRelatorio() throws Exception
	{
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		afastamentoManager.expects(once()).method("findAll");
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").will(returnValue(new ArrayList<AreaOrganizacional>()));
		
		assertEquals(action.prepareRelatorio(), "success");
	}

	public void testGetSet() throws Exception
	{
		assertNotNull(action.getColaboradorAfastamento());

		assertNotNull(action.getParametros());

		ColaboradorAfastamento afastamento = new ColaboradorAfastamento();
		action.setColaboradorAfastamento(afastamento);
		assertTrue(action.getColaboradorAfastamento() instanceof ColaboradorAfastamento);

		action.setNomeBusca("João");
		action.getNomeBusca();
	}
}