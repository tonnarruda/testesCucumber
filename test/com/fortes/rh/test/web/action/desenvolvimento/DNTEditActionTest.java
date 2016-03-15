package com.fortes.rh.test.web.action.desenvolvimento;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.DNTManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.DntFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.DNTEditAction;

public class DNTEditActionTest extends MockObjectTestCase
{
	private DNTEditAction action;
	private Mock DTNManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new DNTEditAction();
        DTNManager = new Mock(DNTManager.class);
        action.setDNTManager((DNTManager) DTNManager.proxy());
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        DTNManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	DNT dnt = DntFactory.getEntity(1L);
    	dnt.setEmpresa(empresa);
    	action.setDnt(dnt);
    	
    	DTNManager.expects(once()).method("findById").with(eq(dnt.getId())).will(returnValue(dnt));
    	assertEquals("success", action.prepareUpdate());
    }
    
    public void testPrepareUpdateEmpresaErrada() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(2L);
    	DNT dnt = DntFactory.getEntity(1L);
    	dnt.setEmpresa(empresa);
    	action.setDnt(dnt);
    	
    	DTNManager.expects(once()).method("findById").with(eq(dnt.getId())).will(returnValue(dnt));
    	assertEquals("error", action.prepareUpdate());    }
    
    public void testPrepareInsert() throws Exception
    {
    	action.setDnt(null);
    	assertEquals("success", action.prepareInsert());
    	assertNotNull(action.getDnt());
    }
    
    public void testInsert() throws Exception
    {
    	DNT dnt = DntFactory.getEntity(1L);
    	action.setDnt(dnt);
    	DTNManager.expects(once()).method("save").with(eq(dnt));
    	assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	DNT dnt = DntFactory.getEntity(1L);
    	action.setDnt(dnt);

    	DTNManager.expects(once()).method("update").with(eq(dnt));
    	assertEquals("success", action.update());
    }
    
    public void testGets() throws Exception
    {
    	DNT dnt = DntFactory.getEntity(1L);
    	action.setDnt(dnt);
    	assertEquals(dnt, action.getModel());
    	
    	AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
    	action.setAreaFiltro(areaOrganizacional);
    	assertEquals(areaOrganizacional, action.getAreaFiltro());
    	
    	ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
    	action.setColaboradorTurma(colaboradorTurma);
    	assertEquals(colaboradorTurma, action.getColaboradorTurma());
    }

}