package com.fortes.rh.test.web.action.geral;
/*
package com.fortes.rh.test.web.action.geral;

import com.fortes.rh.model.geral.GrupoEmpresarial;
import com.fortes.rh.test.web.action.BaseActionTest;
import com.fortes.rh.web.action.geral.GrupoEmpresarialEditAction;

public class GrupoEmpresarialEditActionTest extends BaseActionTest
{
	private GrupoEmpresarialEditAction action;

    protected void setUp() throws Exception 
    {    
        super.setUp();
        action = (GrupoEmpresarialEditAction) ctx.getBean("grupoEmpresarialEditAction");
    }
    
    protected void tearDown() throws Exception 
    {
        super.tearDown();
        action = null;
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testPrepareInsert() throws Exception
    {
    	String result = action.prepareInsert();
    	Object obj = action.getGrupoEmpresarial();
    	assertEquals(result, "success");
    	assertTrue(obj != null);
    }

    public void testPrepareUpdateException() throws Exception
    {
    	action.prepareUpdate();
    	GrupoEmpresarial grupoEmpresarial = new GrupoEmpresarial();  
    	grupoEmpresarial.setId(Long.valueOf("10000"));
    	action.setGrupoEmpresarial(grupoEmpresarial);
    	
    	try
    	{
    		action.prepareUpdate();
    	} 
    	catch(Exception e)
    	{
    		assertTrue(e != null);
    	}
    }
}
*/