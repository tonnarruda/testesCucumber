package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.CandidatoSolicitacaoListAction;
import com.opensymphony.xwork.Action;

public class CandidatoSolicitacaoListActionTest extends MockObjectTestCase
{
	private CandidatoSolicitacaoListAction action;
	private Mock manager;
	private Mock etapaSeletivaManager;
	private Mock solicitacaoManager;
	private Mock solicitacaoAvaliacaoManager;
	private Mock configuracaoNivelCompetenciaManager;
	private Mock historicoColaboradorManager;
	private Mock historicoCandidatoManager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorManager;
	
    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CandidatoSolicitacaoListAction();
        manager = new Mock(CandidatoSolicitacaoManager.class);
        etapaSeletivaManager = new Mock(EtapaSeletivaManager.class);
        solicitacaoManager = new Mock(SolicitacaoManager.class);
        solicitacaoAvaliacaoManager = new Mock(SolicitacaoAvaliacaoManager.class);
        configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
        historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
        historicoCandidatoManager = new Mock(HistoricoCandidatoManager.class);
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        
        action.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) manager.proxy());
        action.setEtapaSeletivaManager((EtapaSeletivaManager) etapaSeletivaManager.proxy());
        action.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());
        action.setSolicitacaoAvaliacaoManager((SolicitacaoAvaliacaoManager) solicitacaoAvaliacaoManager.proxy());
        action.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
        action.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
        action.setHistoricoCandidatoManager((HistoricoCandidatoManager) historicoCandidatoManager.proxy());
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testList() throws Exception
    {
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresaSistema);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	solicitacao.setFaixaSalarial(faixaSalarial);
    	action.setSolicitacao(solicitacao);
    	
    	CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity(1L);
    	Collection<CandidatoSolicitacao> candidatoSolicitacaos = Arrays.asList(candidatoSolicitacao1);
    	Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new ArrayList<ConfiguracaoNivelCompetencia>();
    	
    	etapaSeletivaManager.expects(once()).method("findAllSelect").with(eq(empresaSistema.getId())).will(returnValue(new ArrayList<Estado>()));
    	solicitacaoManager.expects(once()).method("getValor").with(eq(solicitacao.getId())).will(returnValue(solicitacao));
    	manager.expects(once()).method("getCount").withAnyArguments().will(returnValue(candidatoSolicitacaos.size()));
    	manager.expects(once()).method("getCandidatoSolicitacaoList").withAnyArguments().will(returnValue(candidatoSolicitacaos));
    	solicitacaoAvaliacaoManager.expects(once()).method("findBySolicitacaoId").with(eq(solicitacao.getId()), eq(null));
    	configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(solicitacao.getFaixaSalarial().getId()), ANYTHING).will(returnValue(configuracaoNivelCompetencias));
    	colaboradorManager.expects(once()).method("excedeuContratacoes").with(eq(empresaSistema.getId())).will(returnValue(false));
    	
    	assertEquals(Action.SUCCESS, action.list());
    }
    
    public void testListTestandoCondicoesComDadosRetornados() throws Exception
    {
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresaSistema);
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	solicitacao.setFaixaSalarial(faixaSalarial);
    	action.setSolicitacao(solicitacao);
    	action.setPage(2);    	
    	action.setVisualizar('A');
    	
    	Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
    	Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new ArrayList<ConfiguracaoNivelCompetencia>();
    	
    	etapaSeletivaManager.expects(once()).method("findAllSelect").with(eq(empresaSistema.getId())).will(returnValue(new ArrayList<Estado>()));
    	solicitacaoManager.expects(once()).method("getValor").with(eq(solicitacao.getId())).will(returnValue(solicitacao));
    	manager.expects(once()).method("getCount").withAnyArguments().will(returnValue(candidatoSolicitacaos.size()));
    	manager.expects(atLeastOnce()).method("getCandidatoSolicitacaoList").withAnyArguments().will(returnValue(candidatoSolicitacaos));
    	solicitacaoAvaliacaoManager.expects(once()).method("findBySolicitacaoId").with(eq(solicitacao.getId()), eq(null));
    	configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(solicitacao.getFaixaSalarial().getId()), ANYTHING).will(returnValue(configuracaoNivelCompetencias));
    	colaboradorManager.expects(once()).method("excedeuContratacoes").with(eq(empresaSistema.getId())).will(returnValue(false));
    	
    	assertEquals(Action.SUCCESS, action.list());
    	assertTrue(action.getActionMessages().isEmpty());
    }
    
    public void testListTestandoCondicoesSemDadosRetornados() throws Exception
    {
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresaSistema);
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	solicitacao.setFaixaSalarial(faixaSalarial);
    	action.setSolicitacao(solicitacao);
    	action.setVisualizar('N');
    	
    	Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
    	Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new ArrayList<ConfiguracaoNivelCompetencia>();
    	
    	etapaSeletivaManager.expects(once()).method("findAllSelect").with(eq(empresaSistema.getId())).will(returnValue(new ArrayList<Estado>()));
    	solicitacaoManager.expects(once()).method("getValor").with(eq(solicitacao.getId())).will(returnValue(solicitacao));
    	manager.expects(once()).method("getCount").withAnyArguments().will(returnValue(candidatoSolicitacaos.size()));
    	manager.expects(once()).method("getCandidatoSolicitacaoList").withAnyArguments().will(returnValue(candidatoSolicitacaos));
    	solicitacaoAvaliacaoManager.expects(once()).method("findBySolicitacaoId").with(eq(solicitacao.getId()), eq(null));
    	configuracaoNivelCompetenciaManager.expects(once()).method("findByFaixa").with(eq(solicitacao.getFaixaSalarial().getId()), ANYTHING).will(returnValue(configuracaoNivelCompetencias));
    	colaboradorManager.expects(once()).method("excedeuContratacoes").with(eq(empresaSistema.getId())).will(returnValue(false));
    	
    	assertEquals(Action.SUCCESS, action.list());
    	assertEquals("Não existem candidatos para o filtro informado.", ((String) action.getActionMessages().toArray()[0]));
    }
    
    public void testListTriagem() throws Exception
    {
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	action.setSolicitacao(solicitacao);
    	
    	Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
    	
    	solicitacaoManager.expects(once()).method("getValor").with(eq(solicitacao.getId())).will(returnValue(solicitacao));
    	manager.expects(once()).method("findBySolicitacaoTriagem").with(eq(solicitacao.getId())).will(returnValue(candidatoSolicitacaos));
    	
    	assertEquals(Action.SUCCESS, action.listTriagem());
    }
    
    public void testVerHistoricoCandidato() throws Exception
    {
		Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();
		Collection<SolicitacaoHistoricoColaborador> solicitacaoHistoricoColaboradors = new ArrayList<SolicitacaoHistoricoColaborador>();
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		action.setCandidato(candidato);
		
		historicoCandidatoManager.expects(once()).method("findByCandidato").with(eq(candidato)).will(returnValue(historicoCandidatos));
		historicoCandidatoManager.expects(once()).method("montaMapaHistorico").with(eq(historicoCandidatos)).will(returnValue(solicitacaoHistoricoColaboradors));
    	
    	assertEquals(Action.SUCCESS, action.verHistoricoCandidato());
    }
}