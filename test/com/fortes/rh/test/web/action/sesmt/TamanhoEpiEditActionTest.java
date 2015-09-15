package com.fortes.rh.test.web.action.sesmt;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.TamanhoEPIManager;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.TamanhoEPIEditAction;

public class TamanhoEpiEditActionTest extends MockObjectTestCase
{
	private TamanhoEPIEditAction action;
	private Mock manager;
	TamanhoEPI tamanhoEpi;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(TamanhoEPIManager.class);
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

        action = new TamanhoEPIEditAction();
        action.setTamanhoEPIManager((TamanhoEPIManager) manager.proxy());
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testPrepareInsert() throws Exception
    {
    	prepareTamanhoEpi();
    	manager.expects(once()).method("findById").with(eq(tamanhoEpi.getId())).will(returnValue(tamanhoEpi));
    	assertEquals(action.prepareInsert(), "success");
    }

    public void testInsert() throws Exception
    {
    	prepareTamanhoEpi();
    	manager.expects(once()).method("save").with(eq(tamanhoEpi));
    	assertEquals(action.insert(), "success");
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	prepareTamanhoEpi();
    	manager.expects(once()).method("findById").with(eq(tamanhoEpi.getId())).will(returnValue(tamanhoEpi));
    	assertEquals("success", action.prepareUpdate());
     }

    public void testUpdate() throws Exception
    {
    	prepareTamanhoEpi();
    	manager.expects(once()).method("update").with(eq(tamanhoEpi));
    	assertEquals("success", action.update());
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    private TamanhoEPI prepareTamanhoEpi() {
    	this.tamanhoEpi = new TamanhoEPI();
    	this.tamanhoEpi.setId(1L);
    	action.setTamanhoEPI(this.tamanhoEpi);
    	return this.tamanhoEpi;
    }
}
