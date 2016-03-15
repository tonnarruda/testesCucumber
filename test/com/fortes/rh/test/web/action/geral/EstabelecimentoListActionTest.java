package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.EstabelecimentoListAction;

public class EstabelecimentoListActionTest extends MockObjectTestCase
{
	private EstabelecimentoListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new EstabelecimentoListAction();
        manager = new Mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) manager.proxy());
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testList() throws Exception
    {
    	Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
    	estabelecimento1.setId(1L);

    	Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
    	estabelecimento2.setId(2L);

    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	estabelecimentos.add(estabelecimento1);
    	estabelecimentos.add(estabelecimento2);

    	manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(20));
    	manager.expects(once()).method("find").withAnyArguments().will(returnValue(estabelecimentos));

    	assertEquals(action.list(), "success");

    	manager.verify();
    }

    public void testDelete() throws Exception
    {
    	Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
    	estabelecimento1.setId(1L);
    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	action.setEstabelecimento(estabelecimento1);

    	Empresa empresa = action.getEmpresaSistema();
    	empresa.setAcIntegra(true);

    	assertEquals("success",action.delete());
    	assertFalse(action.getActionErrors().isEmpty());

    	empresa.setAcIntegra(false);

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));

    	assertEquals("success",action.delete());
    	assertFalse(action.getActionErrors().isEmpty());

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("remove").with(ANYTHING);

    	assertEquals("success",action.delete());
    	assertFalse(action.getActionMessages().isEmpty());
    }

    public void testGets() throws Exception
    {
    	action.getEstabelecimento();
    	action.getEstabelecimentos();
    }
}
