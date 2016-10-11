package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.CandidatoEleicaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.CandidatoEleicaoListAction;

public class CandidatoEleicaoListActionTest extends MockObjectTestCase
{
	private CandidatoEleicaoListAction action;
	private Mock manager;
	private Mock colaboradorManager;
	private Mock areaOrganizacionalManager;
	private Mock eleicaoManager;
	private Mock candidatoEleicaoManager;


    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(CandidatoEleicaoManager.class);
        action = new CandidatoEleicaoListAction();
        action.setCandidatoEleicaoManager((CandidatoEleicaoManager) manager.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
        eleicaoManager = new Mock(EleicaoManager.class);
        action.setEleicaoManager((EleicaoManager) eleicaoManager.proxy());

        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setEleicao(EleicaoFactory.getEntity(1L));
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testList() throws Exception
    {
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	manager.expects(once()).method("getColaboradoresByEleicao").will(returnValue(new ArrayList<CandidatoEleicao>()));
    	eleicaoManager.expects(once()).method("findByIdProjection").will(returnValue(action.getEleicao()));
    	
    	assertEquals(action.list(), "success");
    	assertNotNull(action.getCandidatoEleicaos());
    }

    public void testDelete() throws Exception
    {
    	action.setCandidatoEleicao(CandidatoEleicaoFactory.getEntity(10L));
    	
    	manager.expects(once()).method("remove").with(eq(10L));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	manager.expects(once()).method("getColaboradoresByEleicao").will(returnValue(new ArrayList<CandidatoEleicao>()));
    	//list
    	eleicaoManager.expects(once()).method("findByIdProjection").will(returnValue(action.getEleicao()));

    	assertEquals(action.delete(), "success");
    }

    public void testImprimirComprovanteInscricao() throws Exception
    {
    	CandidatoEleicao candidatoEleicao = CandidatoEleicaoFactory.getEntity(23L);
    	candidatoEleicao.setCandidato(ColaboradorFactory.getEntity(999L));
    	action.setCandidatoEleicao(candidatoEleicao);
    	
    	eleicaoManager.expects(once()).method("findByIdProjection").will(returnValue(action.getEleicao()));
    	colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(eq(candidatoEleicao.getCandidato().getId()), ANYTHING).will(returnValue(candidatoEleicao.getCandidato()));
    	
    	action.imprimirComprovanteInscricao();
    }

    public void testGetSet() throws Exception
    {
    	assertNotNull(action.getCandidatoEleicao());

    	action.getEleicao();

    	action.setNomeBusca("Jéssica");
    	assertEquals("Jéssica",action.getNomeBusca());

    }
}