package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EpiHistoricoManagerImpl;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.model.geral.relatorio.PppFatorRisco;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.util.DateUtil;

public class EpiHistoricoManagerTest extends MockObjectTestCase
{
	private EpiHistoricoManagerImpl epiHistoricoManager;
	private Mock epiHistoricoDao;

	protected void setUp()
	{
		epiHistoricoManager = new EpiHistoricoManagerImpl();
		epiHistoricoDao = new Mock(EpiHistoricoDao.class);
		epiHistoricoManager.setDao((EpiHistoricoDao) epiHistoricoDao.proxy());
	}

//	public void testFindByData()
//	{
//		Epi epi = new Epi();
//		epi.setId(1L);
//
//		EpiHistorico epiHistorico = new EpiHistorico();
//		epiHistorico.setId(1L);
//		epiHistorico.setEpi(epi);
//
//		Collection<EpiHistorico> historicos = new ArrayList<EpiHistorico>();
//		historicos.add(epiHistorico);
//
//		epiHistoricoDao.expects(once()).method("findByData").with(eq(new Date()),eq(1L)).will(returnValue(historicos));
//
//		Collection<EpiHistorico> historicosRetorno = epiHistoricoManager.findByData(new Date(), 1L);
//
//		assertEquals(historicos, historicosRetorno);
//	}

	public void testGetHistoricosEpi()
	{
		Epi epi = new Epi();
		epi.setId(1L);

		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi);

		Risco risco = new Risco();
		risco.setId(1L);
		risco.setEpis(epis);

		PppFatorRisco fr = new PppFatorRisco();
		fr.setRisco(risco);
		fr.setDataInicio(DateUtil.criarAnoMesDia(2007, 1, 1));
		fr.setDataFim(DateUtil.criarAnoMesDia(2008, 1, 1));

		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setId(1L);
		epiHistorico.setEpi(epi);
		epiHistorico.setData(DateUtil.criarAnoMesDia(2007, 2, 1));
		epiHistorico.setVencimentoCA(DateUtil.criarAnoMesDia(2007, 3, 1));
		epiHistorico.setCA("ca1");

		Collection<EpiHistorico> historicos = new ArrayList<EpiHistorico>();
		historicos.add(epiHistorico);

		epiHistoricoDao.expects(once()).method("getHistoricosEpi").with(eq(epi.getId()),eq(fr.getDataInicio()),eq(fr.getDataFim())).will(returnValue(historicos));

		Collection<EpiHistorico> historicosRetorno = epiHistoricoManager.getHistoricosEpi(fr);

		assertEquals(1, historicosRetorno.size());
	}
}
