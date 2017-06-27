package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemDevolucaoManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDevolucaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemDevolucaoFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemFactory;
import com.fortes.rh.util.CollectionUtil;

public class SolicitacaoEpiItemDevolucaoManagerTest
{
	private SolicitacaoEpiItemDevolucaoManagerImpl solicitacaoEpiItemDevolucaoManager = new SolicitacaoEpiItemDevolucaoManagerImpl();
	private SolicitacaoEpiItemDevolucaoDao solicitacaoEpiItemDevolucaoDao;
	
	@Before
	public void setUp() throws Exception
    {
        solicitacaoEpiItemDevolucaoDao = mock(SolicitacaoEpiItemDevolucaoDao.class);
        solicitacaoEpiItemDevolucaoManager.setDao(solicitacaoEpiItemDevolucaoDao);
    }

	@Test
	public void testGetTotalDevolvido()
	{
		when(solicitacaoEpiItemDevolucaoDao.getTotalDevolvido(1L, 1L)).thenReturn(0);
		assertEquals(0, solicitacaoEpiItemDevolucaoManager.getDao().getTotalDevolvido(1L, 1L));
	}
	
	@Test
	public void testFindSolicEpiItemDevolucaoBySolicitacaoEpiItem()
	{
		Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucoes = new ArrayList<SolicitacaoEpiItemDevolucao>();
		
		when(solicitacaoEpiItemDevolucaoDao.findBySolicitacaoEpiItem(1L)).thenReturn(solicitacaoEpiItemDevolucoes);
		assertEquals(solicitacaoEpiItemDevolucoes, solicitacaoEpiItemDevolucaoManager.getDao().findBySolicitacaoEpiItem(1L));
	}
	
	@Test
	public void testRemoveItensDevolvidosSemDevolucao() throws ColecaoVaziaException{
		SolicitacaoEpiItem solicitacaoEpiItem1 = SolicitacaoEpiItemFactory.getEntity(1L);
		SolicitacaoEpiItem solicitacaoEpiItem2 = SolicitacaoEpiItemFactory.getEntity(2L);
		Collection<SolicitacaoEpiItem> solicitacaoEpiItens = Arrays.asList(solicitacaoEpiItem1, solicitacaoEpiItem2);
		Long[] solicitacaoEpiItensId = new CollectionUtil<SolicitacaoEpiItem>().convertCollectionToArrayIds(solicitacaoEpiItens); 
		
		SolicitacaoEpi solicitacaoEpi1 = SolicitacaoEpiFactory.getEntity(2L);
		solicitacaoEpi1.setSolicitacaoEpiItemId(solicitacaoEpiItem1.getId());
		solicitacaoEpi1.setQtdEpiEntregue(2);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity(2L);
		solicitacaoEpi2.setSolicitacaoEpiItemId(solicitacaoEpiItem2.getId());
		solicitacaoEpi2.setQtdEpiEntregue(1);
		
		SolicitacaoEpi solicitacaoEpi3 = SolicitacaoEpiFactory.getEntity(3L);
		solicitacaoEpi3.setSolicitacaoEpiItemId(solicitacaoEpiItem2.getId());
		solicitacaoEpi3.setQtdEpiEntregue(2);
		
		Collection<SolicitacaoEpi> solicitacaoEpis = Arrays.asList(solicitacaoEpi1, solicitacaoEpi2, solicitacaoEpi3);
				
		when(solicitacaoEpiItemDevolucaoDao.findQtdDevolvidaBySolicitacaoItemIds(solicitacaoEpiItensId)).thenReturn(new ArrayList<SolicitacaoEpiItemDevolucao>());
		Collection<SolicitacaoEpi> solicitacaoEpiRetorno = solicitacaoEpiItemDevolucaoManager.removeItensDevolvidos(solicitacaoEpis);
		
		assertEquals(3, solicitacaoEpiRetorno.size());
	}
	
