package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;
import com.fortes.rh.web.action.sesmt.HistoricoFuncaoEditAction;
import com.fortes.web.tags.CheckBox;

public class HistoricoFuncaoEditActionTest
{
	private HistoricoFuncaoEditAction action;
	private HistoricoFuncaoManager historicoFuncaManager;
	private ExameManager exameManager;
	private RiscoManager riscoManager;
	private CursoManager cursoManager;
	private EpiManager epiManager;
	private CargoManager cargoManager;
	
	@Before
	public void setUp() throws Exception {
		action = new HistoricoFuncaoEditAction();
		
		historicoFuncaManager = mock(HistoricoFuncaoManager.class);
		action.setHistoricoFuncaoManager(historicoFuncaManager);
		
		exameManager = mock(ExameManager.class);
		action.setExameManager(exameManager);
		
		epiManager = mock(EpiManager.class);
		action.setEpiManager(epiManager);
		
		riscoManager = mock(RiscoManager.class);
		action.setRiscoManager(riscoManager);
		
		cursoManager = mock(CursoManager.class);
		action.setCursoManager(cursoManager);
		
		cargoManager = mock(CargoManager.class);
		action.setCargoManager(cargoManager);
	}
	
	@Before
	public void inicializaEmpresaEHistoricoFuncao(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setId(1L);
		action.setHistoricoFuncao(historicoFuncao);
	}

	@Test
    public void execute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

	@Test
    public void prepareInsert() throws Exception
    {
		Cargo cargo = CargoFactory.getEntity(1L);
		action.setCargoTmp(cargo);
		
		when(cargoManager.findByIdProjection(cargo.getId())).thenReturn(cargo);
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());

    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(action.getHistoricoFuncao(), action.getHistoricoFuncao());
    }

	@Test
    public void prepareUpdate() throws Exception
    {
		Cargo cargo = CargoFactory.getEntity(1L);
		action.setCargoTmp(cargo);
		
		when(cargoManager.findByIdProjection(cargo.getId())).thenReturn(cargo);
    	when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	
    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getHistoricoFuncao(), action.getHistoricoFuncao());
    }

	@Test
    public void insert() throws Exception
    {
    	assertEquals(action.insert(), "success");
    }
	
	@Test
    public void insertVeioDoSESMT() throws Exception
    {
		action.setVeioDoSESMT(true);
    	assertEquals(action.insert(), "SUCESSO_VEIO_SESMT");
    }
	
	@Test
	public void insertException() throws Exception{
		Cargo cargo = CargoFactory.getEntity(1L);
		action.setCargoTmp(cargo);
		
		when(cargoManager.findByIdProjection(cargo.getId())).thenReturn(cargo);
		doThrow(new Exception()).when(historicoFuncaManager).saveHistorico(action.getHistoricoFuncao(), action.getExamesChecked(), action.getEpisChecked(), action.getRiscoChecks(), action.getCursosChecked(), action.getRiscosFuncoes());
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	
 		assertEquals("input", action.insert());
 		assertEquals("Ocorreu um erro ao gravar o histórico da função", action.getActionErrors().iterator().next());
	}
	
	@Test
	public void insertFortesException() throws Exception{
		FortesException fortesException = new FortesException("Ocorreu um erro");
		setDadosParaSalvarHistorico();
		Cargo cargo = CargoFactory.getEntity(1L);
		action.setCargoTmp(cargo);
		
		when(cargoManager.findByIdProjection(cargo.getId())).thenReturn(cargo);
		doThrow(fortesException).when(historicoFuncaManager).saveHistorico(action.getHistoricoFuncao(), action.getExamesChecked(), action.getEpisChecked(), action.getRiscoChecks(), action.getCursosChecked(), action.getRiscosFuncoes());
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	
 		assertEquals("input", action.insert());
 		assertEquals(fortesException.getMessage(), action.getActionWarnings().iterator().next());
	}
	
	private void setDadosParaSalvarHistorico(){
		Long[] examesChecked = new Long[]{1L};
    	action.setExamesChecked(examesChecked);

    	Long[] episChecked = new Long[]{1L};
    	action.setEpisChecked(episChecked);
    	
    	Long[] riscoChecks = new Long[]{1L};
    	action.setRiscoChecks(riscoChecks);
    	
    	Long[] cursosChecked = new Long[]{1L};
    	action.setCursosChecked(cursosChecked);
    	
    	action.setRiscosFuncoes(RiscoFuncaoFactory.getCollection());
	}

	@Test
    public void update() throws Exception
    {
    	assertEquals(action.update(), "success");
    }
	
	@Test
    public void updateVeioDoSESMT() throws Exception
    {
		action.setVeioDoSESMT(true);
    	assertEquals(action.update(), "SUCESSO_VEIO_SESMT");
    }
	
	@Test
	public void updateException() throws Exception{
		Cargo cargo = CargoFactory.getEntity(1L);
		action.setCargoTmp(cargo);
		
		when(cargoManager.findByIdProjection(cargo.getId())).thenReturn(cargo);
		doThrow(new Exception()).when(historicoFuncaManager).saveHistorico(action.getHistoricoFuncao(), action.getExamesChecked(), action.getEpisChecked(), action.getRiscoChecks(), action.getCursosChecked(), action.getRiscosFuncoes());
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	
 		assertEquals("input", action.update());
 		assertEquals("Ocorreu um erro ao gravar o histórico da função", action.getActionErrors().iterator().next());
	}
	
	@Test
	public void updateFortesException() throws Exception{
		FortesException fortesException = new FortesException("Ocorreu um erro");
		setDadosParaSalvarHistorico();
    	
		Cargo cargo = CargoFactory.getEntity(1L);
		action.setCargoTmp(cargo);
		
		when(cargoManager.findByIdProjection(cargo.getId())).thenReturn(cargo);
		doThrow(fortesException).when(historicoFuncaManager).saveHistorico(action.getHistoricoFuncao(), action.getExamesChecked(), action.getEpisChecked(), action.getRiscoChecks(), action.getCursosChecked(), action.getRiscosFuncoes());
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	
 		assertEquals("input", action.update());
 		assertEquals(fortesException.getMessage(), action.getActionErrors().iterator().next());
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
    	assertEquals(action.getFuncao(), funcao);
    	assertEquals(action.getCargoTmp(), cargo);

    	action.getExamesChecked();
    	action.getExamesCheckList();
    }
}