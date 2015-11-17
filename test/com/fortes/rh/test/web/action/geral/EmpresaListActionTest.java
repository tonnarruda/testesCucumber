package com.fortes.rh.test.web.action.geral;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.EmpresaListAction;

public class EmpresaListActionTest extends MockObjectTestCase
{
	private EmpresaListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new EmpresaListAction();
        manager = new Mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) manager.proxy());
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

    public void testList() throws Exception
    {
    	Empresa empresa1 = EmpresaFactory.getEmpresa(1L);
    	Empresa empresa2 = EmpresaFactory.getEmpresa(2L);

    	Collection<Empresa> empresas = Arrays.asList(empresa1, empresa2);

    	manager.expects(once()).method("findToList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(empresas));

		assertEquals("success", action.list());

    	manager.verify();
    }

    public void testDeleteEmpresaLogada() throws Exception
    {
    	Empresa fortes = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresa(fortes);

    	action.setEmpresaSistema(fortes);
    	
    	assertEquals("success", action.delete());
    	assertEquals("Não é possível excluir a empresa cujo você esta logado.", action.getActionWarnings().toArray()[0]);
    }
    
    public void testDeleteEmpresaLogadoEmOutra() throws Exception
    {
    	Empresa fortes = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(fortes);

    	Empresa ente = EmpresaFactory.getEmpresa(2L);
    	action.setEmpresa(ente);
    	
    	action.setEmpresa(fortes);
    	action.setEmpresaSistema(ente);
    	
    	manager.expects(once()).method("findByIdProjection").with(eq(fortes.getId())).will(returnValue(fortes));
    	manager.expects(once()).method("removeEmpresa").with(eq(fortes));
    	
    	assertEquals("success", action.delete());
    	assertEquals("Empresa excluída com sucesso.", action.getActionSuccess().toArray()[0]);
    }

    public void testGets() throws Exception
    {
    	action.getEmpresa();
    	action.getEmpresas();
    }
}
