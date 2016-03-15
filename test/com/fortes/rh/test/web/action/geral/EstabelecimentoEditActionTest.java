package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.EstabelecimentoEditAction;

public class EstabelecimentoEditActionTest extends MockObjectTestCase
{
	private EstabelecimentoEditAction action;
	private Mock manager;
	private Mock estadoManager;

	protected void setUp()
	{
		action = new EstabelecimentoEditAction();
		manager = new Mock(EstabelecimentoManager.class);
		estadoManager = new Mock(EstadoManager.class);

		action.setEstabelecimentoManager((EstabelecimentoManager) manager.proxy());
		action.setEstadoManager((EstadoManager) estadoManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

    public void testPrepareInsert() throws Exception
    {
    	Collection<Estado> estados = new ArrayList<Estado>();
    	estadoManager.expects(once()).method("findAll").with(eq(new String[]{"sigla"})).will(returnValue(estados));
    	assertEquals(action.prepareInsert(), "success");
    }

    public void testPrepareUpdate() throws Exception
    {
    	Collection<Estado> estados = new ArrayList<Estado>();
    	estadoManager.expects(once()).method("findAll").with(eq(new String[]{"sigla"})).will(returnValue(estados));

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimento.setId(1L);
    	action.setEstabelecimento(estabelecimento);

    	manager.expects(once()).method("findById").with(eq(estabelecimento.getId())).will(returnValue(estabelecimento));

    	assertEquals(action.prepareUpdate(), "error");

    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	estabelecimento.setEmpresa(empresa);
    	action.setEstabelecimento(estabelecimento);

    	manager.expects(once()).method("findById").with(eq(estabelecimento.getId())).will(returnValue(estabelecimento));
    	manager.expects(once()).method("calculaDV").with(ANYTHING).will(returnValue("01"));
    	assertEquals(action.prepareUpdate(), "success");
    }

    public void testInsert() throws Exception
    {
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimento.setId(1L);
    	action.setEstabelecimento(estabelecimento);

    	manager.expects(once()).method("save").with(eq(estabelecimento));

    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimento.setId(1L);
    	estabelecimento.setEmpresa(empresa);
    	action.setEstabelecimento(estabelecimento);

    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	estabelecimentos.add(estabelecimento);

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));

    	assertEquals(action.update(), "input");

    	estabelecimentos.clear();
    	estabelecimentos.add(estabelecimento);

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("update").with(eq(estabelecimento));
    	assertEquals(action.update(), "success");
    }

    public void testGetsSets() throws Exception
    {
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimento.setId(1L);
    	action.setEstabelecimento(estabelecimento);

    	action.getEstabelecimento();
    	action.setEstabelecimento(null);
    	action.getEstabelecimento();

    	action.getModel();
    }
}