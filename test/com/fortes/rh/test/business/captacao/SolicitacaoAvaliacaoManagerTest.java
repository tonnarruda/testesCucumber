package com.fortes.rh.test.business.captacao;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManagerImpl;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;

public class SolicitacaoAvaliacaoManagerTest {

	public SolicitacaoAvaliacaoManagerImpl solicitacaoAvaliacaoManager;
	public SolicitacaoAvaliacaoDao solicitacaoAvaliacaoDao;
	
	@Before
	public void setUp(){
		solicitacaoAvaliacaoManager = new SolicitacaoAvaliacaoManagerImpl();
		
		solicitacaoAvaliacaoDao = mock(SolicitacaoAvaliacaoDao.class);
		solicitacaoAvaliacaoManager.setDao(solicitacaoAvaliacaoDao);
	}
	
	@Test
	public void testInserirNovasAvaliações(){
		Long solicitacaoId = 1L;
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity(1L);
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity(2L);
		
		Collection<Avaliacao> avaliacoes = Arrays.asList(avaliacao1,avaliacao2);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao = new SolicitacaoAvaliacao(solicitacaoId, avaliacao2.getId(), avaliacao2.getTitulo());
		Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos = Arrays.asList(solicitacaoAvaliacao);
		
		when(solicitacaoAvaliacaoDao.findBySolicitacaoId(solicitacaoId, null)).thenReturn(solicitacaoAvaliacaos);
		 
		solicitacaoAvaliacaoManager.inserirNovasAvaliações(solicitacaoId, avaliacoes);
		verify(solicitacaoAvaliacaoDao, times(1)).save(any(SolicitacaoAvaliacao.class));
	}
}
