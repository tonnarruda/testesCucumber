package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManagerImpl;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class CandidatoSolicitacaoManagerTest_JUnit4
{
	private CandidatoSolicitacaoManagerImpl candidatoSolicitacaoManager;
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;

    @Before
	public void setUp() throws Exception
    {
    	candidatoSolicitacaoManager = new CandidatoSolicitacaoManagerImpl();

        candidatoSolicitacaoDao = mock(CandidatoSolicitacaoDao.class);
        candidatoSolicitacaoManager.setDao(candidatoSolicitacaoDao);
        
        solicitacaoAvaliacaoManager = mock(SolicitacaoAvaliacaoManager.class);
        candidatoSolicitacaoManager.setSolicitacaoAvaliacaoManager(solicitacaoAvaliacaoManager);

        colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);

    	PowerMockito.mockStatic(SpringUtil.class);
//    	Podemos usar BDDMockito.given(methodCall) quando o comportamento for o mesmo para todos os métodos
    	BDDMockito.given(SpringUtil.getBean("colaboradorQuestionarioManager")).willReturn(colaboradorQuestionarioManager);

//    	Não deve ser mais utilizado
//    	Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
    }

    @Test
    public void testFindByCandidatoSolicitacao(){
         
    	CandidatoSolicitacao candidatoSolicitacao = new CandidatoSolicitacao();
    	candidatoSolicitacao.setId(1L);

    	when(candidatoSolicitacaoDao.findByCandidatoSolicitacao(candidatoSolicitacao)).thenReturn(candidatoSolicitacao);

    	assertEquals(candidatoSolicitacao.getId(), candidatoSolicitacaoManager.findByCandidatoSolicitacao(candidatoSolicitacao).getId());
    }

    @Test
    public void testMoverCandidatosException() throws ColecaoVaziaException{

    	Long[] candidatosSolicitacaoId = new Long[]{};
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
    	Collection<Long> candidatoSolicitacaoIds = new ArrayList<Long>();

    	when(candidatoSolicitacaoDao.findCandidatosIdsAptosMover(candidatosSolicitacaoId, solicitacao)).thenReturn(candidatoSolicitacaoIds);
    	Exception exception = null;
    	try
    	{
    		candidatoSolicitacaoManager.moverCandidatos(candidatosSolicitacaoId, null, solicitacao, false);
		} catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
		assertEquals("Candidato selecionado já está na solicitação.", exception.getMessage());
    }
    
    @Test
    public void testMoverCandidatos() throws ColecaoVaziaException{

    	Long[] candidatosSolicitacaoId = new Long[]{1L,2L,3L};
    	Solicitacao solicitacaoOrigin = SolicitacaoFactory.getSolicitacao(1L);
    	Solicitacao solicitacaoDestino = SolicitacaoFactory.getSolicitacao(2L);

//    	Não deve ser mais utilizado
//    	MockSpringUtilJUnit4.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);

    	Collection<Long> candidatoSolicitacaoIds = new ArrayList<Long>();
    	candidatoSolicitacaoIds.add(1L);

    	when(candidatoSolicitacaoDao.findCandidatosIdsAptosMover(candidatosSolicitacaoId, solicitacaoDestino)).thenReturn(candidatoSolicitacaoIds);
    	
//    	Utilizar essa linha quando o retorno for específico do teste.
//    	when(SpringUtil.getBean("colaboradorQuestionarioManager")).thenReturn(colaboradorQuestionarioManager);
    	
//    	Não entendi como usar essa linha, preciso estudar para entender.
//    	PowerMockito.verifyStatic();
    	
    	candidatoSolicitacaoManager.moverCandidatos(candidatosSolicitacaoId, solicitacaoOrigin, solicitacaoDestino, false);

    	verify(colaboradorQuestionarioManager).removeByCandidatoSolicitacaoIdsAndSolicitacaoId(candidatoSolicitacaoIds, solicitacaoOrigin.getId());
    	verify(candidatoSolicitacaoDao).updateSolicitacaoCandidatos(solicitacaoDestino, candidatoSolicitacaoIds);
    }
    
    @Test
    public void testMoverCandidatosAtualizarModelo() throws ColecaoVaziaException{

    	Long[] candidatosSolicitacaoId = new Long[]{1L,2L,3L};
    	Solicitacao solicitacaoOrigem = SolicitacaoFactory.getSolicitacao(1L);
    	Solicitacao solicitacaoDestino = SolicitacaoFactory.getSolicitacao(2L);

//    	Não deve ser mais utilizado
//    	MockSpringUtilJUnit4.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);

    	Collection<Long> candidatoSolicitacaoIds = new ArrayList<Long>();
    	candidatoSolicitacaoIds.add(1L);
    	
    	Collection<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
    	avaliacoes.add(AvaliacaoFactory.getEntity(1L));
    	
    	when(candidatoSolicitacaoDao.findCandidatosIdsAptosMover(candidatosSolicitacaoId, solicitacaoDestino)).thenReturn(candidatoSolicitacaoIds);
    	when(colaboradorQuestionarioManager.getAvaliacoesBySolicitacaoIdAndCandidatoSolicitacaoId(solicitacaoOrigem.getId(), LongUtil.collectionStringToArrayLong(candidatoSolicitacaoIds))).thenReturn(avaliacoes);
    	
    	candidatoSolicitacaoManager.moverCandidatos(candidatosSolicitacaoId, solicitacaoOrigem, solicitacaoDestino, true);
    	
    	verify(solicitacaoAvaliacaoManager).inserirNovasAvaliacoes(solicitacaoDestino.getId(), avaliacoes);
    	verify(colaboradorQuestionarioManager).updateByCandidatoSolicitacaoAndSoclicitacaoOrigemAndDestino(candidatoSolicitacaoIds, solicitacaoOrigem.getId(), solicitacaoDestino.getId());
    	verify(candidatoSolicitacaoDao).updateSolicitacaoCandidatos(solicitacaoDestino, candidatoSolicitacaoIds);
    }
    
