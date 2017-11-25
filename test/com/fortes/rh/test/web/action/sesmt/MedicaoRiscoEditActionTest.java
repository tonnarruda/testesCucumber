package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.MedicaoRiscoManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoAmbienteFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoMedicaoRiscoFactory;
import com.fortes.rh.web.action.sesmt.MedicaoRiscoEditAction;

@SuppressWarnings("unchecked")
public class MedicaoRiscoEditActionTest 
{
	private MedicaoRiscoEditAction action;
	private MedicaoRiscoManager manager;
	private AmbienteManager ambienteManager;
	private FuncaoManager funcaoManager;
	private RiscoAmbienteManager riscoAmbienteManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private RiscoMedicaoRiscoManager riscoMedicaoRiscoManager;
	private HistoricoAmbienteManager historicoAmbienteManager;

	@Before
	public void setUp() throws Exception
	{
		manager = mock(MedicaoRiscoManager.class);
		action = new MedicaoRiscoEditAction();
		action.setMedicaoRiscoManager(manager);
		
		ambienteManager = mock(AmbienteManager.class);
		action.setAmbienteManager(ambienteManager);
		
		funcaoManager = mock(FuncaoManager.class);
		action.setFuncaoManager(funcaoManager);
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);
		
		riscoAmbienteManager = mock(RiscoAmbienteManager.class);
		action.setRiscoAmbienteManager(riscoAmbienteManager);
        
		riscoFuncaoManager = mock(RiscoFuncaoManager.class);
		action.setRiscoFuncaoManager(riscoFuncaoManager);

