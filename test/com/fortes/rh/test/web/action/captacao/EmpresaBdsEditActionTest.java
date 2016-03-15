package com.fortes.rh.test.web.action.captacao;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.EmpresaBdsManager;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaBdsFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.EmpresaBdsEditAction;

public class EmpresaBdsEditActionTest extends MockObjectTestCase
{
	private EmpresaBdsEditAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new EmpresaBdsEditAction();
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

    public void testPrepareInsert() throws Exception
    {
    	EmpresaBds EmpresaBds = EmpresaBdsFactory.getEntity();
    	EmpresaBds.setId(1L);

    	action.setEmpresaBds(EmpresaBds);

    	manager.expects(once()).method("findByIdProjection").with(eq(EmpresaBds.getId())).will(returnValue(EmpresaBds));

    	assertEquals(action.prepareInsert(), "success");
    }

    public void testPrepareUpdate() throws Exception
    {
    	EmpresaBds EmpresaBds = EmpresaBdsFactory.getEntity();
    	EmpresaBds.setId(1L);

    	action.setEmpresaBds(EmpresaBds);

    	manager.expects(once()).method("findByIdProjection").with(eq(EmpresaBds.getId())).will(returnValue(EmpresaBds));
    	assertEquals(action.prepareUpdate(), "success");
    }

    public void testInsert() throws Exception
    {
    	EmpresaBds EmpresaBds = EmpresaBdsFactory.getEntity();
    	EmpresaBds.setId(1L);

    	action.setEmpresaBds(EmpresaBds);

    	manager.expects(once()).method("save").with(eq(EmpresaBds));

    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	EmpresaBds EmpresaBds = EmpresaBdsFactory.getEntity();
    	EmpresaBds.setId(1L);

    	action.setEmpresaBds(EmpresaBds);

    	manager.expects(once()).method("update").with(eq(EmpresaBds));

    	assertEquals(action.update(), "success");
    }

    public void testGets() throws Exception
    {
    	@SuppressWarnings("unused")
		EmpresaBds EmpresaBds = action.getEmpresaBds();
    	@SuppressWarnings("unused")
		Object object = action.getModel();
    }

}