package com.fortes.rh.test.business.captacao;

import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.SolicitacaoManagerImpl;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
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
		char dataStatusAprovacaoSolicitacao = 'S';
		
		solicitacaoManager.calculaIndicadorVagasPreenchidasNoPrazo(empresaId, estabelecimentosIds, areasIds, solicitacoesIds, dataDe, dataAte);
		verify(solicitacaoDao, times(1)).calculaIndicadorVagasPreenchidasNoPrazo(empresaId, estabelecimentosIds, areasIds, solicitacoesIds, dataDe, dataAte);
	}

}