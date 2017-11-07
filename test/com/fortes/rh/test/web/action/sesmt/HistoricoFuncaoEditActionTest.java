package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.business.geral.UsuarioAjudaESocialManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoFuncaoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;
import com.fortes.rh.web.action.sesmt.HistoricoFuncaoEditAction;
import com.fortes.web.tags.CheckBox;

public class HistoricoFuncaoEditActionTest
{
	private HistoricoFuncaoEditAction action;
	private HistoricoFuncaoManager historicoFuncaoManager;
	private ExameManager exameManager;
	private RiscoManager riscoManager;
	private CursoManager cursoManager;
	private EpiManager epiManager;
	private FuncaoManager funcaoManager;
	private CodigoCBOManager codigoCBOManager;
	private UsuarioAjudaESocialManager usuarioAjudaESocialManager;
	
	@Before
	public void setUp() throws Exception {
		action = new HistoricoFuncaoEditAction();
		
		historicoFuncaoManager = mock(HistoricoFuncaoManager.class);
		action.setHistoricoFuncaoManager(historicoFuncaoManager);
		
		exameManager = mock(ExameManager.class);
		action.setExameManager(exameManager);
		
		epiManager = mock(EpiManager.class);
		action.setEpiManager(epiManager);
		
		riscoManager = mock(RiscoManager.class);
		action.setRiscoManager(riscoManager);
		
		cursoManager = mock(CursoManager.class);
		action.setCursoManager(cursoManager);
		
		funcaoManager = mock(FuncaoManager.class);
		action.setFuncaoManager(funcaoManager);
		
		codigoCBOManager = mock(CodigoCBOManager.class);
		action.setCodigoCBOManager(codigoCBOManager);
		
		usuarioAjudaESocialManager= mock(UsuarioAjudaESocialManager.class);
		action.setUsuarioAjudaESocialManager(usuarioAjudaESocialManager);
	}
	
	@Before
	public void inicializaEmpresaEHistoricoFuncao(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		action.setUsuarioLogado(UsuarioFactory.getEntity(2));
		
		action.setFuncao(FuncaoFactory.getEntity(3L));
		
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
		Date data = null;
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity(1L);
		action.setHistoricoFuncao(historicoFuncao);

		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaoManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaoManager.findById(historicoFuncao.getId())).thenReturn(historicoFuncao);
    	when(funcaoManager.findByIdProjection(action.getFuncao().getId())).thenReturn(action.getFuncao());
    	when(historicoFuncaoManager.findUltimoHistoricoAteData(action.getFuncao().getId(), data)).thenReturn(historicoFuncao);
    	
    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(action.getHistoricoFuncao(), action.getHistoricoFuncao());
    }

	@Test
    public void prepareUpdate() throws Exception
    {
    	when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaoManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	when(funcaoManager.findByIdProjection(action.getFuncao().getId())).thenReturn(action.getFuncao());
    	
    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getHistoricoFuncao(), action.getHistoricoFuncao());
    }

	@Test
    public void insert() throws Exception
    {
		action.setFuncao(FuncaoFactory.getEntity(1L));
    	assertEquals(action.insert(), "success");
    }
	
	@Test
	public void insertException() throws Exception{
		Date data = null;
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity();
		historicoFuncao.setCodigoCbo("012345");
		
		
		doThrow(new Exception()).when(historicoFuncaoManager).saveHistorico(action.getHistoricoFuncao(), action.getExamesChecked(), action.getEpisChecked(), action.getRiscoChecks(), action.getCursosChecked(), action.getRiscosFuncoes());
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaoManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	when(funcaoManager.findByIdProjection(action.getFuncao().getId())).thenReturn(action.getFuncao());
    	when(historicoFuncaoManager.findUltimoHistoricoAteData(action.getFuncao().getId(), data)).thenReturn(historicoFuncao);
    	
 		assertEquals("input", action.insert());
 		assertEquals("Ocorreu um erro ao gravar o histórico da função", action.getActionErrors().iterator().next());
	}
	
	@Test
	public void insertFortesException() throws Exception{
		Date data = null;
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity();
		historicoFuncao.setCodigoCbo("012345");
		
		FortesException fortesException = new FortesException("Ocorreu um erro");
		setDadosParaSalvarHistorico();
		doThrow(fortesException).when(historicoFuncaoManager).saveHistorico(action.getHistoricoFuncao(), action.getExamesChecked(), action.getEpisChecked(), action.getRiscoChecks(), action.getCursosChecked(), action.getRiscosFuncoes());
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaoManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	when(funcaoManager.findByIdProjection(action.getFuncao().getId())).thenReturn(action.getFuncao());
    	when(historicoFuncaoManager.findUltimoHistoricoAteData(action.getFuncao().getId(), data)).thenReturn(historicoFuncao);
    	
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
		action.setFuncao(FuncaoFactory.getEntity(1L));
    	assertEquals(action.update(), "success");
    }
	
	@Test
	public void updateException() throws Exception{
		Date data = null;
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity();
		historicoFuncao.setCodigoCbo("012345");
		
		doThrow(new Exception()).when(historicoFuncaoManager).saveHistorico(action.getHistoricoFuncao(), action.getExamesChecked(), action.getEpisChecked(), action.getRiscoChecks(), action.getCursosChecked(), action.getRiscosFuncoes());
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaoManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	when(funcaoManager.findByIdProjection(action.getFuncao().getId())).thenReturn(action.getFuncao());
		when(historicoFuncaoManager.findUltimoHistoricoAteData(action.getFuncao().getId(), data)).thenReturn(historicoFuncao);
		
 		assertEquals("input", action.update());
 		assertEquals("Ocorreu um erro ao gravar o histórico da função", action.getActionErrors().iterator().next());
	}
	
	@Test
	public void updateFortesException() throws Exception{
		Date data = null;
		FortesException fortesException = new FortesException("Ocorreu um erro");
		setDadosParaSalvarHistorico();
		
		HistoricoFuncao historicoFuncao = HistoricoFuncaoFactory.getEntity();
		historicoFuncao.setCodigoCbo("012345");
    	
		when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(action.getEmpresaSistema().getId(), true)).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaoManager.findById(action.getHistoricoFuncao().getId())).thenReturn(action.getHistoricoFuncao());
    	when(riscoManager.findRiscosFuncoesByEmpresa(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<CheckBox>());
    	when(funcaoManager.findByIdProjection(action.getFuncao().getId())).thenReturn(action.getFuncao());
    	when(historicoFuncaoManager.findUltimoHistoricoAteData(action.getFuncao().getId(), data)).thenReturn(historicoFuncao);
    	doThrow(fortesException).when(historicoFuncaoManager).saveHistorico(action.getHistoricoFuncao(), action.getExamesChecked(), action.getEpisChecked(), action.getRiscoChecks(), action.getCursosChecked(), action.getRiscosFuncoes());
     	
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

    	assertEquals(action.getHistoricoFuncao(), historicoFuncao);
    	assertEquals(action.getFuncao(), funcao);

    	action.getExamesChecked();
    	action.getExamesCheckList();
    }
}