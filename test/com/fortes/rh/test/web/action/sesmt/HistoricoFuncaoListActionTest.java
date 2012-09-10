package com.fortes.rh.test.web.action.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.web.action.sesmt.HistoricoFuncaoListAction;

public class HistoricoFuncaoListActionTest extends MockObjectTestCase
{
	private HistoricoFuncaoListAction action;
	private Mock manager;
	private Mock riscoFuncaoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new HistoricoFuncaoListAction();
        
        manager = new Mock(HistoricoFuncaoManager.class);
        action.setHistoricoFuncaoManager((HistoricoFuncaoManager) manager.proxy());

        riscoFuncaoManager = new Mock(RiscoFuncaoManager.class);
        action.setRiscoFuncaoManager((RiscoFuncaoManager) riscoFuncaoManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        action = null;
        manager = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testDelete() throws Exception
    {
    	HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(1L);
    	action.setHistoricoFuncao(historicoFuncao);

    	riscoFuncaoManager.expects(once()).method("removeByHistoricoFuncao").with(ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("remove").with(ANYTHING);
    	
    	assertEquals(action.delete(), "success");
    }

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