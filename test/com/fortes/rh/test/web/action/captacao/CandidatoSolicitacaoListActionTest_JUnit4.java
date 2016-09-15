package com.fortes.rh.test.web.action.captacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusAutorizacaoGestor;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.CandidatoSolicitacaoListAction;
import com.opensymphony.xwork.Action;

public class CandidatoSolicitacaoListActionTest_JUnit4
{
	private CandidatoSolicitacaoListAction action = new CandidatoSolicitacaoListAction();
	private CandidatoSolicitacaoManager manager;
	private EtapaSeletivaManager etapaSeletivaManager;
	private SolicitacaoManager solicitacaoManager;
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

	@Before
    public void setUp() throws Exception
    {
        manager = mock(CandidatoSolicitacaoManager.class);
        etapaSeletivaManager = mock(EtapaSeletivaManager.class);
        solicitacaoManager = mock(SolicitacaoManager.class);
        solicitacaoAvaliacaoManager = mock(SolicitacaoAvaliacaoManager.class);
        configuracaoNivelCompetenciaManager = mock(ConfiguracaoNivelCompetenciaManager.class);
        historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
        historicoCandidatoManager = mock(HistoricoCandidatoManager.class);
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
        gerenciadorComunicacaoManager= mock(GerenciadorComunicacaoManager.class);
        
        action.setCandidatoSolicitacaoManager(manager);
        action.setEtapaSeletivaManager(etapaSeletivaManager);
        action.setSolicitacaoManager(solicitacaoManager);
        action.setSolicitacaoAvaliacaoManager(solicitacaoAvaliacaoManager);
        action.setConfiguracaoNivelCompetenciaManager(configuracaoNivelCompetenciaManager);
        action.setHistoricoColaboradorManager(historicoColaboradorManager);
        action.setHistoricoCandidatoManager(historicoCandidatoManager);
        action.setAreaOrganizacionalManager(areaOrganizacionalManager);
        action.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
        action.setGerenciadorComunicacaoManager(gerenciadorComunicacaoManager);
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }
	
	@After
	public void tearDown() throws Exception
    {
        action = null;
        MockSecurityUtil.verifyRole = false;
        Mockit.restoreAllOriginalDefinitions();
    }

	@Test
    public void testPrepareAprovarReprovarColabSolicitacaoPessoal(){
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
    	
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	action.setPage(1);
    	
    	Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
    	areasOrganizacionais.add(areaOrganizacional);
    	
    	Collection<CandidatoSolicitacao> candidatoSolicitacoes = new ArrayList<CandidatoSolicitacao>();
    	candidatoSolicitacoes.add(candidatoSolicitacao);
    	
    	when(areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, action.getEmpresaSistema().getId())).thenReturn(areasOrganizacionais);
    	when(manager.findColaboradorParticipantesDaSolicitacaoByAreas(areasOrganizacionais, null, null, 'T', null, null)).thenReturn(candidatoSolicitacoes);
    	when(manager.findColaboradorParticipantesDaSolicitacaoByAreas(areasOrganizacionais, null, null, 'T', 1, 15)).thenReturn(candidatoSolicitacoes);
    	
    	assertEquals(Action.SUCCESS, action.prepareAutorizarColabSolicitacaoPessoal());
    }
    
    @Test
	public void testPrepareAprovarReprovarColabSolicitacaoPessoalException(){
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	action.setPage(1);
    	
    	when(areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, action.getEmpresaSistema().getId())).thenThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("","")));
    	
    	action.prepareAutorizarColabSolicitacaoPessoal();
    	
    	assertEquals("Ocorreu uma inconsistência ao carregar a tela.", action.getActionErrors().toArray()[0]);
    }
    
    @Test
    public void testAprovarReprovarColabSolicitacaoPessoal() throws Exception{
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
    	candidatoSolicitacao.setStatusAutorizacaoGestor(StatusAutorizacaoGestor.ANALISE);
    	
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	action.setPage(1);
    	action.setCandidatoSolicitacao(candidatoSolicitacao);
    	action.setUsuarioLogado(UsuarioFactory.getEntity(2L));
    	
    	Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
    	areasOrganizacionais.add(areaOrganizacional);
    	
    	Collection<CandidatoSolicitacao> candidatoSolicitacoes = new ArrayList<CandidatoSolicitacao>();
    	candidatoSolicitacoes.add(candidatoSolicitacao);
    	
    	CandidatoSolicitacao candidatoSolicitacaoAnterior = (CandidatoSolicitacao) candidatoSolicitacao.clone();
    	candidatoSolicitacaoAnterior.setStatusAutorizacaoGestor(StatusAutorizacaoGestor.AUTORIZADO);
    	
    	when(candidatoSolicitacaoManager.findById(candidatoSolicitacao.getId())).thenReturn(candidatoSolicitacaoAnterior);
    	when(areaOrganizacionalManager.findAreasByUsuarioResponsavel(action.getUsuarioLogado(), action.getEmpresaSistema().getId())).thenReturn(areasOrganizacionais);
    	when(manager.findColaboradorParticipantesDaSolicitacaoByAreas(areasOrganizacionais, null, null, 'T', null, null)).thenReturn(candidatoSolicitacoes);
    	when(manager.findColaboradorParticipantesDaSolicitacaoByAreas(areasOrganizacionais, null, null, 'T', 1, 15)).thenReturn(candidatoSolicitacoes);
    	
    	assertEquals(Action.SUCCESS, action.autorizarColabSolicitacaoPessoal());
    	assertEquals("Status gravado com sucesso.", action.getActionMessages().toArray()[0]);
    }
    
    @Test
    public void testAprovarReprovarColabSolicitacaoPessoalExceptin(){
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
    	
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	action.setPage(1);
    	
    	CandidatoSolicitacao candidatoSolicitacaoAnterior = (CandidatoSolicitacao) candidatoSolicitacao.clone();
    	candidatoSolicitacaoAnterior.setStatusAutorizacaoGestor(StatusAutorizacaoGestor.AUTORIZADO);
    	
    	when(candidatoSolicitacaoManager.findById(candidatoSolicitacao.getId())).thenThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("","")));

    	action.autorizarColabSolicitacaoPessoal();
    	
    	assertEquals("Ocorreu uma inconsistência ao tentar gravar o status.", action.getActionErrors().toArray()[0]);
    }
}