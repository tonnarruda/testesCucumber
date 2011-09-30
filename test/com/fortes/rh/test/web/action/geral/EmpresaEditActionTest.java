package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.web.action.geral.EmpresaEditAction;
import com.fortes.web.tags.CheckBox;

public class EmpresaEditActionTest extends MockObjectTestCase
{
	private EmpresaEditAction action;
	private Mock manager;
	private Mock parametrosDoSistemaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new EmpresaEditAction();
        manager = new Mock(EmpresaManager.class);
        parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
        action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        action.setEmpresaManager((EmpresaManager) manager.proxy());
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }
    
    public void testExecute() throws Exception
    {
    	assertEquals("success",action.execute() );
    }
    
    public void testSobre() throws Exception
    {
    	parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(new ParametrosDoSistema()));
    	assertEquals("success",action.sobre());
    }
    
    public void testPrepareImportarCadastros()
    {
    	manager.expects(once()).method("findAll").withNoArguments().will(returnValue(new ArrayList<Empresa>()));
    	manager.expects(once()).method("populaCadastrosCheckBox").withNoArguments().will(returnValue(new ArrayList<CheckBox>()));
    	assertEquals("success", action.prepareImportarCadastros());
    }
    
    
}
