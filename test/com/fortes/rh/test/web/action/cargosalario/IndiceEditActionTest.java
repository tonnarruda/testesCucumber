package com.fortes.rh.test.web.action.cargosalario;

import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.cargosalario.IndiceEditAction;

public class IndiceEditActionTest extends MockObjectTestCase
{
	private IndiceEditAction action;
	private Mock manager;
	private Mock indiceHistoricoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new IndiceEditAction();
        
        manager = new Mock(IndiceManager.class);
        action.setIndiceManager((IndiceManager) manager.proxy());

        indiceHistoricoManager = new Mock(IndiceHistoricoManager.class);
        action.setIndiceHistoricoManager((IndiceHistoricoManager) indiceHistoricoManager.proxy());
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", action.execute());
    }

    public void testInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	action.setIndiceHistorico(indiceHistorico);
    	
    	manager.expects(once()).method("save").with(eq(indice), eq(indiceHistorico));

    	assertEquals("success", action.insert());
    	assertEquals(indice, action.getIndiceAux());
    	assertEquals(indiceHistorico, action.getIndiceHistorico());
    }
    
    public void testUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);
    	
    	manager.expects(once()).method("update").with(eq(indice));
    	
    	assertEquals("success", action.update());
    }
    
    public void testPrepareInsert() throws Exception
    {    	
    	assertEquals("success", action.prepareInsert());
    	assertNotNull(action.getIndiceAux());
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);
    	
    	Collection<IndiceHistorico> indiceHistoricos = IndiceHistoricoFactory.getCollection();
    	
    	manager.expects(once()).method("findById").with(eq(indice.getId())).will(returnValue(indice));
    	indiceHistoricoManager.expects(once()).method("findAllSelect").with(eq(indice.getId())).will(returnValue(indiceHistoricos));
    	
    	assertEquals("success", action.prepareUpdate());
    	assertEquals(indice, action.getIndiceAux());
    	assertEquals(indiceHistoricos, action.getIndicesHistoricos());
    }
    
    public void testGet() throws Exception
    {
    	assertNotNull(action.getModel());    	
    }
}