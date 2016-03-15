package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.EtapaSeletivaListAction;

public class EtapaSeletivaListActionTest extends MockObjectTestCase
{
	private EtapaSeletivaListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new EtapaSeletivaListAction();
        manager = new Mock(EtapaSeletivaManager.class);
        action.setEtapaSeletivaManager((EtapaSeletivaManager) manager.proxy());
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
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva es1 = EtapaSeletivaFactory.getEntity();
    	es1.setId(1L);
    	es1.setEmpresa(empresa);

    	EtapaSeletiva es2 = EtapaSeletivaFactory.getEntity();
    	es2.setId(2L);
    	es2.setEmpresa(empresa);

    	Collection<EtapaSeletiva> etapaSeletivas = new ArrayList<EtapaSeletiva>();
    	etapaSeletivas.add(es1);
    	etapaSeletivas.add(es2);

    	manager.expects(once()).method("getCount").with(ANYTHING).will(returnValue(etapaSeletivas.size()));
    	manager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(etapaSeletivas));

    	assertEquals(action.list(), "success");
    	manager.verify();
    }

    public void testDelete() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva es1 = EtapaSeletivaFactory.getEntity();
    	es1.setId(1L);
    	es1.setEmpresa(empresa);

    	action.setEtapaSeletiva(es1);

    	manager.expects(once()).method("remove").with(eq(es1),ANYTHING);

    	assertEquals(action.delete(), "success");
    	manager.verify();
    }

    @SuppressWarnings("unchecked")
	public void testGets() throws Exception
    {
    	@SuppressWarnings("unused")
		Collection<EtapaSeletiva> etapaSeletivas = action.getEtapaSeletivas();
    	@SuppressWarnings("unused")
		EtapaSeletiva etapaSeletiva = action.getEtapaSeletiva();
    }
}