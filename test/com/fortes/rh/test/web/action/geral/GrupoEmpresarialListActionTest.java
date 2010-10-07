package com.fortes.rh.test.web.action.geral;
/*
package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GrupoEmpresarialManager;
import com.fortes.rh.model.geral.GrupoEmpresarial;
import com.fortes.rh.web.action.geral.GrupoEmpresarialListAction;

public class GrupoEmpresarialListActionTest extends MockObjectTestCase
{
	private GrupoEmpresarialListAction action;
	private Mock manager;

    protected void setUp() throws Exception 
    {    
        super.setUp();
        action = new GrupoEmpresarialListAction();
        manager = new Mock(GrupoEmpresarialManager.class);
        action.setGrupoEmpresarialManager((GrupoEmpresarialManager) manager.proxy());
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

    public void testList() throws Exception
    {
    	GrupoEmpresarial testData = new GrupoEmpresarial();
    	testData.setId(1L);
    	testData.setNome("Empresa 1");
    	testData.setEmpresas(null);
    	
    	GrupoEmpresarial testData2 = new GrupoEmpresarial();
    	testData2.setId(2L);
    	testData2.setNome("Empresa 2");
    	testData2.setEmpresas(null);
    	    	
    	Collection<GrupoEmpresarial> grupos = new ArrayList<GrupoEmpresarial>();
    	grupos.add(testData);
    	grupos.add(testData2);
    	
    	manager.expects(once()).method("findAll").will(returnValue(grupos));
    	
    	assertEquals(action.list(), "success");
    	assertEquals(action.getGrupoEmpresarials(), grupos);
    	manager.verify();
    }
}
*/