package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.sesmt.HistoricoFuncaoEditAction;

public class HistoricoFuncaoEditActionTest extends MockObjectTestCase
{
	private Mock exameManager;
	private HistoricoFuncaoEditAction action;
	private Mock manager;
	private Mock epiManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new HistoricoFuncaoEditAction();
        exameManager = new Mock(ExameManager.class);
        epiManager = new Mock(EpiManager.class);
        
        manager = new Mock(HistoricoFuncaoManager.class);
        action.setExameManager((ExameManager) exameManager.proxy());
        action.setHistoricoFuncaoManager((HistoricoFuncaoManager) manager.proxy());
        action.setEpiManager((EpiManager) epiManager.proxy());
        
        Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
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

    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(1L);
    	action.setHistoricoFuncao(historicoFuncao);

    	exameManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Exame>()));
    	epiManager.expects(once()).method("populaCheckToEpi").with(eq(empresa.getId())).will(returnValue(new ArrayList<Epi>()));
    	manager.expects(once()).method("findByIdProjection").with(eq(historicoFuncao.getId())).will(returnValue(historicoFuncao));

    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(action.getHistoricoFuncao(), historicoFuncao);
    }

    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(1L);

    	action.setHistoricoFuncao(historicoFuncao);

    	exameManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Exame>()));
    	epiManager.expects(once()).method("populaCheckToEpi").with(eq(empresa.getId())).will(returnValue(new ArrayList<Epi>()));
    	manager.expects(once()).method("findByIdProjection").with(eq(historicoFuncao.getId())).will(returnValue(historicoFuncao));

    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getHistoricoFuncao(), historicoFuncao);
    }

    public void testInsert() throws Exception
    {
    	HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(1L);
    	action.setHistoricoFuncao(historicoFuncao);

    	Long[] examesChecked = new Long[]{1L};
    	action.setExamesChecked(examesChecked);

    	Long[] episChecked = new Long[]{1L};
    	action.setEpisChecked(episChecked);

    	manager.expects(once()).method("saveHistorico").with(eq(historicoFuncao),eq(examesChecked), eq(episChecked));
    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(1L);

    	action.setHistoricoFuncao(historicoFuncao);
     	Long[] examesChecked = new Long[]{1L};
    	action.setExamesChecked(examesChecked);

    	Long[] episChecked = new Long[]{1L};
    	action.setEpisChecked(episChecked);

    	manager.expects(once()).method("updateHistorico").with(eq(historicoFuncao),eq(examesChecked),eq(episChecked));
    	assertEquals(action.update(), "success");
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
    	assertEquals(action.getFuncao(), funcao);
    	assertEquals(action.getCargoTmp(), cargo);

    	action.getExamesChecked();
    	action.getExamesCheckList();
    }
}








