package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.web.dwr.AvaliacaoDesempenhoDWR;

public class AvaliacaoDesempenhoDWRTest 
{
	private AvaliacaoDesempenhoDWR avaliacaoDesempenhoDWR;
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;

	@Before
	public void setUp() throws Exception
	{
		avaliacaoDesempenhoDWR = new AvaliacaoDesempenhoDWR();
		avaliacaoDesempenhoManager = mock(AvaliacaoDesempenhoManager.class);
		avaliacaoDesempenhoDWR.setAvaliacaoDesempenhoManager(avaliacaoDesempenhoManager);
	}

	@Test
	public void testGetByEmpresa()
	{
		Long empresaId = 1L;
		when(avaliacaoDesempenhoManager.findAllSelect(empresaId, true, TipoModeloAvaliacao.DESEMPENHO)).thenReturn(new ArrayList<AvaliacaoDesempenho>());
		
		assertEquals(0, avaliacaoDesempenhoDWR.getAvaliacoesByEmpresa(empresaId).size());
	}
	
	@Test
	public void testGetAvaliacoesNaoLiberadasByTitulo()
	{
		Long empresaId = 1L;
		String titulo = "teste";
		when(avaliacaoDesempenhoManager.findTituloModeloAvaliacao(anyInt(), anyInt(), any(Date.class), any(Date.class), eq(empresaId), eq(titulo), anyLong(), eq(true))).thenReturn(new ArrayList<AvaliacaoDesempenho>());
		assertEquals(0, avaliacaoDesempenhoDWR.getAvaliacoesNaoLiberadasByTitulo(empresaId, titulo).size());
	}
	
	@Test
	public void testGetAvaliacoesDesempenhoByModelo()
	{
		Long modeloId = 2L;
		
		when(avaliacaoDesempenhoManager.findByModelo(modeloId)).thenReturn(new ArrayList<AvaliacaoDesempenho>());
		assertEquals(0, avaliacaoDesempenhoDWR.getAvaliacoesDesempenhoByModelo(modeloId).size());
	}
}
