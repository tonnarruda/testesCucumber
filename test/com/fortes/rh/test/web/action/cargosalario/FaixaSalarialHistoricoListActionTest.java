package com.fortes.rh.test.web.action.cargosalario;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.cargosalario.FaixaSalarialHistoricoListAction;

public class FaixaSalarialHistoricoListActionTest extends MockObjectTestCase
{	
	private FaixaSalarialHistoricoListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new FaixaSalarialHistoricoListAction();
       
        manager = new Mock(FaixaSalarialHistoricoManager.class);
        action.setFaixaSalarialHistoricoManager((FaixaSalarialHistoricoManager) manager.proxy());

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
    	assertEquals( "success", action.execute());
    }

    public void testDelete() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);
    	
    	manager.expects(once()).method("remove").with(eq(faixaSalarialHistorico.getId()), eq(empresa), eq(true));
    	
    	assertEquals("success", action.delete());
    }

    public void testGetSet() throws Exception
    {
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	action.setFaixaSalarialAux(faixaSalarial);
    	
    	assertEquals(faixaSalarial, action.getFaixaSalarialAux());
    }
}