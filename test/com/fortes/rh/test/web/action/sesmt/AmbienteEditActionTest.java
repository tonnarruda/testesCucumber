package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.AmbienteEditAction;
import com.fortes.web.tags.CheckBox;

public class AmbienteEditActionTest extends MockObjectTestCase
{
	private AmbienteEditAction action;
	private Mock manager;
	private Mock historicoAmbienteManager;
	private Mock riscoManager;
	private Mock epcManager;
	private Mock estabelecimentoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(AmbienteManager.class);
        historicoAmbienteManager = new Mock(HistoricoAmbienteManager.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

        action = new AmbienteEditAction();
        action.setAmbienteManager((AmbienteManager) manager.proxy());
        action.setHistoricoAmbienteManager((HistoricoAmbienteManager) historicoAmbienteManager.proxy());
        
        riscoManager = mock(RiscoManager.class);
        action.setRiscoManager((RiscoManager) riscoManager.proxy());
        
        epcManager = mock(EpcManager.class);
        action.setEpcManager((EpcManager) epcManager.proxy());
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    	manager = null;
    	historicoAmbienteManager = null;
        action = null;
        super.tearDown();
    }

    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	riscoManager.expects(once()).method("findRiscosAmbientesByEmpresa").with(eq(empresa.getId())).will(returnValue(new ArrayList<RiscoAmbiente>()));
		epcManager.expects(once()).method("findAllSelect");
		epcManager.expects(once()).method("populaCheckBox");
		estabelecimentoManager.expects(once()).method("findAllSelect");

    	assertEquals(action.prepareInsert(), "success");
    }

    public void testPrepareUpdate() throws Exception
    {
    	Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		ambiente.setEmpresa(action.getEmpresaSistema());

		Collection<HistoricoAmbiente> historicos = new ArrayList<HistoricoAmbiente>();
		action.setAmbiente(ambiente);

    	manager.expects(once()).method("findByIdProjection").with(eq(ambiente.getId())).will(returnValue(ambiente));
    	historicoAmbienteManager.expects(once()).method("findToList").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(historicos));
    	estabelecimentoManager.expects(once()).method("findAllSelect");

    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getAmbiente(), ambiente);
    }
    
    public void testPrepareUpdateEmpresaErrada() throws Exception
    {
    	Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
    	Empresa outraEmpresa = new Empresa();
    	outraEmpresa.setId(1234L);
    	ambiente.setEmpresa(outraEmpresa);
    	action.setAmbiente(ambiente);
    	manager.expects(once()).method("findByIdProjection").with(eq(ambiente.getId())).will(returnValue(ambiente));
    	estabelecimentoManager.expects(once()).method("findAllSelect");
    	
    	assertEquals("error", action.prepareUpdate());
    }

    public void testInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setControlaRiscoPor('A');
    	action.setEmpresaSistema(empresa);
    	
    	Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		action.setAmbiente(ambiente);

		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(1L);
		action.setHistoricoAmbiente(historicoAmbiente);

		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();
		action.setRiscoChecks(riscoChecks);
		action.setRiscosAmbientes(riscosAmbientes);
		action.setEpcCheck(epcCheck);
		
		manager.expects(once()).method("saveAmbienteHistorico").with(new Constraint[]{eq(ambiente), eq(historicoAmbiente), eq(riscoChecks), eq(riscosAmbientes), eq(epcCheck), eq(empresa.getControlaRiscoPor())});

    	assertEquals("success", action.insert());
    	assertEquals(action.getAmbiente().getEmpresa(), action.getEmpresaSistema());
    }

    public void testUpdate() throws Exception
    {
    	Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);
    	ambiente.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setAmbiente(ambiente);

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("update").with(eq(ambiente));

    	assertEquals(action.update(), "success");
    	assertEquals(action.getAmbiente(), ambiente);

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	assertEquals(action.update(), "error");
    	assertNotNull(action.getActionErrors());
    }
    
    public void testDelete() throws Exception
    {
    	Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);
    	action.setAmbiente(ambiente);

    	manager.expects(once()).method("removeCascade").with(ANYTHING);

    	assertEquals(action.delete(), "success");
    }

    public void testList() throws Exception
    {
    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		Ambiente a1 = new Ambiente();
		a1.setId(1L);
		a1.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
		Ambiente a2 = new Ambiente();
		a2.setId(2L);
		a2.setEmpresa(MockSecurityUtil.getEmpresaSession(null));

		ambientes.add(a1);
		ambientes.add(a2);

		manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(ambientes.size()));
    	manager.expects(once()).method("findAmbientes").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(ambientes));

    	assertEquals("success", action.list());
    	assertEquals(action.getAmbientes(), ambientes);
    }
    
    public void testImprimirList() throws Exception
    {
    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
    	Ambiente a1 = new Ambiente();
    	a1.setId(1L);
    	a1.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	Ambiente a2 = new Ambiente();
    	a2.setId(2L);
    	a2.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	
    	ambientes.add(a1);
    	ambientes.add(a2);
    	
    	manager.expects(once()).method("findAmbientes").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(ambientes));
    	
    	assertEquals("success", action.imprimirLista());
    	assertEquals(action.getAmbientes(), ambientes);
    }

    public void testImprimirListErro() throws Exception
    {
    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
    	
    	manager.expects(once()).method("findAmbientes").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(ambientes));
		manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(ambientes.size()));
    	manager.expects(once()).method("findAmbientes").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(ambientes));
    	
    	assertEquals("input", action.imprimirLista());
    	assertEquals(action.getAmbientes(), ambientes);
    }
    
    public void testGetSet() throws Exception
    {
    	Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);
    	action.setAmbiente(ambiente);

    	assertEquals(action.getAmbiente(), ambiente);

    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(1L);
    	action.setHistoricoAmbiente(historicoAmbiente);
    	assertEquals(action.getHistoricoAmbiente(), historicoAmbiente);

    	Collection<HistoricoAmbiente> collection = new ArrayList<HistoricoAmbiente>();
    	collection.add(historicoAmbiente);
    	action.setHistoricoAmbientes(collection);
    	assertEquals(action.getHistoricoAmbientes(), collection);

    	ambiente = null;
    	action.setAmbiente(ambiente);

        assertTrue(action.getAmbiente() instanceof Ambiente);
        
    	assertNotNull(action.getAmbiente());
    	
    	action.getRiscoChecks();
    	action.getEpcEficazChecks();
    	action.getRiscos();
    	action.getEpcs();
    	action.getEpcCheck();
    	action.setEpcCheckList(new ArrayList<CheckBox>());
    	action.getEpcCheckList();
    	action.getEstabelecimentos();
    }
}