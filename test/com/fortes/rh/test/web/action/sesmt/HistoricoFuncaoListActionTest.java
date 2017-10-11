package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.web.action.sesmt.HistoricoFuncaoListAction;

public class HistoricoFuncaoListActionTest
{
	private HistoricoFuncaoListAction action;
	private HistoricoFuncaoManager manager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private FuncaoManager funcaoManager;
	
	@Before
    public void setUp() throws Exception
    {
        action = new HistoricoFuncaoListAction();
        
        manager = mock(HistoricoFuncaoManager.class);
        action.setHistoricoFuncaoManager(manager);

        riscoFuncaoManager = mock(RiscoFuncaoManager.class);
        action.setRiscoFuncaoManager(riscoFuncaoManager);
        
        funcaoManager = mock(FuncaoManager.class);
        action.setFuncaoManager(funcaoManager);
    }

	@After
    public void tearDown() throws Exception
    {
        action = null;
        manager = null;
    }

	@Test
    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

	@Test
    public void testDelete() throws Exception
    {
    	Funcao funcao = FuncaoFactory.getEntity(1L);
    	action.setFuncao(funcao);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(1L);
    	action.setHistoricoFuncao(historicoFuncao);

    	when(riscoFuncaoManager.removeByHistoricoFuncao(historicoFuncao.getId())).thenReturn(true);
    	
    	assertEquals(action.delete(), "success");
    }

	@Test
    public void testGetSet() throws Exception
    {
    	HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(1L);

    	action.setHistoricoFuncao(historicoFuncao);

    	Funcao funcao = new Funcao();
    	funcao.setId(1L);

    	action.setFuncao(funcao);

    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	action.setCargoTmp(cargo);

    	assertEquals(action.getHistoricoFuncao(), historicoFuncao);
    	assertEquals(action.getCargoTmp(), cargo);
    	assertEquals(action.getFuncao(), funcao);
    }
}