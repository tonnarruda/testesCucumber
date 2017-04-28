package com.fortes.rh.test.web.action.captacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

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
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.StatusAutorizacaoGestor;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
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
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

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
        colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
        
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
        action.setColaboradorQuestionarioManager(colaboradorQuestionarioManager);
        
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
    
    @Test
    public void testRemoverCandidatoDaSolicitacaoException(){
    	Exception exception = new Exception();
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	action.setSolicitacao(solicitacao);
    	action.setCandidatoSolicitacao(candidatoSolicitacao);
    	
    	doThrow(Exception.class).when(candidatoSolicitacaoManager).remove(new Long[]{action.getCandidatoSolicitacao().getId()});
    	
    	assertEquals("success", action.removerCandidatoDaSolicitacao());
    	assertEquals("Erro ao excluír o candidato do processo seletivo: " + exception.getMessage(), action.getActionErrors().iterator().next());
	}
    
    @Test
    public void testRemoverCandidatoDaSolicitacao(){
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	action.setSolicitacao(solicitacao);
    	action.setCandidatoSolicitacao(candidatoSolicitacao);
    	
    	assertEquals("success", action.removerCandidatoDaSolicitacao());
    	assertEquals("Candidato excluído do processo seletivo.", action.getActionSuccess().iterator().next());
	}
    
    @Test
    public void testRemoverTriagemComCandidatosSelecionados() throws Exception
    {
    	Long[] candidatoSolicitacaoIdsSelecionados = new Long[] {1L};
    	action.setCandidatoSolicitacaoIdsSelecionados(candidatoSolicitacaoIdsSelecionados);
    	assertEquals(Action.SUCCESS, action.removerTriagem());
    	assertEquals("Candidato(s) inserido(s) no processo selectivo com sucesso.", action.getActionSuccess().iterator().next());
    }
    
    @Test
    public void testRemoverTriagemSemCandidatosSelecionados() throws Exception
    {
    	Long[] candidatoSolicitacaoIdsSelecionados = new Long[] {};
    	action.setCandidatoSolicitacaoIdsSelecionados(candidatoSolicitacaoIdsSelecionados);
    	
    	assertEquals(Action.SUCCESS, action.removerTriagem());
    }
    
    @Test
    public void testRemoverTriagemException() throws Exception
    {
    	Exception exception = new Exception();
    	Long[] candidatoSolicitacaoIdsSelecionados = new Long[] {1L};
    	action.setCandidatoSolicitacaoIdsSelecionados(candidatoSolicitacaoIdsSelecionados);
    	
    	doThrow(Exception.class).when(candidatoSolicitacaoManager).updateTriagem(candidatoSolicitacaoIdsSelecionados, false);
    	
    	assertEquals(Action.SUCCESS, action.removerTriagem());
    	assertEquals("Erro ao inserir o(s) candidato(s) no processo seletivo: " + exception.getMessage(), action.getActionErrors().iterator().next());
    }
    
    @Test
    public void testRemoverCandidatosDaSolicitacao()
	{
    	Long[] candidatoSolicitacaoIdsSelecionados = new Long[] {1L};
    	action.setCandidatoSolicitacaoIdsSelecionados(candidatoSolicitacaoIdsSelecionados);
    	assertEquals(Action.SUCCESS, action.removerCandidatosDaSolicitacao());
    	assertEquals("Candidato(s) excluído(s) do processo seletivo.", action.getActionSuccess().iterator().next());
	}
    
    @Test
    public void testRemoverCandidatosDaSolicitacaoException() throws Exception
    {
    	Exception exception = new Exception();
    	Long[] candidatoSolicitacaoIdsSelecionados = new Long[] {};
    	action.setCandidatoSolicitacaoIdsSelecionados(candidatoSolicitacaoIdsSelecionados);
    	
    	doThrow(Exception.class).when(candidatoSolicitacaoManager).remove(candidatoSolicitacaoIdsSelecionados);
    	
    	assertEquals(Action.SUCCESS, action.removerCandidatosDaSolicitacao());
    	assertEquals("Erro ao remover candidatos do processo seletivo: " + exception.getMessage(), action.getActionErrors().iterator().next());
    }
    
    @Test
    public void testDelete() throws Exception
    {
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	Candidato candidato = CandidatoFactory.getCandidato(1L);
    	
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
    	candidatoSolicitacao.setSolicitacao(solicitacao);
    	candidatoSolicitacao.setCandidato(candidato);
    	
    	action.setCandidatoSolicitacao(candidatoSolicitacao);
    	when(candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacao.getId())).thenReturn(candidatoSolicitacao);
    	
    	assertEquals(Action.SUCCESS, action.delete());
    	assertEquals("Candidato excluído do processo seletivo.", action.getActionSuccess().iterator().next());    	
    }
    
    @Test
    public void testDeleteException() throws Exception
    {
    	Exception exception = new Exception();
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	Candidato candidato = CandidatoFactory.getCandidato(1L);
    	
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
    	candidatoSolicitacao.setSolicitacao(solicitacao);
    	candidatoSolicitacao.setCandidato(candidato);
    	
    	action.setCandidatoSolicitacao(candidatoSolicitacao);
    	when(candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacao.getId())).thenReturn(candidatoSolicitacao);
    	doThrow(Exception.class).when(candidatoSolicitacaoManager).remove(new Long[]{action.getCandidatoSolicitacao().getId()});
    	assertEquals(Action.SUCCESS, action.delete());
    	assertEquals("Erro ao excluír o candidato do processo seletivo: " + exception.getMessage(), action.getActionErrors().iterator().next());
    }
}