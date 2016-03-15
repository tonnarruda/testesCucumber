package com.fortes.rh.test.web.action.cargosalario;

import java.util.Date;

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
import com.fortes.rh.web.action.cargosalario.IndiceHistoricoEditAction;

public class IndiceHistoricoEditActionTest extends MockObjectTestCase
{
	private IndiceHistoricoEditAction action;
	private Mock manager;
	private Mock indiceManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new IndiceHistoricoEditAction();
        
        manager = new Mock(IndiceHistoricoManager.class);
        action.setIndiceHistoricoManager((IndiceHistoricoManager) manager.proxy());

        indiceManager = new Mock(IndiceManager.class);
        action.setIndiceManager((IndiceManager) indiceManager.proxy());
        
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
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	indiceHistorico.setData(new Date());
    	action.setIndiceHistorico(indiceHistorico);
    	
    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);
    	
    	manager.expects(once()).method("verifyData").with(eq(indiceHistorico.getId()), eq(indiceHistorico.getData()), eq(indice.getId())).will(returnValue(false));
    	manager.expects(once()).method("save").with(eq(indiceHistorico));

    	assertEquals("success", action.insert());
    }
    
    public void testInsertInput() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	action.setIndiceHistorico(indiceHistorico);

    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);
    	
    	manager.expects(once()).method("verifyData").with(eq(indiceHistorico.getId()), eq(indiceHistorico.getData()), eq(indice.getId())).will(returnValue(true));
    	indiceManager.expects(once()).method("findByIdProjection").with(eq(indice.getId())).will(returnValue(indice));
    	
    	assertEquals("input", action.insert());
    }
    
    public void testUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	indiceHistorico.setData(new Date());
    	action.setIndiceHistorico(indiceHistorico);
    	
    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);
    	
    	manager.expects(once()).method("verifyData").with(eq(indiceHistorico.getId()), eq(indiceHistorico.getData()), eq(indice.getId())).will(returnValue(false));
    	
    	manager.expects(once()).method("update").with(eq(indiceHistorico));
    	
    	assertEquals("success", action.update());
    }
    
    public void testUpdateInput() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	indiceHistorico.setData(new Date());
    	action.setIndiceHistorico(indiceHistorico);

    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);
    	    	
    	manager.expects(once()).method("verifyData").with(eq(indiceHistorico.getId()), eq(indiceHistorico.getData()), eq(indice.getId())).will(returnValue(true));
    	manager.expects(once()).method("findByIdProjection").with(eq(indiceHistorico.getId())).will(returnValue(indiceHistorico));
    	
    	assertEquals("input", action.update());
    }
    
    public void testPrepareInsert() throws Exception
    {    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	action.setIndiceHistorico(indiceHistorico);

    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);

    	indiceManager.expects(once()).method("findByIdProjection").with(eq(indice.getId())).will(returnValue(indice));
    	assertEquals("success", action.prepareInsert());
    	assertEquals(indice, action.getIndiceAux());
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	action.setIndiceHistorico(indiceHistorico);
    	
    	manager.expects(once()).method("findByIdProjection").with(eq(indiceHistorico.getId())).will(returnValue(indiceHistorico));
    	
    	assertEquals("success", action.prepareUpdate());
    }
    
    public void testGet() throws Exception
    {
    	assertNotNull(action.getModel());    	
    }
}