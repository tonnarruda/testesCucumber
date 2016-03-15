package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.TipoEPIListAction;

public class TipoEpiListActionTest extends MockObjectTestCase
{
	private TipoEPIListAction action;
	private Mock manager;

	protected void setUp() throws Exception
    {
        super.setUp();
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        action = new TipoEPIListAction();
        manager = new Mock(TipoEPIManager.class);

        action.setTipoEPIManager((TipoEPIManager) manager.proxy());
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    	manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testDelete() throws Exception
    {
    	TipoEPI tipoEpi = new TipoEPI();
    	tipoEpi.setId(1L);
    	action.setTipoEPI(tipoEpi);

    	action.setMsgAlert("deletando");
    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("remove").with(eq(new Long[]{tipoEpi.getId()}));
    	manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(1));
    	manager.expects(once()).method("find").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<TipoEPI>()));

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionMessages().isEmpty());

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(1));
    	manager.expects(once()).method("find").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<TipoEPI>()));

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionErrors().isEmpty());
    }

    public void testList() throws Exception
    {
    	Collection<TipoEPI> collTipoEpi = new ArrayList<TipoEPI>();

    	manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(collTipoEpi.size()));
    	manager.expects(once()).method("find").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(collTipoEpi));

    	assertEquals("success", action.list());

    	assertEquals(collTipoEpi, action.getTipoEPIs());
    }

    public void testGetSet() throws Exception
    {
    	TipoEPI tipoEPI = null;
    	action.setTipoEPI(tipoEPI);
    	assertTrue(action.getTipoEPI() instanceof TipoEPI);

    	tipoEPI = new TipoEPI();
    	tipoEPI.setId(1L);
    	action.setTipoEPI(tipoEPI);
    	assertEquals(action.getTipoEPI(),tipoEPI);

    	action.setMsgAlert("Fortes");
    	assertEquals(action.getMsgAlert(),"Fortes");
    }
}