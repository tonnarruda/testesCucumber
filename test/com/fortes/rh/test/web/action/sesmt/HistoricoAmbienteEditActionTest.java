package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.web.action.sesmt.HistoricoAmbienteEditAction;

public class HistoricoAmbienteEditActionTest extends MockObjectTestCase
{
	private HistoricoAmbienteEditAction action;
	private Mock manager;
	private Mock riscoManager;
	private Mock epcManager;
	private Mock ambienteManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new HistoricoAmbienteEditAction();

        manager = new Mock(HistoricoAmbienteManager.class);
        action.setHistoricoAmbienteManager((HistoricoAmbienteManager) manager.proxy());;
        
        riscoManager = mock(RiscoManager.class);
        action.setRiscoManager((RiscoManager) riscoManager.proxy());
        
        epcManager = mock(EpcManager.class);
        action.setEpcManager((EpcManager) epcManager.proxy());

        ambienteManager = mock(AmbienteManager.class);
        action.setAmbienteManager((AmbienteManager) ambienteManager.proxy());
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    protected void tearDown() throws Exception
    {
        action = null;
        manager = null;
        super.tearDown();
    }

    public void testPrepareInsert() throws Exception
    {
    	Ambiente ambiente = AmbienteFactory.getEntity(12L);
    	action.setAmbiente(ambiente);
    	
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();

    	action.setHistoricoAmbiente(historicoAmbiente);
    	
    	ambienteManager.expects(once()).method("findByIdProjection").with(eq(ambiente.getId())).will(returnValue(ambiente));
    	epcManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Epc>()));
    	riscoManager.expects(once()).method("findRiscosAmbientesByEmpresa").with(eq(1L)).will(returnValue(new ArrayList<RiscoAmbiente>()));

    	manager.expects(once()).method("findUltimoHistorico").with(eq(ambiente.getId())).will(returnValue(historicoAmbiente));

    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(action.getHistoricoAmbiente(), historicoAmbiente);
    }

    public void testPrepareUpdate() throws Exception
    {
    	Ambiente ambiente = AmbienteFactory.getEntity(12L);
    	action.setAmbiente(ambiente);
    	
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(1L);

    	action.setHistoricoAmbiente(historicoAmbiente);

    	manager.expects(once()).method("findById").with(eq(historicoAmbiente.getId())).will(returnValue(historicoAmbiente));
    	ambienteManager.expects(once()).method("findByIdProjection").with(eq(ambiente.getId())).will(returnValue(ambiente));
    	epcManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Epc>()));
    	riscoManager.expects(once()).method("findRiscosAmbientesByEmpresa").with(eq(1L)).will(returnValue(new ArrayList<RiscoAmbiente>()));

    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getHistoricoAmbiente(), historicoAmbiente);
    }

    public void testInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setControlaRiscoPor('A');
    	
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(1L);
    	
    	String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();
		action.setRiscoChecks(riscoChecks);
		action.setRiscosAmbientes(riscosAmbientes);
		action.setEpcCheck(epcCheck);
		action.setEmpresaSistema(empresa);
		
    	action.setHistoricoAmbiente(historicoAmbiente);
    	
    	manager.expects(once()).method("save").with(new Constraint[]{eq(historicoAmbiente), eq(riscoChecks), eq(riscosAmbientes), eq(epcCheck)});
    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setControlaRiscoPor('A');
    	
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(1L);
    	
    	String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();
		action.setRiscoChecks(riscoChecks);
		action.setRiscosAmbientes(riscosAmbientes);
		action.setEpcCheck(epcCheck);
		action.setEmpresaSistema(empresa);

    	action.setHistoricoAmbiente(historicoAmbiente);
    	manager.expects(once()).method("save").with(new Constraint[]{eq(historicoAmbiente), eq(riscoChecks), eq(riscosAmbientes), eq(epcCheck)});
    	assertEquals(action.update(), "success");
    }
    
    public void testDelete() throws Exception
    {
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(1L);
    	action.setHistoricoAmbiente(historicoAmbiente);

    	manager.expects(once()).method("removeCascade").with(eq(historicoAmbiente.getId()));
    	assertEquals(action.delete(), "success");
    }

    public void testGetSet() throws Exception
    {
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(1L);

    	action.setHistoricoAmbiente(historicoAmbiente);

    	Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);

    	action.setAmbiente(ambiente);

    	assertEquals(action.getHistoricoAmbiente(), historicoAmbiente);
    	assertEquals(action.getAmbiente(), ambiente);

    	historicoAmbiente = null;
    	action.setHistoricoAmbiente(historicoAmbiente);
    	assertTrue(action.getHistoricoAmbiente()  instanceof HistoricoAmbiente);
    	
    	action.getEpcCheckList();
    	action.setEpcCheckList(null);
    	action.getEpcCheck();
    	action.getRiscoChecks();
    	action.getEpcEficazChecks();
    }
}