package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class CopyOfCandidatoSolicitacaoManagerTest_JUnit4 extends MockObjectTestCase
{
	private CandidatoSolicitacaoManagerImpl candidatoSolicitacaoManager;
	private Mock candidatoSolicitacaoDao;
	private Mock colaboradorManager;

    protected void setUp() throws Exception
    {
    	candidatoSolicitacaoManager = new CandidatoSolicitacaoManagerImpl();

        candidatoSolicitacaoDao = new Mock(CandidatoSolicitacaoDao.class);
        candidatoSolicitacaoManager.setDao((CandidatoSolicitacaoDao) candidatoSolicitacaoDao.proxy());

        colaboradorManager = new Mock(ColaboradorManager.class);

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    }

    public void testGetCandidatosBySolicitacaoAberta(){

    	String[] etapaCheck = new String[]{"1","2"};
		Long empresaId = 1L;

		EtapaSeletiva es1 = new EtapaSeletiva();
		es1.setId(1L);

		EtapaSeletiva es2 = new EtapaSeletiva();
		es2.setId(3L);

		CandidatoSolicitacao cs1 = new CandidatoSolicitacao();
		cs1.setId(1L);

		CandidatoSolicitacao cs2 = new CandidatoSolicitacao();
		cs2.setId(2L);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(cs1);
		candidatoSolicitacaos.add(cs2);

		candidatoSolicitacaoDao.expects(once()).method("getCandidatosBySolicitacao").with(new Constraint[]{ANYTHING, eq(empresaId), ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(candidatoSolicitacaos));

		candidatoSolicitacaoManager.getCandidatosBySolicitacao(etapaCheck, empresaId, StatusSolicitacao.TODAS, Apto.SIM, null, null);
    }

    public void testGetCandidatosBySolicitacaoIdCandidatosComHistorico(){

    	ArrayList<Long> idCandidatosComHistoricos = new ArrayList<Long>();
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();

		Collection<CandidatoSolicitacao> css = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaoDao.expects(once()).method("getCandidatosBySolicitacao").with(eq(solicitacao), eq(idCandidatosComHistoricos)).will(returnValue(css));

		assertEquals(css, candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacao, idCandidatosComHistoricos));
    }

    public void testGetEmailNaoApto() throws Exception{

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setMailNaoAptos("mailNaoAptos");
		Long solicitacaoId = 1L;

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setContatoEmail("teste@fortes.com.br");

		CandidatoSolicitacao cs = new CandidatoSolicitacao();
		cs.setApto(Apto.NAO);
		cs.setCandidato(candidato);

		Collection<CandidatoSolicitacao> candidatoSolicitacoes = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacoes.add(cs);

	    candidatoSolicitacaoDao.expects(once()).method("findNaoAptos").with(eq(solicitacaoId)).will(returnValue(candidatoSolicitacoes));

	    assertEquals( "teste@fortes.com.br", candidatoSolicitacaoManager.getEmailNaoAptos(solicitacaoId, empresa)[0]);
    }

    public void testVerificaExisteColaborador(){
    	Long empresaId = 1L;

    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setId(1L);

    	CandidatoSolicitacao cs = new CandidatoSolicitacao();
		cs.setApto(Apto.NAO);
		cs.setCandidato(candidato);

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(cs);

		colaboradorManager.expects(once()).method("candidatoEhColaborador").with(eq(candidato.getId()), eq(empresaId)).will(returnValue(true));

		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);

		candidatoSolicitacaoManager.verificaExisteColaborador(candidatoSolicitacaos, empresaId);
    }

    public void testFindCandidatoSolicitacaoById(){

    	Long candidatoSolicitacaoId = 1L;
    	CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();

    	candidatoSolicitacaoDao.expects(once()).method("findCandidatoSolicitacaoById").with(eq(candidatoSolicitacaoId)).will(returnValue(candidatoSolicitacao));

		assertEquals(candidatoSolicitacao.getId(), candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacaoId).getId());
    }

    public void testFindCandidatoSolicitacaoByIdArray(){

    	Long[] candidatoSolicitacaoId = new Long[]{1L};
    	Collection<CandidatoSolicitacao> candidatoSolicitacao = new ArrayList<CandidatoSolicitacao>();

    	candidatoSolicitacaoDao.expects(once()).method("findCandidatoSolicitacaoById").with(eq(candidatoSolicitacaoId)).will(returnValue(candidatoSolicitacao));

    	assertEquals(candidatoSolicitacao, candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacaoId));
    }

    public void testGetCandidatoSolicitacaoList()
    {
    	Solicitacao solicitacao = new Solicitacao();
    	solicitacao.setId(1L);

    	EtapaSeletiva etapaSeletiva = new EtapaSeletiva();
    	etapaSeletiva.setId(1L);

    	CandidatoSolicitacao cs1 = new CandidatoSolicitacao();
    	cs1.setId(1L);
    	cs1.setSolicitacao(solicitacao);
    	cs1.setApto(Apto.SIM);
    	cs1.setEtapaSeletiva(etapaSeletiva);
    	cs1.setColaboradorId(1L);

    	Collection<CandidatoSolicitacao> candidatoSolicitacaosMock = new ArrayList<CandidatoSolicitacao>();
    	candidatoSolicitacaosMock.add(cs1);

    	candidatoSolicitacaoDao.expects(once()).method("getCandidatoSolicitacaoList").with(new Constraint[]{ANYTHING, ANYTHING, eq(solicitacao.getId()), eq(etapaSeletiva.getId()), ANYTHING, ANYTHING, eq(true), eq(false),ANYTHING, ANYTHING, ANYTHING}).will(returnValue(candidatoSolicitacaosMock));

    	Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(null, null, solicitacao.getId(), etapaSeletiva.getId(), null, null, true, false, null, null, null);

    	assertEquals("Test 1", 1, candidatoSolicitacaos.size());
    }

    public void testIsCandidatoSolicitacaoByCandidato()
    {
    	Candidato candidato = new Candidato();
    	candidato.setId(1L);

    	Candidato candidato2 = new Candidato();
    	candidato2.setId(2L);

    	CandidatoSolicitacao cs = new CandidatoSolicitacao();
    	cs.setId(1L);
    	cs.setCandidato(candidato);

    	candidatoSolicitacaoDao.expects(once()).method("getCandidatoSolicitacaoByCandidato").with(eq(candidato.getId())).will(returnValue(cs));

    	Boolean is =  candidatoSolicitacaoManager.isCandidatoSolicitacaoByCandidato(candidato.getId());

    	assertTrue(is);

    	candidatoSolicitacaoDao.expects(once()).method("getCandidatoSolicitacaoByCandidato").with(eq(candidato2.getId())).will(returnValue(null));

    	is =  candidatoSolicitacaoManager.isCandidatoSolicitacaoByCandidato(candidato2.getId());

    	assertFalse(is);
    }

    public void testFindBySolicitacaoTriagem(){

    	Long solicitacaoId = 1L;

    	Collection<CandidatoSolicitacao> candidatoSolicitacao = new ArrayList<CandidatoSolicitacao>();

    	candidatoSolicitacaoDao.expects(once()).method("findBySolicitacaoTriagem").with(eq(solicitacaoId)).will(returnValue(candidatoSolicitacao));

		assertEquals(candidatoSolicitacao, candidatoSolicitacaoManager.findBySolicitacaoTriagem(solicitacaoId));
    }
    
    public void testFindByFiltroSolicitacaoTriagem(){

    	CandidatoSolicitacao candidatoSolicitacaox = new CandidatoSolicitacao();
    	candidatoSolicitacaox.setTriagem(true);
    	    	
    	Collection<CandidatoSolicitacao> candidatoSolicitacao = new ArrayList<CandidatoSolicitacao>();
    	candidatoSolicitacao.add(candidatoSolicitacaox);
    	
    	candidatoSolicitacaoDao.expects(once()).method("findByFiltroSolicitacaoTriagem").with(eq(true)).will(returnValue(candidatoSolicitacao));

		assertEquals(candidatoSolicitacao, candidatoSolicitacaoManager.findByFiltroSolicitacaoTriagem(true));
    }

    public void testUpdateTriagem(){

    	boolean triagem = true;
    	Long[] candidatoSolicitacaoid = new Long[] {1L};

    	candidatoSolicitacaoDao.expects(once()).method("updateTriagem").with(eq(candidatoSolicitacaoid), eq(triagem));

    	candidatoSolicitacaoManager.updateTriagem(candidatoSolicitacaoid, triagem);
    }
    
    public void testGetCandidatosBySolicitacao()
    {
    	Candidato candidato1 = CandidatoFactory.getCandidato();
    	candidato1.setId(1L);

    	Candidato candidato2 = CandidatoFactory.getCandidato();
    	candidato2.setId(2L);

    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1L);
    	candidatoSolicitacao.setCandidato(candidato1);
    	candidatoSolicitacao.setSolicitacaoId(1L);

    	Collection<Long> idsCandidatos = new ArrayList<Long>();
    	idsCandidatos.add(candidato1.getId());

    	candidatoSolicitacaoDao.expects(once()).method("getCandidatosBySolicitacao").with(ANYTHING).will(returnValue(idsCandidatos));

    	Collection<Long> retorno = candidatoSolicitacaoManager.getCandidatosBySolicitacao(1L);

    	assertEquals(idsCandidatos.size(), retorno.size());
    }
}