		riscoMedicaoRiscoManager = mock(RiscoMedicaoRiscoManager.class);
		action.setRiscoMedicaoRiscoManager(riscoMedicaoRiscoManager);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));		
        action.setMedicaoRisco(new MedicaoRisco());
        
        historicoAmbienteManager = mock(HistoricoAmbienteManager.class);
        action.setHistoricoAmbienteManager(historicoAmbienteManager);
	}

	@Test
	public void testListByAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		when(ambienteManager.findAmbientes(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Ambiente>());
		when(manager.findAllSelectByAmbiente(eq(action.getEmpresaSistema().getId()), anyLong())).thenReturn(new ArrayList<MedicaoRisco>());
		
		assertEquals(action.list(), "success");
		assertNotNull(action.getMedicaoRiscos());
	}

	@Test
	public void testListByFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		when(funcaoManager.findByEmpresa(eq(empresa.getId()))).thenReturn(new ArrayList<Funcao>());
		when(manager.findAllSelectByFuncao(eq(action.getEmpresaSistema().getId()), anyLong())).thenReturn(new ArrayList<MedicaoRisco>());
		
		assertEquals(action.list(), "success");
		assertNotNull(action.getMedicaoRiscos());
	}
	
	@Test
	public void testPrepareInsertByAmbiente() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		Ambiente ambiente = AmbienteFactory.getEntity(2L);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setAmbiente(ambiente);
		action.setMedicaoRisco(medicaoRisco);
		
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(manager.findById(eq(medicaoRisco.getId()))).thenReturn(medicaoRisco);
		
		assertEquals("success", action.prepareInsert());
	}
	
	@Test
	public void testPrepareInsertByFuncao() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		Funcao funcao  = FuncaoFactory.getEntity(2L);
		funcao.setEmpresa(empresa);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setFuncao(funcao);
		action.setMedicaoRisco(medicaoRisco);
		
		when(manager.getTecnicasUtilizadas(eq(empresa.getId()))).thenReturn("");
		
		when(manager.getMedicaoRiscoMedicaoPorFuncao(eq(medicaoRisco.getId()))).thenReturn(medicaoRisco);
		when(manager.findRiscoMedicaoRiscos(eq(medicaoRisco.getId()))).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		assertEquals("success", action.prepareInsert());
	}
	
	@Test
	public void testPrepareUpdateByAmbiente() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
		
		Ambiente ambiente = AmbienteFactory.getEntity(2L);
		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(ambiente.getNome(), action.getEstabelecimento(), "descricao", ambiente, new Date(), "");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setAmbiente(ambiente);
		action.setMedicaoRisco(medicaoRisco);
		
		when(manager.getTecnicasUtilizadas(eq(action.getEmpresaSistema().getId()))).thenReturn("");
		when(historicoAmbienteManager.findUltimoHistoricoAteData(ambiente.getId(), medicaoRisco.getData())).thenReturn(historicoAmbiente);
		
		when(estabelecimentoManager.findAllSelect(eq(empresa.getId()))).thenReturn(new ArrayList<Estabelecimento>());
		when(manager.findById(eq(medicaoRisco.getId()))).thenReturn(medicaoRisco);
		when(riscoAmbienteManager.findRiscosByAmbienteData(eq(ambiente.getId()), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco),anyCollection())).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		
		assertEquals("success", action.prepareUpdate());
	}
	
	@Test
	public void testPrepareUpdateByFuncao() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		Funcao funcao  = FuncaoFactory.getEntity(2L);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setFuncao(funcao);
		action.setMedicaoRisco(medicaoRisco);
		
		when(manager.getTecnicasUtilizadas(eq(empresa.getId()))).thenReturn("");
		when(manager.getMedicaoRiscoMedicaoPorFuncao(eq(medicaoRisco.getId()))).thenReturn(medicaoRisco);
		when(manager.findRiscoMedicaoRiscos(eq(medicaoRisco.getId()))).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		when(funcaoManager.findByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Funcao>());
		when(riscoFuncaoManager.findRiscosByFuncaoData(eq(action.getMedicaoRisco().getFuncao().getId()), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco), anyCollection())).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		
		assertEquals("success", action.prepareUpdate());
	}
	
	@Test
	public void testDelete() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);

		assertEquals(action.delete(), "success");
	}
	
	@Test
	public void testCarregarRiscosByAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setAmbiente(ambiente);
		
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		
		when(riscoAmbienteManager.findRiscosByAmbienteData(eq(action.getEmpresaSistema().getId()), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco), anyCollection())).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		
		assertEquals("success", action.carregarRiscos());
	}
	
	@Test
	public void testCarregarRiscosMedicaoByAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setAmbiente(ambiente);
		
		RiscoMedicaoRisco riscoMedicaoRisco = RiscoMedicaoRiscoFactory.getEntity();
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		riscoMedicaoRiscos.add(riscoMedicaoRisco);
		
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		when(riscoMedicaoRiscoManager.findMedicoesDeRiscosDoAmbiente(eq(ambiente.getId()), any(Date.class))).thenReturn(riscoMedicaoRiscos);
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		
		when(riscoAmbienteManager.findRiscosByAmbienteData(eq(ambiente.getId()), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco), anyCollection())).thenReturn(riscoMedicaoRiscos);
		
		assertEquals("success", action.carregarRiscosComMedicao());
		assertTrue(action.getActionMessages().isEmpty());
	}
	
	@Test
	public void testCarregarRiscosMedicaoByFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		Funcao funcao = FuncaoFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setFuncao(funcao);
		action.setMedicaoRisco(medicaoRisco);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setFuncao(funcao);
		
		RiscoMedicaoRisco riscoMedicaoRisco = RiscoMedicaoRiscoFactory.getEntity();
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		riscoMedicaoRiscos.add(riscoMedicaoRisco);
		
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		when(riscoMedicaoRiscoManager.findMedicoesDeRiscosDaFuncao(eq(action.getMedicaoRisco().getFuncao().getId()), any(Date.class))).thenReturn(riscoMedicaoRiscos);
		
		when(funcaoManager.findByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Funcao>());
		when(riscoFuncaoManager.findRiscosByFuncaoData(eq(action.getFuncao().getId()), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco), anyCollection())).thenReturn(riscoMedicaoRiscos);
		
		assertEquals("success", action.carregarRiscosComMedicao());
		assertTrue(action.getActionMessages().isEmpty());
	}

	@Test
	public void testCarregarRiscosMedicaosemRicosASerCarregado() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		
		Funcao funcao = FuncaoFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setFuncao(funcao);
		action.setMedicaoRisco(medicaoRisco);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setFuncao(funcao);
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		when(riscoMedicaoRiscoManager.findMedicoesDeRiscosDaFuncao(eq(action.getMedicaoRisco().getFuncao().getId()), any(Date.class))).thenReturn(riscoMedicaoRiscos);
		
		when(funcaoManager.findByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Funcao>());
		when(riscoFuncaoManager.findRiscosByFuncaoData(eq(funcao.getId()), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco), anyCollection())).thenReturn(riscoMedicaoRiscos);
		
		assertEquals("success", action.carregarRiscosComMedicao());
		assertEquals("Não há medição anterior para esta Função",action.getActionMessages().toArray()[0]);
	}
	
	@Test
	public void testCarregarRiscosByFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setAmbiente(ambiente);
		
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		
		when(funcaoManager.findByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Funcao>());
		when(riscoFuncaoManager.findRiscosByFuncaoData(anyLong(), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco), anyCollection())).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		assertEquals("success", action.carregarRiscos());
	}
	
	@Test
	public void testCarregarRiscosBy() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor('F');
		action.setEmpresaSistema(empresa);
		
		
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));
		action.setAmbiente(ambiente);
		
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		
		when(funcaoManager.findByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Funcao>());
		when(riscoFuncaoManager.findRiscosByFuncaoData(anyLong(), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco), anyCollection())).thenReturn(new ArrayList<RiscoMedicaoRisco>());
		
		assertEquals("success", action.carregarRiscos());
	}
	
	private String[] ltcatValues = new String[]{"ltcat"};
	private String[] ppraValues = new String[]{"ppra"};
	private String[] tecnicaValues = new String[]{"tecnica"};
	private String[] intensidadeValues = new String[]{"120 graus"};
	private String[] riscoIds = new String[]{"1"};

	@Test
	public void testInsertByAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setControlaRiscoPor(empresa.getControlaRiscoPor());
		action.setEmpresaSistema(empresa);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);
		
		action.setLtcatValues(ltcatValues);
		action.setPpraValues(ppraValues);
		action.setIntensidadeValues(intensidadeValues);
		action.setTecnicaValues(tecnicaValues);
		action.setRiscoIds(riscoIds);

		assertEquals("success", action.insert());
	}

	@Test
	public void testInsertByFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('F');
		action.setControlaRiscoPor(empresa.getControlaRiscoPor());
		action.setEmpresaSistema(empresa);
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);
		
		action.setLtcatValues(ltcatValues);
		action.setPpraValues(ppraValues);
		action.setIntensidadeValues(intensidadeValues);
		action.setTecnicaValues(tecnicaValues);
		action.setRiscoIds(riscoIds);

		assertEquals("success", action.insert());
	}
	@Test
	public void testInsertException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setControlaRiscoPor('A');
		action.setEmpresaSistema(empresa);
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);
		
		doThrow(new ObjectNotFoundException("","")).when(manager).save(eq(action.getMedicaoRisco()), any(String[].class), any(String[].class), any(String[].class), any(String[].class),
				any(String[].class));
		
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		assertEquals("input", action.insert());
		assertEquals("Não foi possível gravar esta medição de risco.", action.getActionErrors().iterator().next());
	}
	
	@Test
	public void testUpdatePorAmbiente() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);
		
		action.setLtcatValues(ltcatValues);
		action.setPpraValues(ppraValues);
		action.setIntensidadeValues(intensidadeValues);
		action.setTecnicaValues(tecnicaValues);
		action.setRiscoIds(riscoIds);
		action.setControlaRiscoPor('A');

		assertEquals("success", action.update());
	}
	
	@Test
	public void testUpdatePorFuncao() throws Exception
	{
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		action.setMedicaoRisco(medicaoRisco);
		
		action.setLtcatValues(ltcatValues);
		action.setPpraValues(ppraValues);
		action.setIntensidadeValues(intensidadeValues);
		action.setTecnicaValues(tecnicaValues);
		action.setRiscoIds(riscoIds);
		action.setControlaRiscoPor('F');

		assertEquals("success", action.update());
	}

	@Test
	public void testUpdateException() throws Exception
	{
		Ambiente ambiente = AmbienteFactory.getEntity(15L);
		
		action.setAmbiente(ambiente);
		action.setData(new Date());
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setAmbiente(ambiente);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		action.setEmpresaSistema(empresa);

		action.setMedicaoRisco(medicaoRisco);
		

		doThrow(new ObjectNotFoundException("","")).when(manager).save(eq(action.getMedicaoRisco()), any(String[].class), any(String[].class), any(String[].class), any(String[].class),
				any(String[].class));
	
		when(manager.getTecnicasUtilizadas(action.getEmpresaSistema().getId())).thenReturn("");
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(manager.findById(eq(medicaoRisco.getId()))).thenReturn(medicaoRisco);
		
		when(riscoAmbienteManager.findRiscosByAmbienteData(eq(ambiente.getId()), any(Date.class))).thenReturn(new ArrayList<Risco>());
		when(manager.preparaRiscosDaMedicao(eq(medicaoRisco), anyCollection())).thenReturn(new ArrayList<RiscoMedicaoRisco>());

		assertEquals("input", action.update());
	}

	@Test
	public void testGetSet() throws Exception
	{
		action.setMedicaoRisco(null);

		assertNotNull(action.getMedicaoRisco());
		assertTrue(action.getMedicaoRisco() instanceof MedicaoRisco);
	}
}