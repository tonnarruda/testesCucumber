package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.web.action.sesmt.HistoricoAmbienteEditAction;

public class HistoricoAmbienteEditActionTest 
{
	private HistoricoAmbienteEditAction action;
	private HistoricoAmbienteManager historicoAmbienteManager;
	private RiscoManager riscoManager;
	private EpcManager epcManager;
	private AmbienteManager ambienteManager;
	private EstabelecimentoManager estabelecimentoManager;

	@Before
    public void setUp() throws Exception
    {
        action = new HistoricoAmbienteEditAction();

        historicoAmbienteManager = mock(HistoricoAmbienteManager.class);
        action.setHistoricoAmbienteManager(historicoAmbienteManager);;
        
        riscoManager = mock(RiscoManager.class);
        action.setRiscoManager(riscoManager);
        
        epcManager = mock(EpcManager.class);
        action.setEpcManager(epcManager);

        ambienteManager = mock(AmbienteManager.class);
        action.setAmbienteManager(ambienteManager);
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager(estabelecimentoManager);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

	@Test
    public void testPrepareInsert() throws Exception
    {
    	Ambiente ambiente = AmbienteFactory.getEntity(12L);
    	action.setAmbiente(ambiente);
    	
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();

    	action.setHistoricoAmbiente(historicoAmbiente);
    	
    	when(ambienteManager.findByIdProjection(eq(ambiente.getId()))).thenReturn(ambiente);
    	when(epcManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Epc>());
    	when(riscoManager.findRiscosAmbientesByEmpresa(eq(1L))).thenReturn(new ArrayList<RiscoAmbiente>());

    	when(historicoAmbienteManager.findUltimoHistorico(eq(ambiente.getId()))).thenReturn(historicoAmbiente);

    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(action.getHistoricoAmbiente(), historicoAmbiente);
    }
	
	@Test
    public void testPrepareInsertHistoricoAmbienteNulo() throws Exception
    {
    	Ambiente ambiente = AmbienteFactory.getEntity(12L);
    	action.setAmbiente(ambiente);
    	
    	HistoricoAmbiente historicoAmbiente = null;
    	action.setHistoricoAmbiente(historicoAmbiente);
    	
    	when(ambienteManager.findByIdProjection(eq(ambiente.getId()))).thenReturn(ambiente);
    	when(epcManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Epc>());
    	when(riscoManager.findRiscosAmbientesByEmpresa(eq(1L))).thenReturn(new ArrayList<RiscoAmbiente>());

    	when(historicoAmbienteManager.findUltimoHistorico(eq(ambiente.getId()))).thenReturn(historicoAmbiente);

    	assertEquals(action.prepareInsert(), "success");
    }

	@Test
    public void testPrepareUpdate() throws Exception
    {
    	Ambiente ambiente = AmbienteFactory.getEntity(12L);
    	action.setAmbiente(ambiente);
    	
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(1L);

    	action.setHistoricoAmbiente(historicoAmbiente);
    	when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
    	when(historicoAmbienteManager.findById(eq(historicoAmbiente.getId()))).thenReturn(historicoAmbiente);
    	when(ambienteManager.findByIdProjection(eq(ambiente.getId()))).thenReturn(ambiente);
    	when(epcManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Epc>());
    	when(riscoManager.findRiscosAmbientesByEmpresa(eq(1L))).thenReturn(new ArrayList<RiscoAmbiente>());

    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getHistoricoAmbiente(), historicoAmbiente);
    }

	@Test
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
    	assertEquals("success", action.insert());
    	assertEquals("Histórico do ambiente cadastrado com sucesso.", action.getActionSuccess().iterator().next());
    }
	
	@Test
    public void testInsertFortesException() throws Exception
    {
    	preparaInsertOuUpdate(null);
		
		doThrow(new FortesException("Teste")).when(historicoAmbienteManager).saveOrUpdate(action.getHistoricoAmbiente(), action.getRiscoChecks(), action.getRiscosAmbientes(), action.getEpcCheck());
		assertEquals(action.insert(), "input");
    }
	
	@Test
    public void testInsertException() throws Exception
    {
    	preparaInsertOuUpdate(null);
		
		doThrow(new Exception("Teste")).when(historicoAmbienteManager).saveOrUpdate(action.getHistoricoAmbiente(), action.getRiscoChecks(), action.getRiscosAmbientes(), action.getEpcCheck());
		assertEquals("input", action.insert());
		assertEquals("Ocorreu um erro ao gravar o histórico do ambiente.", action.getActionErrors().iterator().next());
    }

	private void preparaInsertOuUpdate(Long historicoAmbienteId) {
		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setControlaRiscoPor('A');
    	
    	Ambiente ambiente = AmbienteFactory.getEntity(2L);
    	action.setAmbiente(ambiente);
    	
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(historicoAmbienteId);
    	action.setHistoricoAmbiente(historicoAmbiente);
    	
    	String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();
		action.setRiscoChecks(riscoChecks);
		action.setRiscosAmbientes(riscosAmbientes);
		action.setEpcCheck(epcCheck);
		action.setEmpresaSistema(empresa);
		
		when(ambienteManager.findByIdProjection(action.getAmbiente().getId())).thenReturn(ambiente);
	}

	@Test
    public void testUpdateFortesException() throws Exception
    {
		preparaInsertOuUpdate(2L);
		doThrow(new Exception("Teste Update")).when(historicoAmbienteManager).saveOrUpdate(action.getHistoricoAmbiente(), action.getRiscoChecks(), action.getRiscosAmbientes(), action.getEpcCheck());
		when(historicoAmbienteManager.findById(action.getHistoricoAmbiente().getId())).thenReturn(action.getHistoricoAmbiente());
		assertEquals("input", action.update());
		assertEquals("Ocorreu um erro ao atualizar o histórico do ambiente.", action.getActionErrors().iterator().next());
    }
	
	
	@Test
    public void testUpdateException() throws Exception
    {
		preparaInsertOuUpdate(2L);
		doThrow(new FortesException("Teste Update")).when(historicoAmbienteManager).saveOrUpdate(action.getHistoricoAmbiente(), action.getRiscoChecks(), action.getRiscosAmbientes(), action.getEpcCheck());
		when(historicoAmbienteManager.findById(action.getHistoricoAmbiente().getId())).thenReturn(action.getHistoricoAmbiente());
		assertEquals("input", action.update());
    }
	
	@Test
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
    	assertEquals(action.update(), "success");
    }
    
	@Test
    public void testDelete() throws Exception
    {
    	HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
    	historicoAmbiente.setId(1L);
    	action.setHistoricoAmbiente(historicoAmbiente);
    	assertEquals(action.delete(), "success");
    }
	
	@Test
	public void testList(){
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		action.setAmbiente(ambiente);
		when(ambienteManager.findEntidadeComAtributosSimplesById(action.getAmbiente().getId())).thenReturn(ambiente);
		when(historicoAmbienteManager.findToList(eq(new String[]{"id","descricao","data","nomeAmbiente"}), eq(new String[]{"id","descricao","data","nomeAmbiente"}),
				eq(new String[]{"ambiente.id"}), eq(new Object[]{action.getAmbiente().getId()}),eq( new String[]{"data desc"}))).thenReturn(new ArrayList<HistoricoAmbiente>());
		
		assertEquals("success", action.list());
	}

    @Test
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