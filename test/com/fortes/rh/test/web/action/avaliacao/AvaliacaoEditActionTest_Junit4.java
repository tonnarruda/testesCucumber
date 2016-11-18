package com.fortes.rh.test.web.action.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.avaliacao.AvaliacaoEditAction;

public class AvaliacaoEditActionTest_Junit4
{
	private AvaliacaoEditAction action;
	private AvaliacaoManager avaliacaoMmanager;
	private PeriodoExperienciaManager periodoExperienciaManager;

	@Before
	public void setUp() throws Exception
	{
		action = new AvaliacaoEditAction();

		avaliacaoMmanager = mock(AvaliacaoManager.class);
		action.setAvaliacaoManager(avaliacaoMmanager);
		
		periodoExperienciaManager = mock(PeriodoExperienciaManager.class);
		action.setPeriodoExperienciaManager( periodoExperienciaManager);
		
		action.setAvaliacao(new Avaliacao());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}
	
	@Test
	public void testPrepareInsert() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setAtivo(false);
		action.setAvaliacao(avaliacao);
		Long avaliacaoId = null;
		
		when(periodoExperienciaManager.findPeriodosAtivosAndPeriodoDaAvaliacaoId(eq(action.getEmpresaSistema().getId()), eq(avaliacaoId))).thenReturn(new ArrayList<PeriodoExperiencia>());
		
		assertEquals("success",action.prepareInsert());
		assertTrue(action.getAvaliacao().isAtivo());
	}
	
	@Test
	public void testPrepareUpdateModeloAcompanhamentoPeriodoExperiencia() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		avaliacao.setTipoModeloAvaliacao(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA);
		action.setAvaliacao(avaliacao);
		
		when(avaliacaoMmanager.findById(eq(1L))).thenReturn(avaliacao);
		when(periodoExperienciaManager.findPeriodosAtivosAndPeriodoDaAvaliacaoId(eq(action.getEmpresaSistema().getId()), eq(avaliacao.getId()))).thenReturn(new ArrayList<PeriodoExperiencia>());
		assertEquals("success",action.prepareUpdate());
	}
	
	@Test
	public void testPrepareUpdate() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		avaliacao.setTipoModeloAvaliacao(TipoModeloAvaliacao.AVALIACAO_DESEMPENHO);
		action.setAvaliacao(avaliacao);
		Long avaliacaoId = null;
		
		when(avaliacaoMmanager.findById(eq(1L))).thenReturn(avaliacao);
		when(periodoExperienciaManager.findPeriodosAtivosAndPeriodoDaAvaliacaoId(eq(action.getEmpresaSistema().getId()), eq(avaliacaoId))).thenReturn(new ArrayList<PeriodoExperiencia>());
		assertEquals("success",action.prepareUpdate());
	}
}
