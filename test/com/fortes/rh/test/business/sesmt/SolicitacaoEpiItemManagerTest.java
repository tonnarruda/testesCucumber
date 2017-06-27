package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemDevolucaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemEntregaFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoEpiItemManagerTest
{
	private SolicitacaoEpiItemManagerImpl solicitacaoEpiItemManager = new SolicitacaoEpiItemManagerImpl();
	private SolicitacaoEpiItemDao solicitacaoEpiItemDao;
	private SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager;
	private SolicitacaoEpiItemDevolucaoManager solicitacaoEpiItemDevolucaoManager;

	@Before
	public void setUp() throws Exception
    {
        solicitacaoEpiItemDao = mock(SolicitacaoEpiItemDao.class);
        solicitacaoEpiItemManager.setDao(solicitacaoEpiItemDao);
        
        solicitacaoEpiItemEntregaManager = mock(SolicitacaoEpiItemEntregaManager.class);
        solicitacaoEpiItemManager.setSolicitacaoEpiItemEntregaManager(solicitacaoEpiItemEntregaManager);
        
        solicitacaoEpiItemDevolucaoManager = mock(SolicitacaoEpiItemDevolucaoManager.class);
        solicitacaoEpiItemManager.setSolicitacaoEpiItemDevolucaoManager(solicitacaoEpiItemDevolucaoManager);
    }

	@Test
	public void testFindBySolicitacaoEpi()
	{
		Collection<SolicitacaoEpiItem> colecao = new ArrayList<SolicitacaoEpiItem>();
		Long solicitacaoEpiId = 1L;
		when(solicitacaoEpiItemDao.findBySolicitacaoEpi(solicitacaoEpiId)).thenReturn(colecao);

		assertEquals(colecao, solicitacaoEpiItemManager.findBySolicitacaoEpi(solicitacaoEpiId));
	}

	@Test
	public void testFindBySolicitacaoEpiArray()
	{
		Collection<SolicitacaoEpiItem> colecao = new ArrayList<SolicitacaoEpiItem>();
		Long solicitacaoEpiId = 1L;
		when(solicitacaoEpiItemDao.findAllEntregasBySolicitacaoEpi(solicitacaoEpiId)).thenReturn(colecao);

		assertEquals(colecao, solicitacaoEpiItemManager.findAllEntregasBySolicitacaoEpi(solicitacaoEpiId));
	}
	
	@Test
	public void testRemoveAllBySolicitacaoEpi()
	{
		Long solicitacaoEpiId = 1L;
		solicitacaoEpiItemManager.removeAllBySolicitacaoEpi(solicitacaoEpiId);
		verify(solicitacaoEpiItemDao).removeAllBySolicitacaoEpi(solicitacaoEpiId);
	}
	
	@Test
	public void testValidaDataDevolucaoJaDevolvidos()
	{
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;
		Integer qtdTotalEntregue = 5;
		Integer qtdTotalDevolvida = 5;
		
		when(solicitacaoEpiItemEntregaManager.getTotalEntregue(solicitacaoEpiItemId, null)).thenReturn(qtdTotalEntregue);
		when(solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId)).thenReturn(qtdTotalDevolvida);

		assertEquals("Não é possível inserir uma devolução, pois todas os ítens já foram devolvidos.", solicitacaoEpiItemManager.validaDataDevolucao(DateUtil.criarDataMesAno(1, 1, 2017), solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, 1, DateUtil.criarDataMesAno(1, 1, 2010)));
	}
	
	@Test
	public void testValidaDataDevolucaoJaEntreguesAteAData()
	{
		Date data = DateUtil.criarDataMesAno(22, 6, 2017);
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;
		Integer qtdTotalEntregue = 5;
		Integer qtdTotalDevolvida = 2;
		Integer qtdEntregueAteAData = 0;
		
		when(solicitacaoEpiItemEntregaManager.getTotalEntregue(solicitacaoEpiItemId, null)).thenReturn(qtdTotalEntregue);
		when(solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId)).thenReturn(qtdTotalDevolvida);
		when(solicitacaoEpiItemEntregaManager.findQtdEntregueByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId)).thenReturn(qtdEntregueAteAData);

		assertEquals("Não existe(m) entrega(s) efetuada(s) anterior a data 22/06/2017.", solicitacaoEpiItemManager.validaDataDevolucao(data, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, 1, DateUtil.criarDataMesAno(1, 1, 2010)));
	}
	
	@Test
	public void testValidaDataDevolucaoDevolvendoMaisDoQuePode()
	{
		Date data = DateUtil.criarDataMesAno(22, 6, 2017);
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;
		Integer qtdTotalEntregue = 5;
		Integer qtdTotalDevolvida = 2;
		Integer qtdEntregueAteAData = 3;
		Integer qtdDevolvidaAteAData = 1;
		
		when(solicitacaoEpiItemEntregaManager.getTotalEntregue(solicitacaoEpiItemId, null)).thenReturn(qtdTotalEntregue);
		when(solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId)).thenReturn(qtdTotalDevolvida);
		when(solicitacaoEpiItemEntregaManager.findQtdEntregueByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId)).thenReturn(qtdEntregueAteAData);
		when(solicitacaoEpiItemDevolucaoManager.findQtdDevolvidaByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId)).thenReturn(qtdDevolvidaAteAData);

		assertEquals("Não é possível inserir uma devolução nessa data ( 22/06/2017 ) maior que 2 Item(ns).", solicitacaoEpiItemManager.validaDataDevolucao(data, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, 3, DateUtil.criarDataMesAno(1, 1, 2010)));
	}
	
	@Test
	public void testValidaDataDevolucaoDevolvendoantesDasEntregas()
	{
		Date data = DateUtil.criarDataMesAno(22, 6, 2017);
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;
		Integer qtdTotalEntregue = 5;
		Integer qtdTotalDevolvida = 2;
		Integer qtdEntregueAteAData = 2;
		Integer qtdDevolvidaAteAData = 2;
		
		when(solicitacaoEpiItemEntregaManager.getTotalEntregue(solicitacaoEpiItemId, null)).thenReturn(qtdTotalEntregue);
		when(solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId)).thenReturn(qtdTotalDevolvida);
		when(solicitacaoEpiItemEntregaManager.findQtdEntregueByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId)).thenReturn(qtdEntregueAteAData);
		when(solicitacaoEpiItemDevolucaoManager.findQtdDevolvidaByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId)).thenReturn(qtdDevolvidaAteAData);

		assertEquals("Não é possível inserir uma devolução nessa data, pois já existe(m) devolução(ões) para a(s) entrega(s) efetuada(s) anterior a essa data 22/06/2017.", solicitacaoEpiItemManager.validaDataDevolucao(data, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, 1, DateUtil.criarDataMesAno(1, 1, 2010)));
	}
	
	@Test
	public void testValidaDataDevolucao()
	{
		Date data = DateUtil.criarDataMesAno(22, 6, 2017);
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;
		Integer qtdTotalEntregue = 5;
		Integer qtdTotalDevolvida = 2;
		Integer qtdEntregueAteAData = 2;
		Integer qtdDevolvidaAteAData = 1;
		
		when(solicitacaoEpiItemEntregaManager.getTotalEntregue(solicitacaoEpiItemId, null)).thenReturn(qtdTotalEntregue);
		when(solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId)).thenReturn(qtdTotalDevolvida);
		when(solicitacaoEpiItemEntregaManager.findQtdEntregueByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId)).thenReturn(qtdEntregueAteAData);
		when(solicitacaoEpiItemDevolucaoManager.findQtdDevolvidaByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId)).thenReturn(qtdDevolvidaAteAData);

		assertNull(solicitacaoEpiItemManager.validaDataDevolucao(data, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, 1, null));
	}
	
	@Test
	public void testValidaDataDevolucaoInferiorADataDeEntrega()
	{
		Date data1 = DateUtil.criarDataMesAno(22, 6, 2017);
		Date data2 = DateUtil.criarDataMesAno(22, 8, 2017);
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;
		
		when(solicitacaoEpiItemEntregaManager.getMinDataBySolicitacaoEpiItem(solicitacaoEpiItemId)).thenReturn(data2);

		assertEquals("Não é possível inserir uma devolução anterio a primeira data de entrega ( Data:22/08/2017 )", solicitacaoEpiItemManager.validaDataDevolucao(data1, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, 1, null));
	}
	
	@Test
	public void testValidaDataDevolucaoInferiorADataDaSolicitacao()
	{
		Date data1 = DateUtil.criarDataMesAno(22, 9, 2017);
		Date data2 = DateUtil.criarDataMesAno(22, 10, 2017);
		Long solicitacaoEpiItemId = 1L;
		Long solicitacaoEpiItemDevolucaoId = 2L;

		assertEquals("A data de devolução não pode ser anterior à data de solicitação ( Data solicitação:22/10/2017 )", solicitacaoEpiItemManager.validaDataDevolucao(data1, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, 1, data2));
	}
	
	@Test
	public void testdecideEdicaoSolicitacaoEpiItemEntrega()
	{
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega1 = SolicitacaoEpiItemEntregaFactory.getEntity(1L);
		solicitacaoEpiItemEntrega1.setQtdEntregue(2);
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega2 = SolicitacaoEpiItemEntregaFactory.getEntity(2L);
		solicitacaoEpiItemEntrega2.setQtdEntregue(2);
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega3 = SolicitacaoEpiItemEntregaFactory.getEntity(3L);
		solicitacaoEpiItemEntrega3.setQtdEntregue(3);
		
		Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas = Arrays.asList(solicitacaoEpiItemEntrega1, solicitacaoEpiItemEntrega2, solicitacaoEpiItemEntrega3);
		Long solicitacaoEpiItemId = 1L;
		
		when(solicitacaoEpiItemEntregaManager.findBySolicitacaoEpiItem(solicitacaoEpiItemId)).thenReturn(solicitacaoEpiItemEntregas);
		when(solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, null)).thenReturn(3);
		
		solicitacaoEpiItemManager.decideEdicaoSolicitacaoEpiItemEntrega(solicitacaoEpiItemId, solicitacaoEpiItemEntrega1);
		solicitacaoEpiItemManager.decideEdicaoSolicitacaoEpiItemEntrega(solicitacaoEpiItemId, solicitacaoEpiItemEntrega2);
		solicitacaoEpiItemManager.decideEdicaoSolicitacaoEpiItemEntrega(solicitacaoEpiItemId, solicitacaoEpiItemEntrega3);
		
		assertTrue(solicitacaoEpiItemEntrega1.isItensEntregues());
		assertTrue(solicitacaoEpiItemEntrega2.isItensEntregues());
		assertFalse(solicitacaoEpiItemEntrega3.isItensEntregues());
	}
}