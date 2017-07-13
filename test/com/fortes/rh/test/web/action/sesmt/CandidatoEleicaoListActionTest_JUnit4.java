package com.fortes.rh.test.web.action.sesmt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.CandidatoEleicaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.CandidatoEleicaoListAction;

import mockit.Mockit;

public class CandidatoEleicaoListActionTest_JUnit4 
{
	private CandidatoEleicaoListAction action;
	private CandidatoEleicaoManager manager;
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EleicaoManager eleicaoManager;
	private CandidatoEleicaoManager candidatoEleicaoManager;

	@Before
    public void setUp() throws Exception
    {
        manager = mock(CandidatoEleicaoManager.class);
        action = new CandidatoEleicaoListAction();
        action.setCandidatoEleicaoManager(manager);

        colaboradorManager = mock(ColaboradorManager.class);
        action.setColaboradorManager(colaboradorManager);
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager(areaOrganizacionalManager);
        
        eleicaoManager = mock(EleicaoManager.class);
        action.setEleicaoManager(eleicaoManager);

        candidatoEleicaoManager = mock(CandidatoEleicaoManager.class);
        action.setCandidatoEleicaoManager(candidatoEleicaoManager);
        
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setEleicao(EleicaoFactory.getEntity(1L));
    }

	@After
    public void tearDown() throws Exception
    {
        manager = null;
        action = null;
    }

	@Test
    public void testPrepareInsert() throws Exception
    {
    	action.setCandidatoEleicao(null);
    	
    	Assert.assertEquals("success", action.prepareInsert());
    	Assert.assertNull(action.getCandidatoEleicao().getId());
    }
    
	@Test
    public void testPrepareUpdate() throws Exception
    {
    	CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity(1L);
    	action.setCandidatoEleicao(candidatoEleicao);
    	
    	when(candidatoEleicaoManager.findById(candidatoEleicao.getId())).thenReturn(candidatoEleicao);
    	
    	Assert.assertEquals("success", action.prepareUpdate());
    	Assert.assertEquals(candidatoEleicao.getId(), action.getCandidatoEleicao().getId());
    }
}