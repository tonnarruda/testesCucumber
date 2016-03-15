package com.fortes.rh.test.web.action.sesmt;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.TamanhoEPIManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.business.sesmt.TipoTamanhoEPIManager;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.TipoEPIEditAction;

public class TipoEpiEditActionTest extends MockObjectTestCase
{
	private TipoEPIEditAction action;
	private Mock manager;
	private Mock tamanhoEPIManager;
	private Mock tipoTamanhoEPIManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(TipoEPIManager.class);
        tamanhoEPIManager = new Mock(TamanhoEPIManager.class);
        tipoTamanhoEPIManager = new Mock(TipoTamanhoEPIManager.class);
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

        action = new TipoEPIEditAction();
        action.setTipoEPIManager((TipoEPIManager) manager.proxy());
        action.setTamanhoEPIManager((TamanhoEPIManager) tamanhoEPIManager.proxy());
        action.setTipoTamanhoEPIManager((TipoTamanhoEPIManager) tipoTamanhoEPIManager.proxy());
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
        manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testPrepareUpdate() throws Exception
    {
    	TipoEPI tipoEpi = new TipoEPI();
    	tipoEpi.setId(1L);
    	action.setTipoEPI(tipoEpi);

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(tipoEpi));
    	tamanhoEPIManager.expects(once()).method("populaCheckOrderDescricao");
    	assertEquals("success", action.prepareUpdate());

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	assertEquals("error", action.prepareUpdate());
     }

    public void testUpdate() throws Exception
    {
    	TipoEPI tipoEpi = new TipoEPI();
    	tipoEpi.setId(1L);
    	action.setTipoEPI(tipoEpi);

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("update").with(eq(tipoEpi));
    	tipoTamanhoEPIManager.expects(once()).method("salvarTamanhoEPIs").with(ANYTHING, ANYTHING);
    	assertEquals("success", action.update());

    	manager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(false));
    	assertEquals("error", action.update());
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testPrepareInsert() throws Exception
    {
       	TipoEPI tipoEpi = new TipoEPI();
    	tipoEpi.setId(1L);
    	action.setTipoEPI(tipoEpi);

    	manager.expects(once()).method("findById").with(eq(tipoEpi.getId())).will(returnValue(tipoEpi));
    	tamanhoEPIManager.expects(once()).method("populaCheckOrderDescricao");

    	assertEquals(action.prepareInsert(), "success");
    }

    public void testInsert() throws Exception
    {
    	TipoEPI tipoEpi = new TipoEPI();
    	action.setTipoEPI(tipoEpi);

    	manager.expects(once()).method("save").with(eq(tipoEpi));
    	tipoTamanhoEPIManager.expects(once()).method("salvarTamanhoEPIs").with(ANYTHING, ANYTHING);

    	assertEquals(action.insert(), "success");
    	assertEquals(action.getTipoEPI(), tipoEpi);
    }

    public void testGetSet() throws Exception
    {
    	action.setMsgAlert("Fortes");
    	assertEquals(action.getMsgAlert(), "Fortes");

    	action.setTipoEPI(null);

        assertTrue(action.getTipoEPI() instanceof TipoEPI);
    }
}
