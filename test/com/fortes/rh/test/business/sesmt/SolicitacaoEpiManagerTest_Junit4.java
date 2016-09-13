package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiVO;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;

public class SolicitacaoEpiManagerTest_Junit4
{
	private SolicitacaoEpiManagerImpl solicitacaoEpiManager = new SolicitacaoEpiManagerImpl();
	private SolicitacaoEpiDao solicitacaoEpiDao;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;

	@Before
	public void setUp() throws Exception
    {
        solicitacaoEpiDao = mock(SolicitacaoEpiDao.class);
        solicitacaoEpiManager.setDao(solicitacaoEpiDao);
        
        solicitacaoEpiItemManager = mock(SolicitacaoEpiItemManager.class);
        solicitacaoEpiManager.setSolicitacaoEpiItemManager(solicitacaoEpiItemManager);
    }

	@Test
	public void testFindAllSelectByData()
	{
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		
		SolicitacaoEpiVO solicitacaoEpiVO = new SolicitacaoEpiVO();
		solicitacaoEpiVO.setQtdSolicitacaoEpis(1);
		solicitacaoEpiVO.setSolicitacaoEpis(Arrays.asList(solicitacaoEpi));
		
		when(solicitacaoEpiDao.findAllSelect(anyInt(), anyInt(), anyLong(), any(Date.class), any(Date.class), any(Colaborador.class), anyString(), anyLong(), anyString(), any(Long[].class) , anyChar())).thenReturn(solicitacaoEpiVO);
				
		when(solicitacaoEpiItemManager.findAllEntregasBySolicitacaoEpi(eq(solicitacaoEpi.getId()))).thenReturn(new ArrayList<SolicitacaoEpiItem>());

		when(solicitacaoEpiItemManager.findAllDevolucoesBySolicitacaoEpi(eq(solicitacaoEpi.getId()))).thenReturn(new ArrayList<SolicitacaoEpiItem>());

		assertEquals(solicitacaoEpiVO, solicitacaoEpiManager.findAllSelect(0, 0, 1L, null, null, new Colaborador(), SituacaoSolicitacaoEpi.ABERTA, null, SituacaoColaborador.TODOS, null, 'D'));
	}
	
	@Test
	public void testFindAllSelectByNome() {
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		
		SolicitacaoEpiVO solicitacaoEpiVO = new SolicitacaoEpiVO();
		solicitacaoEpiVO.setQtdSolicitacaoEpis(1);
		solicitacaoEpiVO.setSolicitacaoEpis(Arrays.asList(solicitacaoEpi));
		
		when(solicitacaoEpiDao.findAllSelect(anyInt(), anyInt(), anyLong(), any(Date.class), any(Date.class), any(Colaborador.class), anyString(), anyLong(), anyString(), any(Long[].class) , anyChar())).thenReturn(solicitacaoEpiVO);
				
		when(solicitacaoEpiItemManager.findAllEntregasBySolicitacaoEpi(eq(solicitacaoEpi.getId()))).thenReturn(new ArrayList<SolicitacaoEpiItem>());

		when(solicitacaoEpiItemManager.findAllDevolucoesBySolicitacaoEpi(eq(solicitacaoEpi.getId()))).thenReturn(new ArrayList<SolicitacaoEpiItem>());
		
		assertEquals(solicitacaoEpiVO, solicitacaoEpiManager.findAllSelect(0, 0, 1L, null, null, new Colaborador(), SituacaoSolicitacaoEpi.TODAS, null, SituacaoColaborador.TODOS, null, 'N'));
	}
}