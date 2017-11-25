package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.AmbienteEditAction;
import com.fortes.web.tags.CheckBox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RelatorioUtil.class, SecurityUtil.class})
public class AmbienteEditActionTest
{
	private AmbienteEditAction action;
	private AmbienteManager manager;
	private RiscoManager riscoManager;
	private EpcManager epcManager;
	private EstabelecimentoManager estabelecimentoManager;
	private RiscoAmbienteManager riscoAmbienteManager;
	private EpiManager epiManager;

	@Before
    public void setUp() throws Exception
    {
        action = new AmbienteEditAction();

        manager = mock(AmbienteManager.class);
        action.setAmbienteManager(manager);
        
        riscoManager = mock(RiscoManager.class);
        action.setRiscoManager((RiscoManager) riscoManager);
        
        epcManager = mock(EpcManager.class);
        action.setEpcManager(epcManager);
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager(estabelecimentoManager);
        
        riscoAmbienteManager = mock(RiscoAmbienteManager.class);
        action.setRiscoAmbienteManager(riscoAmbienteManager);
        
        epiManager = mock(EpiManager.class);
        action.setEpiManager(epiManager);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        
        PowerMockito.mockStatic(SecurityUtil.class);
        PowerMockito.mockStatic(RelatorioUtil.class);
    }

    @Test
    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	when(riscoManager.findRiscosAmbientesByEmpresa(eq(empresa.getId()))).thenReturn(new ArrayList<RiscoAmbiente>());
    	when(epcManager.findAllSelect(eq(empresa.getId()))).thenReturn(new ArrayList<Epc>());
    	when(epcManager.populaCheckBox(eq(empresa.getId()))).thenReturn(new ArrayList<CheckBox>());
    	when(estabelecimentoManager.findAllSelect(eq(empresa.getId()))).thenReturn(new ArrayList<Estabelecimento>());
    	
