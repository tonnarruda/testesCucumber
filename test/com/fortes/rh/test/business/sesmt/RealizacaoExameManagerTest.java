package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.RealizacaoExameManagerImpl;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.relatorio.ExameAnualRelatorio;
import com.fortes.rh.util.DateUtil;

public class RealizacaoExameManagerTest extends MockObjectTestCase
{
	private RealizacaoExameManagerImpl realizacaoExameManager;

	private Mock realizacaoExameDao;

	protected void setUp() throws Exception
	{
		realizacaoExameManager = new RealizacaoExameManagerImpl();
		realizacaoExameDao = new Mock(RealizacaoExameDao.class);

		realizacaoExameManager.setDao((RealizacaoExameDao) realizacaoExameDao.proxy());
	}


	public void testGetRelatorioAnualVazio()
	{
		realizacaoExameDao.expects(once()).method("getRelatorioAnual").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));
		Collection<ExameAnualRelatorio> examesAnuals = realizacaoExameManager.getRelatorioAnual(null, new Date());
		assertEquals(0, examesAnuals.size());
	}

	public void testGetRelatorioAnual()
	{
		Date dataFim = new Date();
		Date dataIni = DateUtil.incrementaAno(dataFim, -1);
		
		Collection<Object[]> lista = new ArrayList<Object[]>();

		// Exame Id, Tipo do Exame, Exame Nome, Resultado do exame
		Object[] retornoConsulta1 = new Object[]{1L, MotivoSolicitacaoExame.ADMISSIONAL, "Exame01", ResultadoExame.NORMAL.toString()};
		lista.add(retornoConsulta1);
		
		Object[] retornoConsulta8 = new Object[]{1L, MotivoSolicitacaoExame.ADMISSIONAL, "Exame03", ResultadoExame.ANORMAL.toString()};
		lista.add(retornoConsulta8);
		
		Object[] retornoConsulta10 = new Object[]{1L, MotivoSolicitacaoExame.ADMISSIONAL, "Exame5", ResultadoExame.ANORMAL.toString()};
		lista.add(retornoConsulta10);

		Object[] retornoConsulta4 =  new Object[]{1L, MotivoSolicitacaoExame.DEMISSIONAL, "Exame03", ResultadoExame.NORMAL.toString()};
		lista.add(retornoConsulta4);

		Object[] retornoConsulta5 = new Object[]{1L, MotivoSolicitacaoExame.DEMISSIONAL, "Exame03", ResultadoExame.NORMAL.toString()};
		lista.add(retornoConsulta5);

		Object[] retornoConsulta6 = new Object[]{1L, MotivoSolicitacaoExame.MUDANCA, "Exame03", ResultadoExame.ANORMAL.toString()};
		lista.add(retornoConsulta6);

		Object[] retornoConsulta2 = new Object[]{1L, MotivoSolicitacaoExame.PERIODICO, "Exame03", ResultadoExame.NORMAL.toString()};
		lista.add(retornoConsulta2);

		Object[] retornoConsulta3 =  new Object[]{1L, MotivoSolicitacaoExame.PERIODICO, "Exame023", ResultadoExame.NORMAL.toString()};
		lista.add(retornoConsulta3);
		
		Object[] retornoConsulta7 = new Object[]{1L, MotivoSolicitacaoExame.PERIODICO, "Exame03", ResultadoExame.ANORMAL.toString()};
		lista.add(retornoConsulta7);
		
		Object[] retornoConsulta9 = new Object[]{1L, MotivoSolicitacaoExame.PERIODICO, "Exame53", ResultadoExame.ANORMAL.toString()};
		lista.add(retornoConsulta9);

		realizacaoExameDao.expects(once()).method("getRelatorioAnual").with(ANYTHING, eq(dataIni), eq(dataFim)).will(returnValue(lista));

		Collection<ExameAnualRelatorio> examesAnuals = realizacaoExameManager.getRelatorioAnual(null, dataFim);

		assertEquals(4, examesAnuals.size());

		ExameAnualRelatorio exame1 = (ExameAnualRelatorio) examesAnuals.toArray()[0];
		assertEquals(3F, exame1.getTotalExame());
		assertEquals(2F, exame1.getTotalExameAnormal());
		assertEquals(66.67F, exame1.getExamePorAnormal());

		ExameAnualRelatorio exame2 = (ExameAnualRelatorio) examesAnuals.toArray()[1];
		assertEquals(2F, exame2.getTotalExame());
		assertEquals(0F, exame2.getTotalExameAnormal());
		assertEquals(0F, exame2.getExamePorAnormal());
		assertEquals(0F, exame2.getExamePorAnormal());

		ExameAnualRelatorio exame3 = (ExameAnualRelatorio) examesAnuals.toArray()[2];
		assertEquals(1F, exame3.getTotalExame());
		assertEquals(1F, exame3.getTotalExameAnormal());
		assertEquals(100F, exame3.getExamePorAnormal());

		ExameAnualRelatorio exame4 = (ExameAnualRelatorio) examesAnuals.toArray()[3];
		assertEquals(4F, exame4.getTotalExame());
		assertEquals(2F, exame4.getTotalExameAnormal());
		assertEquals(50F, exame4.getExamePorAnormal());
	}
	
	public void testFindByColaborador()
	{
		realizacaoExameDao.expects(once()).method("findRealizadosByColaborador").will(returnValue(new ArrayList<RealizacaoExame>()));
		assertNotNull(realizacaoExameManager.findRealizadosByColaborador(1L, 500L));
	}
}