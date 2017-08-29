package com.fortes.rh.test.web.action.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.cargosalario.FaixaSalarialHistoricoListAction;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringUtil.class, SecurityUtil.class})
public class FaixaSalarialHistoricoListActionTest 
{	
	private FaixaSalarialHistoricoListAction action;
	private FaixaSalarialHistoricoManager manager;
	private EmpresaManager empresaManager;

	@Before
    public void setUp() throws Exception
    {
        action = new FaixaSalarialHistoricoListAction();
        empresaManager = mock(EmpresaManager.class);
        
        manager = mock(FaixaSalarialHistoricoManager.class);
        action.setFaixaSalarialHistoricoManager(manager);

        PowerMockito.mockStatic(SpringUtil.class);
        PowerMockito.mockStatic(SecurityUtil.class);
    }

	@Test
    public void testExecute() throws Exception
    {
    	assertEquals( "success", action.execute());
    }

    @Test
    public void testDelete() throws Exception
    {
    	BDDMockito.given(SpringUtil.getBean("empresaManager")).willReturn(empresaManager);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);
    	
    	when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
    	
    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);
    	
    	assertEquals("success", action.delete());
    }
    
    @Test
    public void testDeleteExceptionESocial() throws Exception
    {
    	BDDMockito.given(SpringUtil.getBean("empresaManager")).willReturn(empresaManager);
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(true);
    	empresa.setAderiuAoESocial(true);
    	action.setEmpresaSistema(empresa);
    	
    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);
    	action.setFaixaSalarialHistorico(faixaSalarialHistorico);

    	when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
    	assertEquals("success", action.delete());
    	assertTrue(action.getActionMsg().contains("eSocial"));
    }

    @Test
    public void testGetSet() throws Exception
    {
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	action.setFaixaSalarialAux(faixaSalarial);
    	
    	assertEquals(faixaSalarial, action.getFaixaSalarialAux());
    }
}