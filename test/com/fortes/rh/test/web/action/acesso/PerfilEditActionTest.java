package com.fortes.rh.test.web.action.acesso;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.acesso.PapelManager;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockStringUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.acesso.PerfilEditAction;
import com.opensymphony.xwork.ActionContext;

public class PerfilEditActionTest extends MockObjectTestCase
{
	private PerfilEditAction action;
	private Mock manager;
	private Mock papelManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new PerfilEditAction();
        manager = new Mock(PerfilManager.class);
        action.setPerfilManager((PerfilManager) manager.proxy());
        
        papelManager = mock(PapelManager.class);
        action.setPapelManager((PapelManager) papelManager.proxy());
        
        Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(StringUtil.class, MockStringUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
    	MockSecurityUtil.verifyRole = false;
        Mockit.restoreAllOriginalDefinitions();
    }

    public void testPrepareInsert() throws Exception
    {
    	papelManager.expects(once()).method("getPerfilOrganizado").withAnyArguments();

    	assertEquals("success",action.prepareInsert());
    }
    public void testPrepareUpdate() throws Exception
    {
    	Perfil perfil = new Perfil();
    	perfil.setId(1L);
    	perfil.setNome("Perfil 1");
    	action.setPerfil(perfil);
    	
    	manager.expects(once()).method("findById").with(eq(perfil.getId())).will(returnValue(perfil));
    	
    	manager.expects(once()).method("montaPermissoes").with(eq(perfil));
    	
    	papelManager.expects(once()).method("getPerfilOrganizado").withAnyArguments();
    	
    	assertEquals("success",action.prepareUpdate());
    	assertEquals(perfil,action.getPerfil());
    }
    

    public void testInsert() throws Exception
    {
    	Papel papel1 = new Papel();
    	papel1.setId(1L);
    	Papel papel2 = new Papel();
    	papel1.setId(1L);
    	Collection<Papel> papeis = new ArrayList<Papel>();
    	papeis.add(papel1);
    	papeis.add(papel2);
    	Perfil perfil = new Perfil();
    	perfil.setNome("Perfil 1");
    	
    	action.setPerfil(perfil);
    	
    	
    	action.setPermissoes(new String[]{"1","2"});
    	
    	manager.expects(once()).method("save").with(eq(perfil));

    	assertEquals("success",action.insert());
    	assertEquals(papeis.size(), action.getPerfil().getPapeis().size());
    }
    
    public void testUpdate() throws Exception
    {
    	Perfil perfil = new Perfil();
    	perfil.setId(1L);
    	perfil.setNome("Perfil 1");
    	action.setPerfil(perfil);
    	manager.expects(once()).method("update").with(eq(perfil));
    	
    	assertEquals("success",action.update());
    }
    
    public void testGetSet()
    {
    	action.setExibirPerfil("");
    	action.getExibirPerfil();
    	action.getPermissoes();
    	action.getModel();
    }
}