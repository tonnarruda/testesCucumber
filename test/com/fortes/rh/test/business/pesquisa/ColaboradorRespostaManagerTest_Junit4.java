package com.fortes.rh.test.business.pesquisa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.pesquisa.ColaboradorRespostaManagerImpl;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;

public class ColaboradorRespostaManagerTest_Junit4 
{
	private ColaboradorRespostaManagerImpl colaboradorRespostaManager = new ColaboradorRespostaManagerImpl();
	private ColaboradorRespostaDao colaboradorRespostaDao;

    @Before
	public void setUp() throws Exception
    {
        colaboradorRespostaDao = mock(ColaboradorRespostaDao.class);
        colaboradorRespostaManager.setDao(colaboradorRespostaDao);
    }
    
    @Test
    public void testFindInPerguntaIdsAvaliacaoTipoAcompanhamentoPeriodoExperiencia()
    {
    	Long[] empresasIds = new Long[]{2L};
    	Long[] avaliacoesDesempenhoIds = null;
    	Date dataInicio = new Date();
    	Date dataFim = new Date();
    	
    	when(colaboradorRespostaDao.findInPerguntaIdsAvaliacao(any(Long[].class),any(Long[].class),eq(dataInicio), eq(dataFim), eq(empresasIds), eq(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA), eq(avaliacoesDesempenhoIds))).thenReturn(new ArrayList<ColaboradorResposta>());
    	assertEquals(0, colaboradorRespostaManager.findInPerguntaIdsAvaliacao(null, new Long[]{}, dataInicio, dataFim, empresasIds, TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, avaliacoesDesempenhoIds).size());
    }
    
    @Test
    public void testFindInPerguntaIdsAvaliacaoTipoDesempenho()
    {
    	Long[] empresasIds = new Long[]{1L, 2L};
    	Long[] avaliacoesDesempenhoIds = new Long[]{33L, 44L};
    	Date dataInicio = null;
    	Date dataFim = null;
    	
    	
    	when(colaboradorRespostaDao.findInPerguntaIdsAvaliacao(any(Long[].class),any(Long[].class), eq(dataInicio), eq(dataFim), eq(empresasIds), eq(TipoModeloAvaliacao.DESEMPENHO), eq(avaliacoesDesempenhoIds))).thenReturn(new ArrayList<ColaboradorResposta>());
    	assertEquals(0, colaboradorRespostaManager.findInPerguntaIdsAvaliacao(null, new Long[]{}, dataInicio, dataFim, empresasIds, TipoModeloAvaliacao.DESEMPENHO, avaliacoesDesempenhoIds).size());
    }
}