	@Test(expected=ColecaoVaziaException.class)
	public void testRemoveItensDevolvidosComDevolucoesCompletas() throws ColecaoVaziaException{
		SolicitacaoEpiItem solicitacaoEpiItem1 = SolicitacaoEpiItemFactory.getEntity(1L);
		SolicitacaoEpiItem solicitacaoEpiItem2 = SolicitacaoEpiItemFactory.getEntity(2L);
		
		SolicitacaoEpi solicitacaoEpi1 = SolicitacaoEpiFactory.getEntity(2L);
		solicitacaoEpi1.setSolicitacaoEpiItemId(solicitacaoEpiItem1.getId());
		solicitacaoEpi1.setQtdEpiEntregue(2);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity(2L);
		solicitacaoEpi2.setSolicitacaoEpiItemId(solicitacaoEpiItem2.getId());
		solicitacaoEpi2.setQtdEpiEntregue(1);
		
		SolicitacaoEpi solicitacaoEpi3 = SolicitacaoEpiFactory.getEntity(3L);
		solicitacaoEpi3.setSolicitacaoEpiItemId(solicitacaoEpiItem2.getId());
		solicitacaoEpi3.setQtdEpiEntregue(2);
		
		Collection<SolicitacaoEpi> solicitacaoEpis = Arrays.asList(solicitacaoEpi1, solicitacaoEpi2, solicitacaoEpi3);
		
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao1 = SolicitacaoEpiItemDevolucaoFactory.getEntity(null, 2, solicitacaoEpiItem1);
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao2 = SolicitacaoEpiItemDevolucaoFactory.getEntity(null, 3, solicitacaoEpiItem2);
		Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucoes = Arrays.asList(solicitacaoEpiItemDevolucao1, solicitacaoEpiItemDevolucao2);  
				
		when(solicitacaoEpiItemDevolucaoDao.findQtdDevolvidaBySolicitacaoItemIds(new Long[]{solicitacaoEpiItem1.getId(), solicitacaoEpiItem2.getId(), solicitacaoEpiItem2.getId()})).thenReturn(solicitacaoEpiItemDevolucoes);
		solicitacaoEpiItemDevolucaoManager.removeItensDevolvidos(solicitacaoEpis);
	}
	
	@Test
	public void testRemoveItensDevolvidosComDevolucoesParciais() throws ColecaoVaziaException{
		SolicitacaoEpiItem solicitacaoEpiItem1 = SolicitacaoEpiItemFactory.getEntity(1L);
		SolicitacaoEpiItem solicitacaoEpiItem2 = SolicitacaoEpiItemFactory.getEntity(2L);
		
		SolicitacaoEpi solicitacaoEpi1 = SolicitacaoEpiFactory.getEntity(2L);
		solicitacaoEpi1.setSolicitacaoEpiItemId(solicitacaoEpiItem1.getId());
		solicitacaoEpi1.setQtdEpiEntregue(2);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity(2L);
		solicitacaoEpi2.setSolicitacaoEpiItemId(solicitacaoEpiItem2.getId());
		solicitacaoEpi2.setQtdEpiEntregue(1);
		
		SolicitacaoEpi solicitacaoEpi3 = SolicitacaoEpiFactory.getEntity(3L);
		solicitacaoEpi3.setSolicitacaoEpiItemId(solicitacaoEpiItem2.getId());
		solicitacaoEpi3.setQtdEpiEntregue(2);
		
		Collection<SolicitacaoEpi> solicitacaoEpis = Arrays.asList(solicitacaoEpi1, solicitacaoEpi2, solicitacaoEpi3);
		
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao1 = SolicitacaoEpiItemDevolucaoFactory.getEntity(null, 2, solicitacaoEpiItem1);
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao2 = SolicitacaoEpiItemDevolucaoFactory.getEntity(null, 2, solicitacaoEpiItem2);
		Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucoes = Arrays.asList(solicitacaoEpiItemDevolucao1, solicitacaoEpiItemDevolucao2);  
				
		when(solicitacaoEpiItemDevolucaoDao.findQtdDevolvidaBySolicitacaoItemIds(new Long[]{solicitacaoEpiItem1.getId(), solicitacaoEpiItem2.getId(), solicitacaoEpiItem2.getId()})).thenReturn(solicitacaoEpiItemDevolucoes);
		Collection<SolicitacaoEpi> solicitacaoEpiRetorno = solicitacaoEpiItemDevolucaoManager.removeItensDevolvidos(solicitacaoEpis);
		
		assertEquals(1, solicitacaoEpiRetorno.size());
		assertEquals(solicitacaoEpiItem2.getId(), ((SolicitacaoEpi) solicitacaoEpiRetorno.toArray()[0]).getSolicitacaoEpiItemId());		
	}
}
