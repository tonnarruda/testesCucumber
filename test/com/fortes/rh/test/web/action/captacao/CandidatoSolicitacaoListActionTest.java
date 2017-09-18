package com.fortes.rh.test.web.action.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.exception.LimiteColaboradorExcedidoException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.ModelUtil;
import com.fortes.rh.web.action.captacao.CandidatoSolicitacaoListAction;
import com.opensymphony.xwork.Action;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class, ModelUtil.class})

public class CandidatoSolicitacaoListActionTest
{
	private CandidatoSolicitacaoListAction action;
	private CandidatoSolicitacaoManager manager;
	private EtapaSeletivaManager etapaSeletivaManager;
	private SolicitacaoManager solicitacaoManager;
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	
    @Before
	public void setUp() throws Exception
    {
        action = new CandidatoSolicitacaoListAction();
        manager = mock(CandidatoSolicitacaoManager.class);
        etapaSeletivaManager = mock(EtapaSeletivaManager.class);
        solicitacaoManager = mock(SolicitacaoManager.class);
        solicitacaoAvaliacaoManager = mock(SolicitacaoAvaliacaoManager.class);
        configuracaoNivelCompetenciaManager = mock(ConfiguracaoNivelCompetenciaManager.class);
        historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
        historicoCandidatoManager = mock(HistoricoCandidatoManager.class);
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        colaboradorManager = mock(ColaboradorManager.class);
        quantidadeLimiteColaboradoresPorCargoManager = mock(QuantidadeLimiteColaboradoresPorCargoManager.class);
        
        action.setCandidatoSolicitacaoManager(manager);
        action.setEtapaSeletivaManager(etapaSeletivaManager);
        action.setSolicitacaoManager(solicitacaoManager);
        action.setSolicitacaoAvaliacaoManager(solicitacaoAvaliacaoManager);
        action.setConfiguracaoNivelCompetenciaManager(configuracaoNivelCompetenciaManager);
        action.setHistoricoColaboradorManager(historicoColaboradorManager);
        action.setHistoricoCandidatoManager(historicoCandidatoManager);
        action.setAreaOrganizacionalManager(areaOrganizacionalManager);
        action.setColaboradorManager(colaboradorManager);
        action.setQuantidadeLimiteColaboradoresPorCargoManager(quantidadeLimiteColaboradoresPorCargoManager);
        
    	PowerMockito.mockStatic(SecurityUtil.class, ModelUtil.class);
    }

    @After
    public void tearDown() throws Exception
    {
        manager = null;
        action = null;
    }

    @Test
    public void testList() throws Exception
    {
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresaSistema(empresaSistema);
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial.setCargo(CargoFactory.getEntity("Cargo Nome"));
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setConsiderarQtdColaboradoresPorCargo(true);
    	
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	solicitacao.setFaixaSalarial(faixaSalarial);
    	solicitacao.setMotivoSolicitacao(motivoSolicitacao);
    	solicitacao.setAreaOrganizacional(areaOrganizacional);
    	action.setSolicitacao(solicitacao);
    	
    	CandidatoSolicitacao candidatoSolicitacao1 = CandidatoSolicitacaoFactory.getEntity(1L);
    	Collection<CandidatoSolicitacao> candidatoSolicitacaos = Arrays.asList(candidatoSolicitacao1);
    	Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new ArrayList<ConfiguracaoNivelCompetencia>();
    	
    	when(etapaSeletivaManager.findAllSelect(empresaSistema.getId())).thenReturn(new ArrayList<EtapaSeletiva>());
    	when(solicitacaoManager.getValor(solicitacao.getId())).thenReturn(solicitacao);
    	when(manager.getCount(solicitacao.getId(), action.getEtapaSeletivaId(), action.getIndicadoPor(), action.getValueApto(action.getVisualizar()), false, true, action.getObservacaoRH(), null, action.getVisualizar())).thenReturn(candidatoSolicitacaos.size());
    	when(manager.getCandidatoSolicitacaoList(action.getPage(), action.getPagingSize(), solicitacao.getId(), action.getEtapaSeletivaId(), action.getIndicadoPor(), action.getValueApto(action.getVisualizar()), false, true, action.getObservacaoRH(), null, action.getVisualizar())).thenReturn(candidatoSolicitacaos);
    	when(solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacao.getId(),null)).thenReturn(new ArrayList<SolicitacaoAvaliacao>());
    	when(configuracaoNivelCompetenciaManager.findByFaixa(solicitacao.getFaixaSalarial().getId(), null)).thenReturn(configuracaoNivelCompetencias);
    	when(colaboradorManager.excedeuContratacao(empresaSistema.getId())).thenReturn(false);
    	when(ModelUtil.getValor(solicitacao, "getMotivoSolicitacao().isConsiderarQtdColaboradoresPorCargo()", Boolean.FALSE)).thenReturn(Boolean.TRUE);
    	doThrow(LimiteColaboradorExcedidoException.class).when(quantidadeLimiteColaboradoresPorCargoManager).validaLimite(solicitacao.getAreaOrganizacional().getId(), solicitacao.getFaixaSalarial().getId(), action.getEmpresaSistema().getId(), null);
    	when(ModelUtil.hasNotNull("getFaixaSalarial().getCargo()", solicitacao)).thenReturn(Boolean.TRUE);
    	
