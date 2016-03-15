package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.EmpresaBdsManager;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaBdsFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.EmpresaBdsListAction;

public class EmpresaBdsListActionTest extends MockObjectTestCase
{
	private EmpresaBdsListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new EmpresaBdsListAction();
        manager = new Mock(EmpresaBdsManager.class);
        action.setEmpresaBdsManager((EmpresaBdsManager) manager.proxy());
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

    	EmpresaBds es1 = EmpresaBdsFactory.getEntity();
    	es1.setId(1L);
    	es1.setEmpresa(empresa);

    	EmpresaBds es2 = EmpresaBdsFactory.getEntity();
    	es2.setId(2L);
    	es2.setEmpresa(empresa);

    	Collection<EmpresaBds> empresaBdss = new ArrayList<EmpresaBds>();
    	empresaBdss.add(es1);
    	empresaBdss.add(es2);

    	manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(empresaBdss.size()));
    	manager.expects(once()).method("find").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(empresaBdss));

    	assertEquals("success", action.list());
    	assertEquals(2, action.getTotalSize());
    	assertEquals(empresaBdss, action.getEmpresaBdss());
    }

    public void testDelete() throws Exception
    {
    	EmpresaBds es1 = EmpresaBdsFactory.getEntity();
    	es1.setId(1L);

    	action.setEmpresaBds(es1);

    	manager.expects(once()).method("remove").with(ANYTHING);
    	manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(1));
    	manager.expects(once()).method("find").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(new ArrayList<EmpresaBds>()));

    	assertEquals("success",action.delete());
    	assertFalse(action.getActionMessages().isEmpty());
    }

    public void testDeleteException() throws Exception
    {
    	action.setEmpresaBds(null);

    	try
		{
    		manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(1));
    		manager.expects(once()).method("find").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(new ArrayList<EmpresaBds>()));
    		action.delete();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		assertFalse(action.getActionErrors().isEmpty());
    }

    @SuppressWarnings("unchecked")
	public void testGets() throws Exception
    {
    	@SuppressWarnings("unused")
		Collection<EmpresaBds> EmpresaBdss = action.getEmpresaBdss();
    	@SuppressWarnings("unused")
		EmpresaBds EmpresaBds = action.getEmpresaBds();
    }
}