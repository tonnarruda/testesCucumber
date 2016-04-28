package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorAvaliacaoPraticaManagerTest
{
	ColaboradorAvaliacaoPraticaManagerImpl colaboradorAvaliacaoPraticaManager = new ColaboradorAvaliacaoPraticaManagerImpl();
	ColaboradorAvaliacaoPraticaDao colaboradorAvaliacaoPraticaDao;
	
	@Before
	public void setUp() throws Exception
    {
        colaboradorAvaliacaoPraticaDao = mock(ColaboradorAvaliacaoPraticaDao.class);
        colaboradorAvaliacaoPraticaManager.setDao(colaboradorAvaliacaoPraticaDao);
    }

	@Test
	public void testFindAllSelect()
	{
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = ColaboradorAvaliacaoPraticaFactory.getCollection(1L);

		when(colaboradorAvaliacaoPraticaDao.findAll()).thenReturn(colaboradorAvaliacaoPraticas);
		assertEquals(colaboradorAvaliacaoPraticas, colaboradorAvaliacaoPraticaManager.findAll());
	}

	@Test
	public void testFindByColaboradorIdAndCertificacaoId() {
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(DateUtil.criarDataMesAno(1, 12, 2015));

		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setCertificacao(certificacao);
		colaboradorAvaliacaoPratica.setColaborador(colaborador);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = Arrays.asList(colaboradorAvaliacaoPratica);
		
		when(colaboradorAvaliacaoPraticaDao.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao.getId(), null, null, true)).thenReturn(colaboradorAvaliacoesPraticas);
		
		assertEquals(1, colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao.getId(), null, null, true).size());
	}
	
	@Test
	public void testRemoveColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(){
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);

		Exception exception = null;
		try {
			colaboradorAvaliacaoPraticaManager.removeByColaboradorCertificacaoId(colaboradorCertificacao.getId());
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	@Test
	public void findByCertificacaoIdAndColaboradoresIds() {
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(colaborador, certificacao, DateUtil.criarDataMesAno(1, 12, 2015));
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(colaboradorCertificacao, colaborador, certificacao, avaliacaoPratica, 9.0);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(colaborador2, certificacao, DateUtil.criarDataMesAno(1, 12, 2015));
		ColaboradorCertificacao colaboradorCertificacao3 = ColaboradorCertificacaoFactory.getEntity(colaborador2, certificacao, DateUtil.criarDataMesAno(1, 1, 2016));
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica2 = ColaboradorAvaliacaoPraticaFactory.getEntity(colaboradorCertificacao2, colaborador2, certificacao, avaliacaoPratica, 9.0);
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica3 = ColaboradorAvaliacaoPraticaFactory.getEntity(colaboradorCertificacao3, colaborador2, certificacao, avaliacaoPratica, 9.0);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPraticas.add(colaboradorAvaliacaoPratica);
		colaboradorAvaliacoesPraticas.add(colaboradorAvaliacaoPratica2);
		colaboradorAvaliacoesPraticas.add(colaboradorAvaliacaoPratica3);
		
		when(colaboradorAvaliacaoPraticaDao.findByCertificacaoIdAndColaboradoresIds(certificacao.getId(), new Long[]{colaborador.getId(), colaborador2.getId()})).thenReturn(colaboradorAvaliacoesPraticas);
		
		Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas= colaboradorAvaliacaoPraticaManager.findMapByCertificacaoIdAndColaboradoresIds(certificacao.getId(), new Long[]{colaborador.getId(), colaborador2.getId()});
		
		assertEquals(2, mapColaboradorAvaliacoesPraticas.size());
		assertEquals(1, mapColaboradorAvaliacoesPraticas.get(colaborador.getId()).size());
		assertEquals(2, mapColaboradorAvaliacoesPraticas.get(colaborador2.getId()).size());
	}
}