    	assertEquals(Action.SUCCESS, action.list());
    	assertEquals("O limite de colaboradores cadastrados para o cargo \""+faixaSalarial.getCargo().getNome()+"\" foi atingido de acordo com a configuração existente.", ((String) action.getActionMessages().toArray()[0]));
    }
    
    @Test
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
    	
    	when(etapaSeletivaManager.findAllSelect(empresaSistema.getId())).thenReturn(new ArrayList<EtapaSeletiva>());
    	when(solicitacaoManager.getValor(solicitacao.getId())).thenReturn(solicitacao);
    	when(manager.getCount(solicitacao.getId(), action.getEtapaSeletivaId(), action.getIndicadoPor(), action.getValueApto(action.getVisualizar()), false, true, action.getObservacaoRH(), null, action.getVisualizar())).thenReturn(candidatoSolicitacaos.size());
    	when(manager.getCandidatoSolicitacaoList(action.getPage(), action.getPagingSize(), solicitacao.getId(), action.getEtapaSeletivaId(), action.getIndicadoPor(), action.getValueApto(action.getVisualizar()), false, true, action.getObservacaoRH(), null, action.getVisualizar())).thenReturn(candidatoSolicitacaos);
    	when(solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacao.getId(),null)).thenReturn(new ArrayList<SolicitacaoAvaliacao>());
    	when(configuracaoNivelCompetenciaManager.findByFaixa(solicitacao.getFaixaSalarial().getId(), null)).thenReturn(configuracaoNivelCompetencias);
    	when(colaboradorManager.excedeuContratacao(empresaSistema.getId())).thenReturn(false);
    	when(ModelUtil.getValor(solicitacao, "getMotivoSolicitacao().isConsiderarQtdColaboradoresPorCargo()", Boolean.FALSE)).thenReturn(Boolean.FALSE);

    	assertEquals(Action.SUCCESS, action.list());
    	assertTrue(action.getActionMessages().isEmpty());
    }
    
    @Test
    public void testListTestandoCondicoesSemDadosRetornadosSemConsiderarQtdColaboradoresPorCargo() throws Exception
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
    	
    	when(etapaSeletivaManager.findAllSelect(empresaSistema.getId())).thenReturn(new ArrayList<EtapaSeletiva>());
    	when(solicitacaoManager.getValor(solicitacao.getId())).thenReturn(solicitacao);
    	when(manager.getCount(solicitacao.getId(), action.getEtapaSeletivaId(), action.getIndicadoPor(), action.getValueApto(action.getVisualizar()), false, true, action.getObservacaoRH(), null, action.getVisualizar())).thenReturn(candidatoSolicitacaos.size());
    	when(manager.getCandidatoSolicitacaoList(action.getPage(), action.getPagingSize(), solicitacao.getId(), action.getEtapaSeletivaId(), action.getIndicadoPor(), action.getValueApto(action.getVisualizar()), false, true, action.getObservacaoRH(), null, action.getVisualizar())).thenReturn(candidatoSolicitacaos);
    	when(solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacao.getId(),null)).thenReturn(new ArrayList<SolicitacaoAvaliacao>());
    	when(configuracaoNivelCompetenciaManager.findByFaixa(solicitacao.getFaixaSalarial().getId(), null)).thenReturn(configuracaoNivelCompetencias);
    	when(colaboradorManager.excedeuContratacao(empresaSistema.getId())).thenReturn(false);
    	when(ModelUtil.getValor(solicitacao, "getMotivoSolicitacao().isConsiderarQtdColaboradoresPorCargo()", Boolean.FALSE)).thenReturn(Boolean.FALSE);

    	assertEquals(Action.SUCCESS, action.list());
    	assertEquals("Não existem candidatos para o filtro informado.", ((String) action.getActionMessages().toArray()[0]));
    }
    
    @Test
    public void testListTriagem() throws Exception
    {
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
    	action.setSolicitacao(solicitacao);
    	
    	Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
    	
    	when(solicitacaoManager.getValor(solicitacao.getId())).thenReturn(solicitacao);
    	when(manager.findBySolicitacaoTriagem(solicitacao.getId())).thenReturn(candidatoSolicitacaos);
    	
    	assertEquals(Action.SUCCESS, action.listTriagem());
    }
    
    @Test
    public void testVerHistoricoCandidato() throws Exception
    {
		Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();
		Collection<SolicitacaoHistoricoColaborador> solicitacaoHistoricoColaboradors = new ArrayList<SolicitacaoHistoricoColaborador>();
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		action.setCandidato(candidato);
		
		when(historicoCandidatoManager.findByCandidato(candidato)).thenReturn(historicoCandidatos);
		when(historicoCandidatoManager.montaMapaHistorico(historicoCandidatos)).thenReturn(solicitacaoHistoricoColaboradors);
    	
    	assertEquals(Action.SUCCESS, action.verHistoricoCandidato());
    }
}