package com.fortes.rh.test.web.action.desenvolvimento;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorTurmaEditAction;

public class ColaboradorTurmaEditActionTest extends MockObjectTestCase
{
	private ColaboradorTurmaEditAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new ColaboradorTurmaEditAction();
        manager = new Mock(ColaboradorTurmaManager.class);

        action.setColaboradorTurmaManager((ColaboradorTurmaManager) manager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
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

    public void testUpdate() throws Exception
    {
    	action.setColaboradoresTurmaId(new Long[]{1L});
    	action.setColaboradorTurmaHidden(new String[]{"1","2"});

    	manager.expects(atLeastOnce()).method("saveUpdate").with(ANYTHING,ANYTHING);
    	assertEquals("success", action.update());
    }

    public void testUpdateException() throws Exception
    {
    	action.setColaboradoresTurmaId(new Long[]{1L});
    	action.setColaboradorTurmaHidden(new String[]{"1"});

    	manager.expects(once()).method("saveUpdate").with(ANYTHING,ANYTHING).will(throwException(new Exception()));
    	manager.expects(once()).method("saveUpdate").with(ANYTHING,ANYTHING);
    	assertEquals("input", action.update());
    }
    
    public void testGets() throws Exception
    {
		action.getColaboradorTurma();
    }

}