    	assertEquals(action.prepareInsert(), "success");
    }

    @Test
    public void testInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
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
		
    	assertEquals("success", action.insert());
    }

    @Test
    public void testDelete() throws Exception
    {
    	Ambiente ambiente = new Ambiente();
    	ambiente.setId(1L);
    	action.setAmbiente(ambiente);

    	assertEquals(action.delete(), "success");
    }

    @Test
    public void testListException() throws Exception
    {
		when(manager.getCount(action.getEmpresaSistema().getId(), action.getHistoricoAmbiente())).thenReturn(0);
		doThrow(new ColecaoVaziaException()).when(manager).findAmbientes(action.getPage(), action.getPagingSize(), action.getEmpresaSistema().getId(), action.getHistoricoAmbiente());;
		
	   	assertEquals("success", action.list());
	   	assertEquals("Não existem dados para o filtro informado.", action.getActionMessages().iterator().next());
    }
    
    @Test
    public void testList() throws Exception
    {
    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		Ambiente a1 = new Ambiente();
		a1.setId(1L);
		Ambiente a2 = new Ambiente();
		a2.setId(2L);

		ambientes.add(a1);
		ambientes.add(a2);

		when(manager.getCount(action.getEmpresaSistema().getId(), action.getHistoricoAmbiente())).thenReturn(ambientes.size());
		when(manager.findAmbientes(action.getPage(), action.getPagingSize(), action.getEmpresaSistema().getId(), action.getHistoricoAmbiente())).thenReturn(ambientes);

		assertEquals("success", action.list());
    	assertEquals(action.getAmbientes(), ambientes);
    }
    
    @Test
    public void testImprimirLista() throws Exception
    {
    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
    	Ambiente a1 = AmbienteFactory.getEntity(1L);
    	Ambiente a2 = AmbienteFactory.getEntity(2L);
    	
    	ambientes.add(a1);
    	ambientes.add(a2);
    	
    	when(manager.findAmbientes(0, 0, action.getEmpresaSistema().getId(), action.getHistoricoAmbiente())).thenReturn(ambientes);
    	
    	assertEquals("success", action.imprimirLista());
    	assertEquals(action.getAmbientes(), ambientes);
    }

    @Test
    public void testImprimirListaErro() throws Exception
    {
    	Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
    	
    	when(manager.findAmbientes(action.getPage(), action.getPagingSize(), action.getEmpresaSistema().getId(), new HistoricoAmbiente())).thenReturn(ambientes);
    	when(manager.getCount(action.getEmpresaSistema().getId(), new HistoricoAmbiente())).thenReturn(ambientes.size());
    	
    	assertEquals("input", action.imprimirLista());
    	assertEquals(action.getAmbientes(), ambientes);
    }
    
    @Test
    public void testPrepareRelatorioMapaDeRisco() throws Exception
    {
    	String[] estabelecimentoCheck = {"1"};
    	action.setEstabelecimentoCheck(estabelecimentoCheck);
    	Collection<CheckBox> ambientesCheckList = new ArrayList<CheckBox>();
    	
    	when(estabelecimentoManager.populaCheckBox(eq(action.getEmpresaSistema().getId()))).thenReturn(ambientesCheckList);
    	when(manager.populaCheckBoxByEstabelecimentos(eq(LongUtil.arrayStringToArrayLong(estabelecimentoCheck)))).thenReturn(new ArrayList<CheckBox>());
       	
    	assertEquals("success", action.prepareRelatorioMapaDeRisco());
    }
    
    @Test
    public void testImprimirRelatorioMapaDeRisco() throws Exception
    {
    	String[] ambientesCheck = {"1","2"};
    	action.setAmbienteCheck(ambientesCheck);
    	
    	Collection<RiscoAmbiente> riscoAmbientes = new ArrayList<RiscoAmbiente>();
    	riscoAmbientes.add(new RiscoAmbiente());
    	
    	when(riscoAmbienteManager.findByAmbiente(anyLong())).thenReturn(riscoAmbientes);
    	when(manager.findByIdProjection(anyLong())).thenReturn(new Ambiente());
    	when(epiManager.findEpisDoAmbiente(anyLong(), any(Date.class))).thenReturn(new ArrayList<Epi>());
    	when(manager.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.MASCULINO))).thenReturn(2);
    	when(manager.getQtdColaboradorByAmbiente(anyLong(), any(Date.class), eq(Sexo.FEMININO))).thenReturn(1);
    	
    	assertEquals("success", action.imprimirRelatorioMapaDeRisco());
    }
    
    @Test
    public void testImprimirRelatorioMapaDeRiscoSemRiscoAmbiente() throws Exception
    {
    	CheckBox checkBox = new CheckBox();
    	checkBox.setId("1");
    	
    	Collection<CheckBox> ambientesCheckList = new ArrayList<CheckBox>();
    	ambientesCheckList.add(checkBox);
    	
    	String[] ambientesCheck = {"1"};
    	String[] estabelecimentoCheck = {};
    	
    	action.setAmbienteCheck(ambientesCheck);
    	action.setAmbienteCheckList(ambientesCheckList);
    	action.setEstabelecimentoCheck(estabelecimentoCheck);
    	
    	Collection<RiscoAmbiente> riscoAmbientes = new ArrayList<RiscoAmbiente>();
    	
    	when(riscoAmbienteManager.findByAmbiente(1L)).thenReturn(riscoAmbientes);
    	when(estabelecimentoManager.populaCheckBox(eq(action.getEmpresaSistema().getId()))).thenReturn(ambientesCheckList);
    	when(manager.populaCheckBoxByEstabelecimentos(eq(LongUtil.arrayStringToArrayLong(estabelecimentoCheck)))).thenReturn(new ArrayList<CheckBox>());
    
    	assertEquals("input", action.imprimirRelatorioMapaDeRisco());
    }
    
    @Test
    public void testImprimirRelatorioMapaDeRiscoException() throws Exception
    {
    	CheckBox checkBox = new CheckBox();
    	checkBox.setId("1");
    	
    	Collection<CheckBox> ambientesCheckList = new ArrayList<CheckBox>();
    	ambientesCheckList.add(checkBox);
    	
    	String[] ambientesCheck = {"1"};
    	String[] estabelecimentoCheck = {};
    	
    	action.setAmbienteCheck(ambientesCheck);
    	action.setAmbienteCheckList(ambientesCheckList);
    	action.setEstabelecimentoCheck(estabelecimentoCheck);
    	
    	Collection<RiscoAmbiente> riscoAmbientes = null;
    	
    	when(riscoAmbienteManager.findByAmbiente(1L)).thenReturn(riscoAmbientes);
    	when(estabelecimentoManager.populaCheckBox(eq(action.getEmpresaSistema().getId()))).thenReturn(ambientesCheckList);
    	when(manager.populaCheckBoxByEstabelecimentos(eq(LongUtil.arrayStringToArrayLong(estabelecimentoCheck)))).thenReturn(ambientesCheckList);
    	
    	assertEquals("input", action.imprimirRelatorioMapaDeRisco());
    }
    
    @Test
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