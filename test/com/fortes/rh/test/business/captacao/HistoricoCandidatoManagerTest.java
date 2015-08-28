package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManagerImpl;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.EventoAgenda;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.captacao.relatorio.ProdutividadeRelatorio;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.factory.captacao.HistoricoCandidatoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class HistoricoCandidatoManagerTest extends MockObjectTestCase
{
	private HistoricoCandidatoManagerImpl historicoCandidatoManager = new HistoricoCandidatoManagerImpl();
	private Mock transactionManager;
	private Mock historicoCandidatoDao;
	private Mock candidatoManager;
	private Mock etapaSeletivaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        transactionManager = new Mock(PlatformTransactionManager.class);
        historicoCandidatoManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        historicoCandidatoDao = new Mock(HistoricoCandidatoDao.class);
        historicoCandidatoManager.setDao((HistoricoCandidatoDao) historicoCandidatoDao.proxy());

        candidatoManager = new Mock(CandidatoManager.class);
        historicoCandidatoManager.setCandidatoManager((CandidatoManager) candidatoManager.proxy());

        etapaSeletivaManager = new Mock(EtapaSeletivaManager.class);
        historicoCandidatoManager.setEtapaSeletivaManager((EtapaSeletivaManager) etapaSeletivaManager.proxy());


        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
        super.tearDown();
    }
    
    public void testProcessoSeletivoRelatorio()
    {
    	ProcessoSeletivoRelatorio processoSeletivoRelatorio = new ProcessoSeletivoRelatorio();
    	processoSeletivoRelatorio.addQtdParticipantes(7, 5.0, false);
    	processoSeletivoRelatorio.addQtdParticipantes(7, 5.0, true);
    	
    	assertEquals(50.00, processoSeletivoRelatorio.getQtdAprovJul());
    }

	public void testFindByCandidatoCollect()
	{
    	Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();
    	Collection<CandidatoSolicitacao> candidatos = new ArrayList<CandidatoSolicitacao>();

    	historicoCandidatoDao.expects(once()).method("findByCandidato").with(ANYTHING).will(returnValue(historicoCandidatos));

    	assertEquals(historicoCandidatos, historicoCandidatoManager.findByCandidato(candidatos));

	}

	public void testFindList()
	{
		CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
		Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();

    	historicoCandidatoDao.expects(once()).method("findList").with(ANYTHING).will(returnValue(historicoCandidatos));

    	assertEquals(historicoCandidatos, historicoCandidatoManager.findList(candidatoSolicitacao));

	}

	public void testFindByPeriodo()
	{
		Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();

    	historicoCandidatoDao.expects(once()).method("findByPeriodo").with(ANYTHING).will(returnValue(historicoCandidatos));

    	assertEquals(historicoCandidatos, historicoCandidatoManager.findByPeriodo(new HashMap()));

	}
	
	public void testUpdateAgenda()
	{
		historicoCandidatoDao.expects(once()).method("updateAgenda").withAnyArguments().will(returnValue(true));
		assertTrue(historicoCandidatoManager.updateAgenda(null, null, null, null, null));
	}
	
	public void testFindResponsaveis()
	{
		historicoCandidatoDao.expects(once()).method("findResponsaveis").will(returnValue(new String[]{}));
		assertNotNull(historicoCandidatoManager.findResponsaveis());
	}
	
	public void testGetEventos()
	{
		EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
		etapaSeletiva.setNome("entrevista");
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		candidato.setNome("Maria");
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
		candidatoSolicitacao.setCandidato(candidato);
		
		HistoricoCandidato historicoCandidato = HistoricoCandidatoFactory.getEntity();
		historicoCandidato.setData(DateUtil.montaDataByString("26/02/2011"));
		historicoCandidato.setResponsavel("Francisco");
		historicoCandidato.setEtapaSeletiva(etapaSeletiva);
		historicoCandidato.setCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidato.setHoraIni("02:00");
		historicoCandidato.setHoraFim("03:00");
		
		Collection<HistoricoCandidato> historicos = new ArrayList<HistoricoCandidato>();
		historicos.add(historicoCandidato);
		
		historicoCandidatoDao.expects(once()).method("getEventos").withAnyArguments().will(returnValue(historicos));
		Collection<EventoAgenda> eventos = historicoCandidatoManager.getEventos("Francisco", 1L);
		assertEquals(1, eventos.size());
		
		EventoAgenda eventoAgenda = (EventoAgenda) eventos.toArray()[0];
		assertEquals(new Long(1L), eventoAgenda.getId());
		assertEquals("2011-02-26T02:00", eventoAgenda.getStart());
		assertEquals("2011-02-26T03:00", eventoAgenda.getEnd());
	}

	public void testMontaMapaHistorico()
	{
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);

		CandidatoSolicitacao cs1 = new CandidatoSolicitacao();
		cs1.setId(1L);
		cs1.setSolicitacao(solicitacao);

		Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();

		HistoricoCandidato hc1 = new HistoricoCandidato();
		hc1.setId(1L);
		hc1.setCandidatoSolicitacao(cs1);

		HistoricoCandidato hc2 = new HistoricoCandidato();
		hc2.setId(2L);
		hc2.setCandidatoSolicitacao(cs1);

		historicoCandidatos.add(hc1);
		historicoCandidatos.add(hc2);

		Collection<SolicitacaoHistoricoColaborador> shc = historicoCandidatoManager.montaMapaHistorico(historicoCandidatos);

		assertEquals(2, ((SolicitacaoHistoricoColaborador)shc.toArray()[0]).getHistoricos().size());
		assertEquals(solicitacao, ((SolicitacaoHistoricoColaborador)shc.toArray()[0]).getSolicitacao());
	}

    public void testSaveHistoricos() throws Exception
    {

    	MockSpringUtil.mocks.put("candidatoManager", candidatoManager);

    	boolean blackList = true;
    	HistoricoCandidato historicoCandidato = new HistoricoCandidato();
    	historicoCandidato.setApto(Apto.NAO);
    	historicoCandidato.setObservacao("observacao");

    	Candidato candidato = new Candidato();
    	candidato.setId(2L);

    	CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
    	candidatoSolicitacao.setId(1L);
    	candidatoSolicitacao.setCandidato(candidato);

    	String[] candidatosCheck = new String[]{"1","2"};

		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
		historicoCandidatoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		candidatoManager.expects(once()).method("setBlackList").with(ANYTHING, ANYTHING, ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);

		historicoCandidatoManager.saveHistoricos(historicoCandidato, candidatosCheck, blackList);

		historicoCandidatoManager.setDao(null);
		transactionManager.expects(once()).method("rollback").with(ANYTHING);


		Exception exp = null;

		try{
			historicoCandidatoManager.saveHistoricos(historicoCandidato, candidatosCheck, blackList);
		}catch (Exception e) {
			exp = e;
		}

		assertNotNull(exp);
    }

    public void testGetProdutividade(){

    	String ano = "2000";

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setId(1L);

		Collection<HistoricoCandidato> historicoCandidatos = criaHistoricos(etapa);

		Collection<EtapaSeletiva> etapas = new ArrayList<EtapaSeletiva>();
		etapas.add(etapa);

		historicoCandidatoDao.expects(once()).method("findByPeriodo").with(ANYTHING).will(returnValue(historicoCandidatos));
		etapaSeletivaManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(etapas));

    	Collection<ProdutividadeRelatorio> pr = historicoCandidatoManager.getProdutividade(ano, 1L);

    	assertEquals((Long)1L, ((ProdutividadeRelatorio)pr.toArray()[0]).getEtapa().getId());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdJan());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdFev());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdMar());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdAbr());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdMai());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdJun());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdJul());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdAgo());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdSet());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdOut());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdNov());
    	assertEquals(1, ((ProdutividadeRelatorio)pr.toArray()[0]).getQtdDez());

    }

	public void testFindByIdProjection()
    {
    	CandidatoSolicitacao cs1 = new CandidatoSolicitacao();
    	cs1.setId(1L);

    	HistoricoCandidato hc1 = new HistoricoCandidato();
    	hc1.setId(1L);
    	hc1.setCandidatoSolicitacao(cs1);

    	historicoCandidatoDao.expects(once()).method("findByIdProjection").with(eq(hc1.getId())).will(returnValue(hc1));

    	HistoricoCandidato historicoCandidato = historicoCandidatoManager.findByIdProjection(hc1.getId());
    	assertEquals(hc1.getId(), historicoCandidato.getId());
    }

    private Collection<HistoricoCandidato> criaHistoricos(EtapaSeletiva etapa)
	{
    	Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();

		HistoricoCandidato hist1 = new HistoricoCandidato();
		hist1.setData(DateUtil.criarAnoMesDia(2008, 1, 1));
		hist1.setEtapaSeletiva(etapa);

		HistoricoCandidato hist2 = new HistoricoCandidato();
		hist2.setData(DateUtil.criarAnoMesDia(2008, 2, 1));
		hist2.setEtapaSeletiva(etapa);

		HistoricoCandidato hist3 = new HistoricoCandidato();
		hist3.setData(DateUtil.criarAnoMesDia(2008, 3, 1));
		hist3.setEtapaSeletiva(etapa);

		HistoricoCandidato hist4 = new HistoricoCandidato();
		hist4.setData(DateUtil.criarAnoMesDia(2008, 4, 1));
		hist4.setEtapaSeletiva(etapa);

		HistoricoCandidato hist5 = new HistoricoCandidato();
		hist5.setData(DateUtil.criarAnoMesDia(2008, 5, 1));
		hist5.setEtapaSeletiva(etapa);

		HistoricoCandidato hist6 = new HistoricoCandidato();
		hist6.setData(DateUtil.criarAnoMesDia(2008, 6, 1));
		hist6.setEtapaSeletiva(etapa);

		HistoricoCandidato hist7 = new HistoricoCandidato();
		hist7.setData(DateUtil.criarAnoMesDia(2008, 7, 1));
		hist7.setEtapaSeletiva(etapa);

		HistoricoCandidato hist8 = new HistoricoCandidato();
		hist8.setData(DateUtil.criarAnoMesDia(2008, 8, 1));
		hist8.setEtapaSeletiva(etapa);

		HistoricoCandidato hist9 = new HistoricoCandidato();
		hist9.setData(DateUtil.criarAnoMesDia(2008, 9, 1));
		hist9.setEtapaSeletiva(etapa);

		HistoricoCandidato hist10 = new HistoricoCandidato();
		hist10.setData(DateUtil.criarAnoMesDia(2008, 10, 1));
		hist10.setEtapaSeletiva(etapa);

		HistoricoCandidato hist11 = new HistoricoCandidato();
		hist11.setData(DateUtil.criarAnoMesDia(2008, 11, 1));
		hist11.setEtapaSeletiva(etapa);

		HistoricoCandidato hist12 = new HistoricoCandidato();
		hist12.setData(DateUtil.criarAnoMesDia(2008, 12, 1));
		hist12.setEtapaSeletiva(etapa);

		historicoCandidatos.add(hist1);
		historicoCandidatos.add(hist2);
		historicoCandidatos.add(hist3);
		historicoCandidatos.add(hist4);
		historicoCandidatos.add(hist5);
		historicoCandidatos.add(hist6);
		historicoCandidatos.add(hist7);
		historicoCandidatos.add(hist8);
		historicoCandidatos.add(hist9);
		historicoCandidatos.add(hist10);
		historicoCandidatos.add(hist11);
		historicoCandidatos.add(hist12);

		return historicoCandidatos;
	}
    
    public void testFindByCandidato()
    {

    	Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();
    	Candidato candidato = new Candidato();

    	historicoCandidatoDao.expects(once()).method("findByCandidato").with(ANYTHING).will(returnValue(historicoCandidatos));

    	assertEquals(historicoCandidatos, historicoCandidatoManager.findByCandidato(candidato));
    }
    
    public void testRelatorioProcessoSeletivo()
    {
    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	
		Collection<ProcessoSeletivoRelatorio> processosSeletivos = new ArrayList<ProcessoSeletivoRelatorio>();
		ProcessoSeletivoRelatorio processoSeletivoRelatorio1 = new ProcessoSeletivoRelatorio();
		processoSeletivoRelatorio1.setEtapa(etapaSeletiva);
		ProcessoSeletivoRelatorio processoSeletivoRelatorio2 = new ProcessoSeletivoRelatorio();
		processoSeletivoRelatorio2.setEtapa(etapaSeletiva);
		
		processosSeletivos.add(processoSeletivoRelatorio1);
		processosSeletivos.add(processoSeletivoRelatorio2);
		
    	Collection<HistoricoCandidato> historicoCandidatos = new ArrayList<HistoricoCandidato>();
    	HistoricoCandidato historicoCandidato = HistoricoCandidatoFactory.getEntity();
    	historicoCandidato.setEtapaSeletiva(etapaSeletiva);
    	
		etapaSeletivaManager.expects(once()).method("montaProcessosSeletivos").with(ANYTHING, ANYTHING).will(returnValue(processosSeletivos));
		historicoCandidatoDao.expects(once()).method("findQtdParticipantes").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(returnValue(historicoCandidatos));
		
    	assertEquals(2, historicoCandidatoManager.relatorioProcessoSeletivo("2010", 1L, 1L, new Long[]{1L}).size());
    }
}
