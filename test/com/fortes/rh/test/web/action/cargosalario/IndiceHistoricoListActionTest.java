package com.fortes.rh.test.web.action.cargosalario;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.cargosalario.IndiceHistoricoListAction;

public class IndiceHistoricoListActionTest extends MockObjectTestCase
{	
	private IndiceHistoricoListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new IndiceHistoricoListAction();
       
        manager = new Mock(IndiceHistoricoManager.class);
        action.setIndiceHistoricoManager((IndiceHistoricoManager) manager.proxy());

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
    	assertEquals( "success", action.execute());
    }

    public void testDelete() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	action.setIndiceHistorico(indiceHistorico);
    	
    	manager.expects(once()).method("remove").with(eq(indiceHistorico.getId()));
    	
    	assertEquals("success", action.delete());
    	assertEquals(indiceHistorico, action.getIndiceHistorico());
    }
    
    public void testGet() throws Exception
    {
    	action.setIndiceHistorico(null);
    	assertNotNull(action.getIndiceHistorico());
    	
    	Indice indice = IndiceFactory.getEntity(1L);
    	action.setIndiceAux(indice);
    	assertEquals(indice, action.getIndiceAux());
    }
}