package com.fortes.rh.test.web.action.acesso;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.acesso.UsuarioListAction;

public class UsuarioListActionTest extends MockObjectTestCase
{
	private UsuarioListAction action;
	private Mock manager;
	private Mock empresaManager;
	private Mock usuarioEmpresaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new UsuarioListAction();
        manager = new Mock(UsuarioManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        usuarioEmpresaManager = new Mock(UsuarioEmpresaManager.class);
        action.setUsuarioManager((UsuarioManager) manager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        action.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());
        
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
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
	    montaList();
	    assertEquals(action.list(), "success");
    }

	private void montaList() 
	{
		Usuario jose = UsuarioFactory.getEntity();
	    jose.setNome("José");

	   	Empresa empresa1 = new Empresa();
    	empresa1.setId(1L);
    	empresa1.setCodigoAC("1");

    	Empresa empresa2 = new Empresa();
    	empresa2.setId(2L);
    	empresa2.setCodigoAC("2");

    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	empresas.add(empresa1);
    	empresas.add(empresa2);
    	
    	Collection<Usuario> usuarios = new ArrayList<Usuario>();
    	usuarios.add(jose);
	    
	    empresaManager.expects(once()).method("findToList").will(returnValue(empresas));
	    manager.expects(once()).method("findUsuarios").will(returnValue(usuarios));
	    manager.expects(once()).method("getCountUsuario").will(returnValue(1));
	}

    public void testImprimirUsuariosPerfis() 
    {
    	usuarioEmpresaManager.expects(once()).method("findPerfisEmpresas");
    	
    	assertEquals(action.imprimirUsuariosPerfis(), "success");
	}
    
    public void testDelete() throws Exception 
    {
    	manager.expects(once()).method("removeUsuario");
    	montaList();
    	
    	assertEquals(action.delete(), "success");
    }
    
    public void testDeleteException() throws Exception  
    {
    	manager.expects(once()).method("removeUsuario").will(throwException(new Exception("erro")));;
    	montaList() ;
    	action.delete();
    	
    	assertEquals("Não foi possível excluir este Usuário. Utilize o campo \"Ativo\" para retirar o acesso do usuário ao sistema.",((ArrayList<String>)action.getActionErrors()).get(0));
    }
}