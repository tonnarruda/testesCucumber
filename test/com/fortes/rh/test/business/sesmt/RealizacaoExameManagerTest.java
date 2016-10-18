package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.RealizacaoExameManagerImpl;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.relatorio.ExameAnualRelatorio;
import com.fortes.rh.util.DateUtil;

public class RealizacaoExameManagerTest
{
	private RealizacaoExameManagerImpl realizacaoExameManager;

	private RealizacaoExameDao realizacaoExameDao;

	@Before
	public void setUp() throws Exception
	{
		realizacaoExameManager = new RealizacaoExameManagerImpl();
		realizacaoExameDao = mock(RealizacaoExameDao.class);

		realizacaoExameManager.setDao(realizacaoExameDao);
	}

	@Test
	public void testGetRelatorioAnualVazio()
	{
		Date hoje = new Date();
		
		when(realizacaoExameDao.getRelatorioExame(null, hoje, null)).thenReturn(null);
		Collection<ExameAnualRelatorio> examesAnuals = realizacaoExameManager.getRelatorioExame(null, new Date(), null);
		assertEquals(0, examesAnuals.size());
	}

	@Test
	public void testGetRelatorioAnual()
	{
		Date dataFim = new Date();
		Date dataIni = DateUtil.incrementaAno(dataFim, -1);
		
		Collection<Object[]> listaDeRetornoDeConsulta = new ArrayList<Object[]>();

		// Exame Id, Tipo do Exame, Exame Nome, Resultado do exame
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.ADMISSIONAL, "Exame01", ResultadoExame.NORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.ADMISSIONAL, "Exame03", ResultadoExame.ANORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.ADMISSIONAL, "Exame5", ResultadoExame.ANORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.DEMISSIONAL, "Exame03", ResultadoExame.NORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.DEMISSIONAL, "Exame03", ResultadoExame.NORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.MUDANCA, "Exame03", ResultadoExame.ANORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.PERIODICO, "Exame03", ResultadoExame.NORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.PERIODICO, "Exame023", ResultadoExame.NORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.PERIODICO, "Exame03", ResultadoExame.ANORMAL.toString()});
		listaDeRetornoDeConsulta.add(new Object[]{1L, MotivoSolicitacaoExame.PERIODICO, "Exame53", ResultadoExame.ANORMAL.toString()});

		when(realizacaoExameDao.getRelatorioExame(null, dataIni, dataFim)).thenReturn(listaDeRetornoDeConsulta);

		Collection<ExameAnualRelatorio> examesAnuals = realizacaoExameManager.getRelatorioExame(null, dataIni, dataFim);

		assertEquals(4, examesAnuals.size());

		ExameAnualRelatorio exame1 = (ExameAnualRelatorio) examesAnuals.toArray()[0];
		assertEquals(new Float(3), exame1.getTotalExame());
		assertEquals(new Float(2), exame1.getTotalExameAnormal());
		assertEquals(new Float(66.67), exame1.getExamePorAnormal());

		ExameAnualRelatorio exame2 = (ExameAnualRelatorio) examesAnuals.toArray()[1];
		assertEquals(new Float(2), exame2.getTotalExame());
		assertEquals(new Float(0), exame2.getTotalExameAnormal());
		assertEquals(new Float(0), exame2.getExamePorAnormal());
		assertEquals(new Float(0), exame2.getExamePorAnormal());

		ExameAnualRelatorio exame3 = (ExameAnualRelatorio) examesAnuals.toArray()[2];
		assertEquals(new Float(1), exame3.getTotalExame());
		assertEquals(new Float(1), exame3.getTotalExameAnormal());
		assertEquals(new Float(100), exame3.getExamePorAnormal());

		ExameAnualRelatorio exame4 = (ExameAnualRelatorio) examesAnuals.toArray()[3];
		assertEquals(new Float(4), exame4.getTotalExame());
		assertEquals(new Float(2), exame4.getTotalExameAnormal());
		assertEquals(new Float(50), exame4.getExamePorAnormal());
	}
	
	@Test
	public void testFindByColaborador()
	{
		when(realizacaoExameDao.findRealizadosByColaborador(1L, 500L)).thenReturn(new ArrayList<RealizacaoExame>());
		assertNotNull(realizacaoExameManager.findRealizadosByColaborador(1L, 500L));
	}
}