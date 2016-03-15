package com.fortes.rh.test.web.action.captacao;

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
import com.fortes.rh.web.action.captacao.EtapaSeletivaEditAction;

public class EtapaSeletivaEditActionTest extends MockObjectTestCase
{
	private EtapaSeletivaEditAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new EtapaSeletivaEditAction();
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

    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setEmpresa(empresa);

    	action.setEtapaSeletiva(etapaSeletiva);

    	manager.expects(once()).method("findByEtapaSeletivaId").with(eq(etapaSeletiva.getId()),eq(empresa.getId())).will(returnValue(etapaSeletiva));
    	manager.expects(once()).method("sugerirOrdem").with(eq(empresa.getId())).will(returnValue(1));

    	assertEquals(action.prepareInsert(), "success");
    }

    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setEmpresa(empresa);

    	action.setEtapaSeletiva(etapaSeletiva);

    	manager.expects(once()).method("findByEtapaSeletivaId").with(eq(etapaSeletiva.getId()),eq(empresa.getId())).will(returnValue(etapaSeletiva));

    	assertEquals(action.prepareUpdate(), "success");
    }

    public void testInsert() throws Exception
    {
    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);

    	action.setEtapaSeletiva(etapaSeletiva);

    	manager.expects(once()).method("save").with(eq(etapaSeletiva));

    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);

    	action.setEtapaSeletiva(etapaSeletiva);

    	manager.expects(once()).method("update").with(eq(etapaSeletiva),ANYTHING);

    	assertEquals(action.update(), "success");
    }

    public void testGets() throws Exception
    {
    	@SuppressWarnings("unused")
		EtapaSeletiva etapaSeletiva = action.getEtapaSeletiva();
    	@SuppressWarnings("unused")
		Object object = action.getModel();
    }

}