//    @Test
//	public void testListarSolicitacoesEmAbertoCandidatoCasoVinculoForCandidato(){
//	
//		TipoPessoa tipoPessoa=TipoPessoa.CANDIDATO;
//		Candidato candidato = CandidatoFactory.getCandidato(1l);
//		
//		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1l);
//		solicitacao.setData(new Date());
//		
//		Solicitacao solicitacaoEncerrada = SolicitacaoFactory.getSolicitacao(2l);
//		solicitacaoEncerrada.setData(new Date());
//		solicitacaoEncerrada.setEncerrada(true);
//		solicitacaoEncerrada.setDataEncerramento(new Date());
//
//		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1l);
//		candidatoSolicitacao.setCandidato(candidato);
//		candidatoSolicitacao.setSolicitacao(solicitacao);
//		
//		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity(1l);
//		candidatoSolicitacao2.setCandidato(candidato);
//		candidatoSolicitacao2.setSolicitacao(solicitacaoEncerrada);
//		
//		Collection<Solicitacao> solicitacoes  = Arrays.asList(solicitacaoEncerrada,solicitacao);
//		CollectionUtil<Solicitacao> collectionUtil = new CollectionUtil<Solicitacao>();
//		
//		solicitacoes=collectionUtil.sortCollectionBoolean(solicitacoes,"encerrada","asc");
//		
//		when(solicitacaoDao.listarSolicitacoesEmAbertoCandidato(eq(candidato.getId()), any(Date.class))).thenReturn(solicitacoes);
//		
//		Collection<Solicitacao> listarSolicitacoesEmAbertoCandidatoOuColaborador = solicitacaoManager.listarSolicitacoesEmAbertoCandidatoOuColaborador(tipoPessoa, candidato.getId(),new Date());
//		
//		assertEquals(2, listarSolicitacoesEmAbertoCandidatoOuColaborador.size());
//		assertFalse(((Solicitacao)listarSolicitacoesEmAbertoCandidatoOuColaborador.toArray()[0]).isEncerrada());
//		assertTrue(((Solicitacao)listarSolicitacoesEmAbertoCandidatoOuColaborador.toArray()[1]).isEncerrada());
//		
//		verify(solicitacaoDao,never()).listarSolicitacoesEmAbertoColaborador(candidato.getId(), new Date());	
//	}
//	
//	@Test
//	public void testListarSolicitacoesEmAbertoCandidatoCasoVinculoForCandidatoComSolicitacaoExameSemData(){
//		
//		TipoPessoa tipoPessoa=TipoPessoa.CANDIDATO;
//		Candidato candidato = CandidatoFactory.getCandidato(1l);
//		
//		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1l);
//		solicitacaoExame.setData(null);
//		
//		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1l);
//		solicitacao.setData(new Date());
//		
//		Solicitacao solicitacaoEncerrada = SolicitacaoFactory.getSolicitacao(2l);
//		solicitacaoEncerrada.setData(new Date());
//		solicitacaoEncerrada.setEncerrada(true);
//		solicitacaoEncerrada.setDataEncerramento(new Date());
//		
//		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1l);
//		candidatoSolicitacao.setCandidato(candidato);
//		candidatoSolicitacao.setSolicitacao(solicitacao);
//		
//		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity(1l);
//		candidatoSolicitacao2.setCandidato(candidato);
//		candidatoSolicitacao2.setSolicitacao(solicitacaoEncerrada);
//		
//		Collection<Solicitacao> solicitacoes  = Arrays.asList(solicitacaoEncerrada,solicitacao);
//		CollectionUtil<Solicitacao> collectionUtil = new CollectionUtil<Solicitacao>();
//		
//		solicitacoes=collectionUtil.sortCollectionBoolean(solicitacoes,"encerrada","asc");
//		
//		when(solicitacaoDao.listarSolicitacoesEmAbertoCandidato(eq(candidato.getId()), any(Date.class))).thenReturn(solicitacoes);
//		
//		Collection<Solicitacao> listarSolicitacoesEmAbertoCandidatoOuColaborador = solicitacaoManager.listarSolicitacoesEmAbertoCandidatoOuColaborador(tipoPessoa, candidato.getId(), solicitacaoExame.getData());
//		
//		assertEquals(2, listarSolicitacoesEmAbertoCandidatoOuColaborador.size());
//		assertFalse(((Solicitacao)listarSolicitacoesEmAbertoCandidatoOuColaborador.toArray()[0]).isEncerrada());
//		assertTrue(((Solicitacao)listarSolicitacoesEmAbertoCandidatoOuColaborador.toArray()[1]).isEncerrada());
//		
//		verify(solicitacaoDao,never()).listarSolicitacoesEmAbertoColaborador(candidato.getId(), new Date());	
//	}
//	
//	@Test
//	public void testListarSolicitacoesEmAbertoColaboradorCasoVinculoForColaborador(){
//		
//		TipoPessoa tipoPessoa=TipoPessoa.COLABORADOR;
//		
//		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1l);
//		solicitacaoExame.setData(new Date());
//		
//		Candidato candidato = CandidatoFactory.getCandidato(1l);
//		
//		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1l);
//		solicitacao.setData(DateUtil.retornaDataDiaAnterior(solicitacaoExame.getData()));
//		
//		Solicitacao solicitacaoEncerrada = SolicitacaoFactory.getSolicitacao(2l);
//		solicitacaoEncerrada.setData(DateUtil.retornaDataDiaAnterior(solicitacaoExame.getData()));
//		solicitacaoEncerrada.setEncerrada(true);
//		solicitacaoEncerrada.setDataEncerramento(new Date());
//
//		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1l);
//		candidatoSolicitacao.setCandidato(candidato);
//		candidatoSolicitacao.setSolicitacao(solicitacao);
//		candidatoSolicitacao.setStatus(StatusCandidatoSolicitacao.APROMOVER);
//		
//		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity(1l);
//		candidatoSolicitacao2.setCandidato(candidato);
//		candidatoSolicitacao2.setSolicitacao(solicitacaoEncerrada);
//		candidatoSolicitacao2.setStatus(StatusCandidatoSolicitacao.APROMOVER);
//		
//		Colaborador colaborador = ColaboradorFactory.getEntity(1l);
//		colaborador.setCandidato(candidato);
//		
//		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1l, colaborador);
//		historicoColaborador.setCandidatoSolicitacao(candidatoSolicitacao);
//		
//		Collection<Solicitacao> solicitacoes = Arrays.asList(solicitacaoEncerrada,solicitacao);
//		CollectionUtil<Solicitacao> collectionUtil = new CollectionUtil<Solicitacao>();
//		
//		solicitacoes=collectionUtil.sortCollectionBoolean(solicitacoes,"encerrada","asc");
//		
//		when(solicitacaoDao.listarSolicitacoesEmAbertoColaborador(colaborador.getCandidato().getId(), solicitacaoExame.getData())).thenReturn(solicitacoes);
//		
//		Collection<Solicitacao> listarSolicitacoesEmAbertoColaborador = solicitacaoManager.listarSolicitacoesEmAbertoCandidatoOuColaborador(tipoPessoa, colaborador.getCandidato().getId(), solicitacaoExame.getData());
//		
//		assertEquals(2, listarSolicitacoesEmAbertoColaborador.size());
//		assertFalse(((Solicitacao)listarSolicitacoesEmAbertoColaborador.toArray()[0]).isEncerrada());
//		assertTrue(((Solicitacao)listarSolicitacoesEmAbertoColaborador.toArray()[1]).isEncerrada());
//		
//		verify(solicitacaoDao,never()).listarSolicitacoesEmAbertoCandidato(candidato.getId(), new Date());	
//	}
//	
//	@Test
//	public void testListarSolicitacoesEmAbertoColaboradorCasoVinculoForColaboradorSemSolicitacaoExame(){
//		
//		TipoPessoa tipoPessoa=TipoPessoa.COLABORADOR;
//		
//		Candidato candidato = CandidatoFactory.getCandidato(1l);
//		
//		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1l);
//		solicitacao.setData(new Date());
//		
//		Solicitacao solicitacaoEncerrada = SolicitacaoFactory.getSolicitacao(2l);
//		solicitacaoEncerrada.setData(DateUtil.retornaDataDiaAnterior(new Date()));
//		solicitacaoEncerrada.setEncerrada(true);
//		solicitacaoEncerrada.setDataEncerramento(new Date());
//		
//		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(1l);
//		candidatoSolicitacao.setCandidato(candidato);
//		candidatoSolicitacao.setSolicitacao(solicitacao);
//		candidatoSolicitacao.setStatus(StatusCandidatoSolicitacao.APROMOVER);
//		
//		CandidatoSolicitacao candidatoSolicitacao2 = CandidatoSolicitacaoFactory.getEntity(1l);
//		candidatoSolicitacao2.setCandidato(candidato);
//		candidatoSolicitacao2.setSolicitacao(solicitacaoEncerrada);
//		candidatoSolicitacao2.setStatus(StatusCandidatoSolicitacao.APROMOVER);
//		
//		Colaborador colaborador = ColaboradorFactory.getEntity(1l);
//		colaborador.setCandidato(candidato);
//		
//		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1l, colaborador);
//		historicoColaborador.setCandidatoSolicitacao(candidatoSolicitacao);
//		
//		Collection<Solicitacao> solicitacoes = Arrays.asList(solicitacaoEncerrada,solicitacao);
//		CollectionUtil<Solicitacao> collectionUtil = new CollectionUtil<Solicitacao>();
//		
//		solicitacoes=collectionUtil.sortCollectionBoolean(solicitacoes,"encerrada","asc");
//		
//		when(solicitacaoDao.listarSolicitacoesEmAbertoColaborador(eq(colaborador.getCandidato().getId()), any(Date.class))).thenReturn(solicitacoes);
//		
//		Collection<Solicitacao> listarSolicitacoesEmAbertoColaborador = solicitacaoManager.listarSolicitacoesEmAbertoCandidatoOuColaborador(tipoPessoa, colaborador.getCandidato().getId(), null);
//		
//		assertEquals(2, listarSolicitacoesEmAbertoColaborador.size());
//		assertFalse(((Solicitacao)listarSolicitacoesEmAbertoColaborador.toArray()[0]).isEncerrada());
//		assertTrue(((Solicitacao)listarSolicitacoesEmAbertoColaborador.toArray()[1]).isEncerrada());
//		
//		verify(solicitacaoDao,never()).listarSolicitacoesEmAbertoCandidato(candidato.getId(), new Date());	
//	}
    
}
