package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.SolicitacaoManagerImpl;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoManagerTest_Junit4
{
	private SolicitacaoManagerImpl solicitacaoManager = new SolicitacaoManagerImpl();
	private SolicitacaoDao solicitacaoDao;

	@Before
	public void setUp() throws Exception
	{
		solicitacaoDao = mock(SolicitacaoDao.class);
		solicitacaoManager.setDao(solicitacaoDao);
	}
	
	@Test
	public void testCalculaIndicadorVagasPreenchidasNoPrazo(){
		Long empresaId = 2L;
		Long[] estabelecimentosIds = new Long[]{1L, 2L};
		Long[] areasIds = new Long[]{1L, 5L};
		Long[] solicitacoesIds = new Long[]{};
		Date dataDe = new Date();
		Date dataAte = DateUtil.incrementaDias(dataDe, 10);
		
		Solicitacao sol1 = SolicitacaoFactory.getSolicitacao();
		sol1.setQtdVagasPreenchidas(2);
		sol1.setQuantidade(1);
		
		Solicitacao sol2 = SolicitacaoFactory.getSolicitacao();
		sol2.setQtdVagasPreenchidas(1);
		sol2.setQuantidade(2);
		
		Collection<Solicitacao> solicitacoes = Arrays.asList(sol1,sol2);
		
		when(solicitacaoDao.calculaIndicadorVagasPreenchidasNoPrazo(empresaId, estabelecimentosIds, areasIds, solicitacoesIds, dataDe, dataAte)).thenReturn(solicitacoes);

		Double percentual = solicitacaoManager.calculaIndicadorVagasPreenchidasNoPrazo(empresaId, estabelecimentosIds, areasIds, solicitacoesIds, dataDe, dataAte);
		
		assertEquals(new Double(75.0), percentual);
	}
	
	@Test
	public void testCalculaIndicadorVagasPreenchidasNoPrazoSemSolicitacao(){
		Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		when(solicitacaoDao.calculaIndicadorVagasPreenchidasNoPrazo(null, null, null, null, null, null)).thenReturn(solicitacoes);

		Double percentual = solicitacaoManager.calculaIndicadorVagasPreenchidasNoPrazo(null, null, null, null, null, null);
		
		assertEquals(new Double(0.0), percentual);
	}

}