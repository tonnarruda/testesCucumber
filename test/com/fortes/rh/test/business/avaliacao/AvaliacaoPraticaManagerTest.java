package com.fortes.rh.test.business.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManagerImpl;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;

public class AvaliacaoPraticaManagerTest
{
	private AvaliacaoPraticaManagerImpl avaliacaoPraticaManager = new AvaliacaoPraticaManagerImpl();
	private AvaliacaoPraticaDao avaliacaoPraticaDao;

	@Before
	public void setUp() throws Exception
    {
        avaliacaoPraticaDao = mock(AvaliacaoPraticaDao.class);
        avaliacaoPraticaManager.setDao(avaliacaoPraticaDao);
    }

	@Test
	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		Collection<AvaliacaoPratica> avaliacaoPraticas = AvaliacaoPraticaFactory.getCollection(1L);
		when(avaliacaoPraticaDao.find(1, 15, new String[] {"empresa.id"}, new Object[] { empresaId }, new String[] { "titulo" }, null)).thenReturn(avaliacaoPraticas);

		assertEquals(avaliacaoPraticas, avaliacaoPraticaManager.find(1, 15, new String[] {"empresa.id"}, new Object[] { empresaId }, new String[] { "titulo" }));
	}
	
	@Test
	public void testFindByCertificacaoId(){
		Long certificacaoId = 1L;
		Collection<AvaliacaoPratica> avaliacaoPraticas = AvaliacaoPraticaFactory.getCollection(1L);
		when(avaliacaoPraticaDao.findByCertificacaoId(certificacaoId)).thenReturn(avaliacaoPraticas);
		
		assertEquals(1, avaliacaoPraticaManager.findByCertificacaoId(certificacaoId).size());
	}
	
	
	@Test
	public void findMapByCertificacaoId(){
		Long certificacaoId1 = 1L;
		Long certificacaoId2 = 2L;
		
		AvaliacaoPratica avaliacaoPratica1 = AvaliacaoPraticaFactory.getEntity(1L, certificacaoId1);
		AvaliacaoPratica avaliacaoPratica2 = AvaliacaoPraticaFactory.getEntity(2L, certificacaoId1);
		AvaliacaoPratica avaliacaoPratica3 = AvaliacaoPraticaFactory.getEntity(3L, certificacaoId2);
		
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(avaliacaoPratica1);
		avaliacaoPraticas.add(avaliacaoPratica2);
		avaliacaoPraticas.add(avaliacaoPratica3);
		
		when(avaliacaoPraticaDao.findByCertificacaoId(new Long[]{certificacaoId1, certificacaoId2})).thenReturn(avaliacaoPraticas);
		
		Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas = avaliacaoPraticaManager.findMapByCertificacaoId(new Long[]{certificacaoId1, certificacaoId2});
		
		assertEquals(2, mapAvaliacoesPraticas.size());
		assertEquals(2, mapAvaliacoesPraticas.get(certificacaoId1).size());
		assertEquals(1, mapAvaliacoesPraticas.get(certificacaoId2).size());
	}
}
