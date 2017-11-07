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
import com.fortes.rh.business.geral.UsuarioAjudaESocialManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.dicionario.TelaAjudaESocial;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
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
	private UsuarioAjudaESocialManager usuarioAjudaESocialManager;

	@Before
    public void setUp()
    {
        historicoFuncaoManager = mock(HistoricoFuncaoManager.class);
        funcaoManager = mock(FuncaoManager.class);
        exameManager = mock(ExameManager.class);
        epiManager = mock(EpiManager.class);
        riscoManager = mock(RiscoManager.class);
        cursoManager = mock(CursoManager.class);
        usuarioAjudaESocialManager = mock(UsuarioAjudaESocialManager.class);

        action = new FuncaoEditAction();
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setFuncaoManager(funcaoManager);
        action.setHistoricoFuncaoManager(historicoFuncaoManager);
        action.setUsuarioAjudaESocialManager(usuarioAjudaESocialManager);
        action.setExameManager(exameManager);
        action.setEpiManager(epiManager);
        action.setRiscoManager(riscoManager);
        action.setCursoManager(cursoManager);

        PowerMockito.mockStatic(SecurityUtil.class);
        PowerMockito.mockStatic(CheckListBoxUtil.class);
        
        action.setUsuarioLogado(UsuarioFactory.getEntity(2L));
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
    	
    	when(usuarioAjudaESocialManager.verifyExists(new String[]{"usuario.id", "telaAjuda"},new Object[]{action.getUsuarioLogado().getId(), TelaAjudaESocial.EDICAO_HISTORICO_FUNCAO})).thenReturn(false); 
    	
    	assertEquals(action.prepareInsert(), "success");
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