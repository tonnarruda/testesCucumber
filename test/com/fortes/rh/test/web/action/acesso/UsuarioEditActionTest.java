package com.fortes.rh.test.web.action.acesso;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.web.action.acesso.UsuarioEditAction;

public class UsuarioEditActionTest extends MockObjectTestCase
{
	private UsuarioEditAction action;
	private Mock manager;
	private Mock empresaManager;
	private Mock colaboradorManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new UsuarioEditAction();
        manager = new Mock(UsuarioManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        action.setUsuarioManager((UsuarioManager) manager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
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

    public void testPrepareRecuperaSenha() throws Exception
    {
    	Empresa empresa1 = new Empresa();
    	empresa1.setId(1L);
    	empresa1.setCodigoAC("1");

    	Empresa empresa2 = new Empresa();
    	empresa2.setId(2L);
    	empresa2.setCodigoAC("2");

    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa1);
    	empresas.add(empresa2);

    	empresaManager.expects(once()).method("findToList").will(returnValue(empresas));

    	assertEquals(action.prepareRecuperaSenha(), "success");
    	manager.verify();
    }

    public void testRecuperaSenha() throws Exception
    {
    	Empresa empresa1 = new Empresa();
    	empresa1.setId(1L);
    	empresa1.setCodigoAC("1");

    	Empresa empresa2 = new Empresa();
    	empresa2.setId(2L);
    	empresa2.setCodigoAC("2");

    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa1);
    	empresas.add(empresa2);

    	empresaManager.expects(once()).method("findToList").will(returnValue(empresas));
    	colaboradorManager.expects(once()).method("recuperaSenha").with(ANYTHING,ANYTHING).will(returnValue("Mensagem"));

    	assertEquals(action.recuperaSenha(), "success");
    	manager.verify();
    }
}