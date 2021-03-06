package com.fortes.rh.test.web.action.acesso;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.test.util.mockObjects.MockLongUtil;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.acesso.PerfilListAction;

public class PerfilListActionTest extends MockObjectTestCase
{
	private PerfilListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new PerfilListAction();
        manager = new Mock(PerfilManager.class);
        action.setPerfilManager((PerfilManager) manager.proxy());
        
        Mockit.redefineMethods(LongUtil.class, MockLongUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testList() throws Exception
    {
    	Perfil perfil1 = new Perfil();
    	perfil1.setId(1L);
    	perfil1.setNome("Perfil 1");
    	perfil1.setPapeis(null);

    	Perfil testData2 = new Perfil();
    	testData2.setId(2L);
    	testData2.setNome("Perfil 2");
    	perfil1.setPapeis(null);

    	Collection<Perfil> perfilsAux = new ArrayList<Perfil>();
    	perfilsAux.add(perfil1);
    	perfilsAux.add(testData2);

    	manager.expects(once()).method("getCount").will(returnValue(2));
    	manager.expects(once()).method("findAll").with(eq(action.getPage()),eq(action.getPagingSize())).will(returnValue(perfilsAux));
    	manager.expects(once()).method("findAll").with(eq(null),eq(null)).will(returnValue(perfilsAux));

    	assertEquals(action.list(), "success");
    	assertEquals(action.getPerfils(), perfilsAux);
    	manager.verify();
    }
    
    public void testImprimirPerfis() throws Exception 
    {
    	manager.expects(once()).method("findPapeis");
    	assertEquals("success", action.imprimirPerfis() );
	}
    
    public void testDelete() throws Exception 
    {
    	Perfil perfil1 = new Perfil();
    	perfil1.setId(1L);
    	perfil1.setNome("Perfil 1");
    	perfil1.setPapeis(null);
    	action.setPerfil(perfil1);
    	
    	manager.expects(once()).method("remove");
    	assertEquals("success", action.delete());
    }
}