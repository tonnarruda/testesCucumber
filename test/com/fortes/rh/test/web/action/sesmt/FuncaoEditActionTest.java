package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.sesmt.FuncaoEditAction;
import com.fortes.web.tags.CheckBox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class,CheckListBoxUtil.class})
public class FuncaoEditActionTest 
{
	private FuncaoEditAction action;
	private HistoricoFuncaoManager historicoFuncaoManager;
	private FuncaoManager funcaoManager;
	private ExameManager exameManager;
	private EpiManager epiManager;
	private RiscoManager riscoManager;
	private CursoManager cursoManager;
	private CodigoCBOManager codigoCBOManager;

	@Before
    public void setUp()
    {
        historicoFuncaoManager = mock(HistoricoFuncaoManager.class);
        funcaoManager = mock(FuncaoManager.class);
        exameManager = mock(ExameManager.class);
        epiManager = mock(EpiManager.class);
        riscoManager = mock(RiscoManager.class);
        cursoManager = mock(CursoManager.class);
        codigoCBOManager = mock(CodigoCBOManager.class);

        action = new FuncaoEditAction();
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setFuncaoManager(funcaoManager);
        action.setHistoricoFuncaoManager(historicoFuncaoManager);
        action.setExameManager(exameManager);
        action.setEpiManager(epiManager);
        action.setRiscoManager(riscoManager);
        action.setCursoManager(cursoManager);
        action.setCodigoCBOManager(codigoCBOManager);

        PowerMockito.mockStatic(SecurityUtil.class);
        PowerMockito.mockStatic(CheckListBoxUtil.class);
    }

	@Test
    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    @Test
    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(eq(empresa.getId()), eq(true))).thenReturn(new ArrayList<CheckBox>());
    	when(riscoManager.findRiscosFuncoesByEmpresa(eq(empresa.getId()))).thenReturn(new ArrayList<RiscoFuncao>());
    	when(cursoManager.populaCheckListCurso(eq(empresa.getId()))).thenReturn(new ArrayList<CheckBox>());
    	
    	assertEquals(action.prepareInsert(), "success");
    	assertNotNull(action.getExamesCheckList());
    }

    @Test
    public void testPrepareUpdate() throws Exception
    {
    	Funcao funcaoRetorno = FuncaoFactory.getEntity(1L);
    	action.setFuncao(funcaoRetorno);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresa);
    	
    	Collection<HistoricoFuncao> historicoFuncaos = new ArrayList<HistoricoFuncao>();
    	HistoricoFuncao hf1 = new HistoricoFuncao();
    	hf1.setId(2L);
    	HistoricoFuncao hf2 = new HistoricoFuncao();
    	hf2.setId(2L);

    	historicoFuncaos.add(hf1);
    	historicoFuncaos.add(hf2);
    	action.setHistoricoFuncaos(historicoFuncaos);

    	when(exameManager.findByEmpresaComAsoPadrao(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Exame>());
    	when(epiManager.populaCheckToEpi(eq(empresa.getId()), eq(true))).thenReturn(new ArrayList<CheckBox>());
    	when(funcaoManager.findByIdProjection(eq(funcaoRetorno.getId()))).thenReturn(funcaoRetorno);
    	when(cursoManager.populaCheckListCurso(eq(action.getEmpresaSistema().getId()))).thenReturn(new ArrayList<CheckBox>());
    	when(historicoFuncaoManager.findToList(new String[]{"id","descricao","data"}, new String[]{"id","descricao","data"}, new String[]{"funcao.id"}, new Object[]{action.getFuncao().getId()}, new String[]{"data desc"})).thenReturn(historicoFuncaos);

    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(action.getFuncao(), funcaoRetorno);
    	assertEquals(action.getHistoricoFuncaos(), historicoFuncaos);
    	assertNotNull(action.getExamesCheckList());
    }

    @Test
    public void testInsert() throws Exception
    {
    	
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setControlaRiscoPor('A');
    	action.setEmpresaSistema(empresa);

    	Funcao funcaoRetorno = new Funcao();
    	funcaoRetorno.setId(1L);

    	action.setFuncao(funcaoRetorno);

    	HistoricoFuncao historicoFuncao = new HistoricoFuncao();
    	historicoFuncao.setId(2L);

    	action.setHistoricoFuncao(historicoFuncao);

    	Long[] examesChecked = new Long[]{1L};
    	action.setExamesChecked(examesChecked);

    	Long[] episChecked = new Long[]{1L};
    	action.setEpisChecked(episChecked);
    	
    	Long[] cursosChecked = new Long[]{1L};
    	action.setCursosChecked(cursosChecked);
    	
    	String[] riscoChecks = new String[]{"822", "823"};
    	Collection<RiscoFuncao> riscosFuncoes = RiscoFuncaoFactory.getCollection();
    	action.setRiscoChecks(riscoChecks);
		action.setRiscosFuncoes(riscosFuncoes);

    	assertEquals(action.insert(), "success");
    }

    @Test
    public void testUpdate() throws Exception
    {
    	Funcao funcaoRetorno = new Funcao();
    	funcaoRetorno.setId(1L);

    	action.setFuncao(funcaoRetorno);

    	assertEquals(action.update(), "success");
    	assertEquals(action.getFuncao(), funcaoRetorno);
    }
  
    @Test
    public void testGetSet() throws Exception
    {
    	Funcao funcao = new Funcao();
    	funcao.setId(1L);

    	action.setFuncao(funcao);

    	assertEquals(action.getFuncao(), funcao);

    	funcao = null;
    	action.setFuncao(funcao);
    	assertTrue(action.getFuncao() instanceof Funcao);

    	action.setAmbientes(null);
    	action.getAmbientes();
    	action.getColaborador();
    	action.getHistoricoColaborador();
    	action.getFuncaos();
    	action.setFuncaos(null);
    	action.getPage();
    	action.setPage(1);
    	action.getAreaBusca();
    	action.setAreaBusca(null);
    	action.getNomeBusca();
    	action.setNomeBusca("");
    	action.getExamesChecked();
    }
}