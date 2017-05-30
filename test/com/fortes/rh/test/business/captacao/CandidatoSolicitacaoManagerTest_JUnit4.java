package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

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
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
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
    	
    	verify(solicitacaoAvaliacaoManager).inserirNovasAvaliações(solicitacaoDestino.getId(), avaliacoes);
    	verify(colaboradorQuestionarioManager).updateByCandidatoSolicitacaoAndSoclicitacaoOrigemAndDestino(candidatoSolicitacaoIds, solicitacaoOrigem.getId(), solicitacaoDestino.getId());
    	verify(candidatoSolicitacaoDao).updateSolicitacaoCandidatos(solicitacaoDestino, candidatoSolicitacaoIds);
    }
}
